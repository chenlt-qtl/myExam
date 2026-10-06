#!/bin/bash

# 遇到任意未处理错误时立即退出，避免解压或校验失败后继续替换线上版本。
set -e

# 部署路径定义
WORK_DIR="/hcdata/files"
BACKUP_DIR="${WORK_DIR}/backup"
DEPLOY_TMP_DIR="${WORK_DIR}/tmp/manual-web-deploy"
EXTRACT_DIR="${DEPLOY_TMP_DIR}/extract"
PREPARED_VIEW_DIR="${DEPLOY_TMP_DIR}/view"
FRONTEND_PACKAGE="/tmp/hc-srms-view.zip"
TARGET_VIEW_DIR="/hcdata/view"

# 获取当前日期和时间，用于生成备份目录名称。
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

# 生成唯一的前端备份目录路径，同一秒重复部署时在名称后追加序号。
generate_backup_path() {
    local base_path="${BACKUP_DIR}/view_${CURRENT_DATE_TIME}"
    local backup_path="${base_path}"
    local counter=1

    while [ -e "${backup_path}" ]; do
        backup_path="${base_path}-${counter}"
        counter=$((counter + 1))
    done

    echo "${backup_path}"
}

# 删除本次解压和组装目录，但始终保留 /tmp 中人工上传的原始 ZIP。
cleanup_deploy_tmp_dir() {
    if [ -d "${DEPLOY_TMP_DIR}" ]; then
        sudo rm -rf "${DEPLOY_TMP_DIR:?}"
    fi
}

# 部署开始前校验 ZIP 完整性，避免备份或替换线上目录后才发现压缩包不可用。
check_frontend_package() {
    log_step "步骤1: 校验前端制品..."

    if [ ! -s "${FRONTEND_PACKAGE}" ]; then
        log_error "找不到前端制品或制品为空: ${FRONTEND_PACKAGE}"
        exit 1
    fi

    if ! command -v unzip >/dev/null 2>&1; then
        log_error "服务器未安装 unzip，无法解压前端制品"
        exit 1
    fi

    if ! unzip -tq "${FRONTEND_PACKAGE}" >/dev/null; then
        log_error "前端 ZIP 完整性校验失败: ${FRONTEND_PACKAGE}"
        exit 1
    fi

    log_info "前端制品校验通过"
}

# 解压并兼容三种目录结构，最终统一组装为包含 index.html 的 view 目录。
prepare_frontend_files() {
    local source_view_dir

    log_step "步骤2: 解压并组装前端文件..."
    cleanup_deploy_tmp_dir
    sudo mkdir -p "${EXTRACT_DIR}"
    sudo unzip -q "${FRONTEND_PACKAGE}" -d "${EXTRACT_DIR}"

    if [ -f "${EXTRACT_DIR}/view/index.html" ]; then
        source_view_dir="${EXTRACT_DIR}/view"
        log_info "识别到 ZIP 根目录中的 view 目录"
    elif [ -f "${EXTRACT_DIR}/hc-srms-view/index.html" ]; then
        source_view_dir="${EXTRACT_DIR}/hc-srms-view"
        log_info "识别到 ZIP 根目录中的 hc-srms-view 目录"
    elif [ -f "${EXTRACT_DIR}/index.html" ]; then
        source_view_dir="${EXTRACT_DIR}"
        log_info "识别到 ZIP 根目录中的前端静态文件"
    else
        log_error "ZIP 解压后未找到有效的 index.html"
        log_error "支持的入口: view/index.html、hc-srms-view/index.html 或 index.html"
        exit 1
    fi

    sudo mkdir -p "${PREPARED_VIEW_DIR}"
    sudo cp -R "${source_view_dir}/." "${PREPARED_VIEW_DIR}/"

    if [ ! -f "${PREPARED_VIEW_DIR}/index.html" ]; then
        log_error "前端文件组装失败，未生成: ${PREPARED_VIEW_DIR}/index.html"
        exit 1
    fi

    log_info "前端文件已准备完成，/tmp 中的原始文件将保留"
}

# 备份当前线上前端目录；目录不存在时允许继续首次部署。
backup_current_view() {
    log_step "步骤3: 备份当前前端版本..."
    sudo mkdir -p "${BACKUP_DIR}"

    if [ -d "${TARGET_VIEW_DIR}" ]; then
        local backup_path
        backup_path=$(generate_backup_path)
        sudo cp -R "${TARGET_VIEW_DIR}" "${backup_path}"
        log_info "当前前端版本已备份到: ${backup_path}"
    else
        log_warn "当前前端目录不存在，跳过备份: ${TARGET_VIEW_DIR}"
    fi
}

# 仅在新前端文件完成解压和校验后替换线上目录。
deploy_frontend_files() {
    log_step "步骤4: 替换前端文件..."

    if [ -d "${TARGET_VIEW_DIR}" ]; then
        sudo rm -rf "${TARGET_VIEW_DIR:?}"
    fi

    sudo cp -R "${PREPARED_VIEW_DIR}" "${TARGET_VIEW_DIR}"
    log_info "前端文件替换完成: ${TARGET_VIEW_DIR}"
}

# 重启现有 nginx 容器，使新静态文件正式生效。
restart_nginx() {
    log_step "步骤5: 重启 Nginx..."

    if sudo docker restart nginx; then
        log_info "Nginx 容器重启成功"
    else
        log_error "Nginx 容器重启失败"
        exit 1
    fi
}

main() {
    log_info "开始执行 SRM 前端手工制品部署..."
    log_info "开始时间: $(date)"

    # 无论部署成功或失败都清理工作目录，人工上传到 /tmp 的原始 ZIP 不受影响。
    trap cleanup_deploy_tmp_dir EXIT

    check_frontend_package
    prepare_frontend_files
    backup_current_view
    deploy_frontend_files
    restart_nginx

    log_info "SRM 前端部署完成，原始制品已保留: ${FRONTEND_PACKAGE}"
    log_info "结束时间: $(date)"
}

main "$@"
