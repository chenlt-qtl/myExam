#!/bin/bash

# 遇到任意未处理错误时立即退出，避免回退过程在目录状态不完整时继续执行。
set -e

# 回退目录定义
DEPLOY_DIR="/hcdata/chatbi"
BACKUP_DIR="${DEPLOY_DIR}/backup"
APP_DIR_NAME="launchers-standalone-1.0.0-SNAPSHOT"
CURRENT_APP_DIR="${DEPLOY_DIR}/${APP_DIR_NAME}"
OLD_APP_DIR="${DEPLOY_DIR}/${APP_DIR_NAME}_old"

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

# 按目录修改时间遍历 backup-*，返回最后一次生成或更新的备份目录。
find_latest_backup() {
    local backup_path
    local latest_backup=""

    for backup_path in "${BACKUP_DIR}"/backup-*; do
        if [ ! -d "${backup_path}" ]; then
            continue
        fi

        if [ -z "${latest_backup}" ] || [ "${backup_path}" -nt "${latest_backup}" ]; then
            latest_backup="${backup_path}"
        fi
    done

    echo "${latest_backup}"
}

# 停服前完成全部目录和启动脚本检查，避免无可用备份时中断线上服务。
check_rollback_files() {
    local latest_backup=$1

    if [ ! -d "${CURRENT_APP_DIR}" ]; then
        log_error "当前部署目录不存在: ${CURRENT_APP_DIR}"
        exit 1
    fi

    if [ -z "${latest_backup}" ]; then
        log_error "备份目录中没有可用的 backup-* 文件夹: ${BACKUP_DIR}"
        exit 1
    fi

    if [ ! -f "${latest_backup}/bin/supersonic-start.sh" ]; then
        log_error "最新备份中找不到启动脚本: ${latest_backup}/bin/supersonic-start.sh"
        exit 1
    fi
}

# 停止指定端口上的服务，优先使用 lsof，无法查到时再使用 netstat。
stop_service_on_port() {
    local port=$1
    local pids

    log_info "步骤1: 停止端口 ${port} 上的服务..."
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

    log_info "端口 ${port} 上的服务已停止"
}

# 保留本次回退前的当前版本；旧的 _old 目录存在时按要求先删除。
preserve_current_version() {
    log_info "步骤2: 保留当前部署版本..."

    if [ -d "${OLD_APP_DIR}" ]; then
        sudo rm -rf "${OLD_APP_DIR:?}"
        log_info "已删除原有旧版本目录: ${OLD_APP_DIR}"
    fi

    sudo mv "${CURRENT_APP_DIR}" "${OLD_APP_DIR}"
    log_info "当前版本已改名为: ${OLD_APP_DIR}"
}

# 复制最新备份作为正式部署目录，备份原目录继续保留。
restore_latest_backup() {
    local latest_backup=$1

    log_info "步骤3: 恢复最后一次备份..."
    sudo cp -r "${latest_backup}" "${CURRENT_APP_DIR}"
    sudo chmod +x "${CURRENT_APP_DIR}/bin/supersonic-start.sh"
    log_info "已从备份恢复: ${latest_backup}"
}

# 启动回退后的服务。
start_service() {
    log_info "步骤4: 启动回退后的服务..."
    cd "${CURRENT_APP_DIR}/bin"
    sudo ./supersonic-start.sh
    log_info "服务启动成功"
}

main() {
    local latest_backup

    log_info "开始执行 HC 服务回退..."
    latest_backup=$(find_latest_backup)

    check_rollback_files "${latest_backup}"
    log_info "本次使用的备份目录: ${latest_backup}"

    stop_service_on_port 9080
    preserve_current_version
    restore_latest_backup "${latest_backup}"
    start_service

    log_info "服务回退完成，备份目录和 _old 版本均已保留"
}

main "$@"
