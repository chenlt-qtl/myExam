#!/bin/bash

# 遇到任意未处理错误时立即退出，避免制品未准备完整时继续替换线上版本。
set -e

# 部署路径定义
WORK_DIR="/hcdata/files"
BACKUP_DIR="${WORK_DIR}/backup"
DEPLOY_TMP_DIR="${WORK_DIR}/tmp/manual-app-deploy"
BACKEND_PACKAGE="/tmp/hc-start-boot.jar"
TARGET_JAR="${WORK_DIR}/hc-start-boot.jar"

# 获取当前日期和时间，用于生成备份文件名称。
CURRENT_DATE_TIME=$(date +%Y%m%d-%H%M%S)

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# 日志函数
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

log_step() {
    echo -e "${BLUE}[STEP]${NC} $1"
}

# 生成唯一的备份文件路径，同一秒重复部署时在名称后追加序号。
generate_backup_path() {
    local base_path="${BACKUP_DIR}/hc-start-boot.jar_${CURRENT_DATE_TIME}"
    local backup_path="${base_path}"
    local counter=1

    while [ -e "${backup_path}" ]; do
        backup_path="${base_path}-${counter}"
        counter=$((counter + 1))
    done

    echo "${backup_path}"
}

# 删除本次部署的临时文件，但始终保留 /tmp 中人工上传的原始制品。
cleanup_deploy_tmp_dir() {
    if [ -d "${DEPLOY_TMP_DIR}" ]; then
        sudo rm -rf "${DEPLOY_TMP_DIR:?}"
    fi
}

# 部署开始前先校验并复制制品，避免备份或停服后才发现上传文件不可用。
prepare_backend_package() {
    log_step "步骤1: 校验并准备后端制品..."

    if [ ! -s "${BACKEND_PACKAGE}" ]; then
        log_error "找不到后端制品或制品为空: ${BACKEND_PACKAGE}"
        exit 1
    fi

    cleanup_deploy_tmp_dir
    sudo mkdir -p "${DEPLOY_TMP_DIR}"
    sudo cp "${BACKEND_PACKAGE}" "${DEPLOY_TMP_DIR}/hc-start-boot.jar"

    if [ ! -s "${DEPLOY_TMP_DIR}/hc-start-boot.jar" ]; then
        log_error "后端制品复制失败: ${DEPLOY_TMP_DIR}/hc-start-boot.jar"
        exit 1
    fi

    log_info "后端制品已准备完成，/tmp 中的原始文件将保留"
}

# 备份当前线上 Jar；旧文件不存在时允许继续首次部署。
backup_current_jar() {
    log_step "步骤2: 备份当前后端版本..."
    sudo mkdir -p "${BACKUP_DIR}"

    if [ -f "${TARGET_JAR}" ]; then
        local backup_path
        backup_path=$(generate_backup_path)
        sudo cp "${TARGET_JAR}" "${backup_path}"
        log_info "当前版本已备份到: ${backup_path}"
    else
        log_warn "当前后端 Jar 不存在，跳过备份: ${TARGET_JAR}"
    fi
}

# 停止所有使用 hc-start-boot.jar 启动的旧进程，先正常终止，必要时再强制终止。
stop_backend_service() {
    local pids

    log_step "步骤3: 停止旧后端服务..."
    pids=$(ps -ef | grep "[h]c-start-boot.jar" | awk '{print $2}')

    if [ -z "${pids}" ]; then
        log_warn "未找到 hc-start-boot.jar 进程"
        return
    fi

    for pid in ${pids}; do
        log_info "正在停止进程 PID: ${pid}"
        sudo kill -15 "${pid}" 2>/dev/null || true
    done

    sleep 3

    for pid in ${pids}; do
        if sudo kill -0 "${pid}" 2>/dev/null; then
            log_warn "进程 ${pid} 未响应 SIGTERM，执行强制终止"
            sudo kill -9 "${pid}" 2>/dev/null || true
        fi
    done
}

# 使用已完成校验的临时制品覆盖线上 Jar。
deploy_backend_package() {
    log_step "步骤4: 替换后端 Jar..."
    sudo mkdir -p "${WORK_DIR}"
    sudo cp "${DEPLOY_TMP_DIR}/hc-start-boot.jar" "${TARGET_JAR}"
    log_info "后端 Jar 替换完成: ${TARGET_JAR}"
}

# Fastjson 安全模式参数必须放在 -jar 之前，确保该参数作为 JVM 系统属性生效。
start_backend_service() {
    log_step "步骤5: 启动后端服务..."
    cd "${WORK_DIR}"
    sudo nohup java -Dfastjson.parser.safeMode=true -jar hc-start-boot.jar >/dev/null 2>&1 &
    log_info "后端启动命令已执行"
}

# 等待应用完成启动后检查进程，未发现进程时以失败状态退出。
check_backend_service() {
    local new_pids

    log_step "步骤6: 检查后端服务状态..."
    log_info "等待60秒后检查服务状态..."
    sleep 60
    new_pids=$(ps -ef | grep "[h]c-start-boot.jar" | awk '{print $2}')

    if [ -n "${new_pids}" ]; then
        log_info "服务启动成功，进程 PID: ${new_pids}"
    else
        log_error "服务启动失败，未找到 hc-start-boot.jar 进程"
        log_info "请检查应用日志获取详细信息"
        exit 1
    fi
}

main() {
    log_info "开始执行 SRM 后端手工制品部署..."
    log_info "开始时间: $(date)"

    # 无论部署成功或失败都清理工作目录，人工上传到 /tmp 的原始 Jar 不受影响。
    trap cleanup_deploy_tmp_dir EXIT

    prepare_backend_package
    backup_current_jar
    stop_backend_service
    deploy_backend_package
    start_backend_service
    check_backend_service

    log_info "SRM 后端部署完成，原始制品已保留: ${BACKEND_PACKAGE}"
    log_info "结束时间: $(date)"
}

main "$@"
