hc_prod_full.sh#!/bin/bash

# 设置脚本在遇到错误时立即退出
set -e

# 获取当前日期和时间
CURRENT_DATE_TIME=$(date +%Y%m%d-%H%M)

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

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

# 检查并创建backup目录
create_backup_dir() {
    if [ ! -d "/hcdata/backup" ]; then
        mkdir -p /hcdata/backup
        log_info "创建backup目录: /hcdata/backup"
    fi
}

# 生成唯一的备份文件夹名
generate_backup_name() {
    local base_name="backup-${CURRENT_DATE_TIME}"
    local counter=1
    local backup_name="${base_name}"

    while [ -d "/hcdata/backup/${backup_name}" ]; do
        backup_name="${base_name}-${counter}"
        ((counter++))
    done

    echo "${backup_name}"
}



# 主函数
main() {
    log_info "开始备份文件..."

    cd /hcdata/chatbi
    pwd

    log_info "步骤3: 备份旧文件..."

    # 3. 备份旧文件：sudo复制launchers-standalone-1.0.0-SNAPSHOT到backup目录下，重命名为backup-[yyyyMMdd-hh:mm]，如果文件夹已存在，则在后面加上序号。
    if [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
        backup_name=$(generate_backup_name)
        log_info "备份到 backup/${backup_name}"
        sudo cp -r launchers-standalone-1.0.0-SNAPSHOT "backup/${backup_name}"
    else
        log_warn "launchers-standalone-1.0.0-SNAPSHOT目录不存在，跳过备份"
    fi

    if [ $? -eq 0 ]; then
        log_info "备份成功！"
    else
        log_error "备份失败"
        exit 1
    fi
}

# 执行主函数
main "$@"