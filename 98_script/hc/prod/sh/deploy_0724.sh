#!/bin/bash

# 遇到任意未处理错误时立即退出，避免在部署文件未准备完整时继续替换线上版本。
set -e

# 部署目录定义
DEPLOY_DIR="/hcdata/chatbi"
WORK_DIR="${DEPLOY_DIR}/tmp"
BACKUP_DIR="${DEPLOY_DIR}/backup"
APP_DIR_NAME="launchers-standalone-1.0.0-SNAPSHOT"

# /tmp 中的原始部署包只用于复制，部署过程中不得删除或移动。
BACKEND_PACKAGE="/tmp/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
FRONTEND_PACKAGE="/tmp/supersonic-webapp.tar.gz"

# 获取当前日期和时间，用于生成备份目录名称。
CURRENT_DATE_TIME=$(date +%Y%m%d-%H%M)

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
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

# 部署开始前必须同时确认前后端包存在，避免备份或停服后才发现文件不完整。
check_deploy_packages() {
    local missing_package=0

    if [ ! -f "${BACKEND_PACKAGE}" ]; then
        log_error "找不到后端部署包: ${BACKEND_PACKAGE}"
        missing_package=1
    fi

    if [ ! -f "${FRONTEND_PACKAGE}" ]; then
        log_error "找不到前端部署包: ${FRONTEND_PACKAGE}"
        missing_package=1
    fi

    if [ "${missing_package}" -ne 0 ]; then
        log_error "部署包不完整，本次部署已终止"
        exit 1
    fi
}

# 生成唯一的备份目录名称，同一分钟重复部署时在名称后追加序号。
generate_backup_name() {
    local base_name="backup-${CURRENT_DATE_TIME}"
    local backup_name="${base_name}"
    local counter=1

    while [ -d "${BACKUP_DIR}/${backup_name}" ]; do
        backup_name="${base_name}-${counter}"
        counter=$((counter + 1))
    done

    echo "${backup_name}"
}

# 新部署文件处理前先备份当前线上版本，旧版本不存在时允许继续首次部署。
backup_current_version() {
    log_info "步骤1: 备份旧版本..."
    sudo mkdir -p "${BACKUP_DIR}"

    if [ -d "${DEPLOY_DIR}/${APP_DIR_NAME}" ]; then
        local backup_name
        backup_name=$(generate_backup_name)
        sudo cp -r "${DEPLOY_DIR}/${APP_DIR_NAME}" "${BACKUP_DIR}/${backup_name}"
        log_info "旧版本已备份到: ${BACKUP_DIR}/${backup_name}"
    else
        log_warn "旧部署目录不存在，跳过备份: ${DEPLOY_DIR}/${APP_DIR_NAME}"
    fi
}

# 清理独立工作目录并复制部署包；这里只清理 /hcdata/chatbi/tmp，不处理 /tmp 中的原始包。
prepare_work_dir() {
    log_info "步骤2: 准备部署工作目录..."

    if [ -d "${WORK_DIR}" ]; then
        sudo rm -rf "${WORK_DIR:?}"
    fi
    sudo mkdir -p "${WORK_DIR}"

    sudo cp "${BACKEND_PACKAGE}" "${WORK_DIR}/"
    sudo cp "${FRONTEND_PACKAGE}" "${WORK_DIR}/"
    log_info "部署包已复制到: ${WORK_DIR}"
}

# 解压前后端部署包，并将前端文件组装到后端部署目录。
assemble_deploy_files() {
    log_info "步骤3: 解压并组装部署文件..."
    cd "${WORK_DIR}"

    # 参考现有生产解压脚本，前端包先通过 gzip 生成 tar 文件，再使用 tar 解包。
    log_info "正在解压前端包..."
    if sudo gzip -d "$(basename "${FRONTEND_PACKAGE}")"; then
        log_info "前端包 gzip 解压成功"
    else
        log_error "前端包 gzip 解压失败"
        exit 1
    fi

    if sudo tar -xf supersonic-webapp.tar; then
        log_info "前端包 tar 解包成功"
    else
        log_error "前端包 tar 解包失败"
        exit 1
    fi

    # 后端包同样分为 gzip 解压和 tar 解包两个步骤，便于准确定位失败阶段。
    log_info "正在解压后端包..."
    if sudo gzip -d "$(basename "${BACKEND_PACKAGE}")"; then
        log_info "后端包 gzip 解压成功"
    else
        log_error "后端包 gzip 解压失败"
        exit 1
    fi

    if sudo tar -xf launchers-standalone-1.0.0-SNAPSHOT-bin.tar; then
        log_info "后端包 tar 解包成功"
    else
        log_error "后端包 tar 解包失败"
        exit 1
    fi

    if [ ! -d "supersonic-webapp" ]; then
        log_error "前端包解压后未找到目录: supersonic-webapp"
        exit 1
    fi

    if [ ! -d "${APP_DIR_NAME}" ]; then
        log_error "后端包解压后未找到目录: ${APP_DIR_NAME}"
        exit 1
    fi

    sudo mv supersonic-webapp webapp
    sudo mv webapp "${APP_DIR_NAME}/"

    # 修复 Windows 换行符并确保启动脚本可以执行。
    if [ ! -f "${APP_DIR_NAME}/bin/supersonic-start.sh" ]; then
        log_error "找不到启动脚本: ${APP_DIR_NAME}/bin/supersonic-start.sh"
        exit 1
    fi
    sudo sed -i 's/\r$//' "${APP_DIR_NAME}/bin/supersonic-start.sh"
    sudo chmod +x "${APP_DIR_NAME}/bin/supersonic-start.sh"
    log_info "部署文件组装完成"
}

# 停止指定端口上的旧服务，优先使用 lsof，无法查到时再使用 netstat。
stop_service_on_port() {
    local port=$1
    local pids

    log_info "步骤4: 停止端口 ${port} 上的旧服务..."
    pids=$(sudo lsof -ti:"${port}" 2>/dev/null || sudo netstat -tlnp 2>/dev/null | grep ":${port} " | awk '{print $7}' | cut -d'/' -f1)

    if [ -z "${pids}" ]; then
        log_warn "端口 ${port} 上没有运行的服务"
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

# 新版本准备完整后才替换线上目录，减少因解压失败造成的服务中断。
deploy_new_version() {
    log_info "步骤5: 替换线上部署目录..."

    if [ -d "${DEPLOY_DIR}/${APP_DIR_NAME}" ]; then
        sudo rm -rf "${DEPLOY_DIR:?}/${APP_DIR_NAME}"
    fi

    sudo cp -r "${WORK_DIR}/${APP_DIR_NAME}" "${DEPLOY_DIR}/"
    log_info "新版本部署目录复制完成"
}

# 启动新版本服务。
start_service() {
    log_info "步骤6: 启动新服务..."
    cd "${DEPLOY_DIR}/${APP_DIR_NAME}/bin"
    sudo ./supersonic-start.sh
    log_info "服务启动成功"
}

main() {
    log_info "开始执行 HC 生产环境部署..."

    check_deploy_packages
    backup_current_version
    prepare_work_dir
    assemble_deploy_files
    stop_service_on_port 9080
    deploy_new_version
    start_service

    log_info "部署完成，/tmp 中的原始部署包已保留"
}

main "$@"
