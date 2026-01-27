#!/bin/bash

# 设置脚本在遇到错误时立即退出
set -e

# 获取当前日期和时间
CURRENT_DATE_TIME=$(date +%Y%m%d-%H%M%S)

# 脚本工作目录
WORK_DIR="/hcdata/files"

# Git凭据
GIT_USERNAME="da.mu"
GIT_PASSWORD="Git@hc01"

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
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

log_step() {
    echo -e "${BLUE}[STEP]${NC} $1"
}

# 主函数
main() {
    log_info "开始执行SRM前端部署流程..."
    log_info "开始时间: $(date)"
    echo "========================================"

    # 1. 在code/dbs/hc-srms目录拉取最新的git代码
    log_step "步骤1: 拉取最新代码..."
    cd ${WORK_DIR}
    if [ ! -d "code/hc-srms-view" ]; then
        log_error "code/hc-srms-view目录不存在，请检查路径"
        exit 1
    fi
    cd code/hc-srms-view
    log_info "当前目录: $(pwd)"

    # 配置git凭据
    log_info "配置Git凭据..."
    sudo git config --global credential.helper store
    sudo echo "https://da.mu:Git@hc01@github.com" > ~/.git-credentials

    # 执行git pull，强制覆盖本地代码
    if sudo git fetch --all && sudo git reset --hard origin/$(sudo git branch --show-current) && sudo git pull; then
        log_info "代码拉取成功"
    else
        log_error "代码拉取失败"
        exit 1
    fi
    echo "----------------------------------------"

    # 2. 在code/dbs/hc-srms目录下执行mvn clean -DskipTests package打包
    log_step "步骤2: 打包..."
    log_info "执行pnpm build:prod..."
    if sudo pnpm build:prod; then
        log_info "打包成功"
    else
        log_error "打包失败"
        exit 1
    fi
    echo "----------------------------------------"

    # 3. 备份hc-start-boot.jar，备份到当前目录的backup目录下面，命名规则hc-start-boot.jar_日期+时间
    log_step "步骤3: 备份旧文件..."
    cd ${WORK_DIR}
    if [ ! -d "backup" ]; then
        mkdir -p backup
        log_info "创建backup目录: ${WORK_DIR}/backup"
    fi

    if [ -d "/hcdata/view" ]; then
        log_info "备份 view 到 backup/view_${CURRENT_DATE_TIME}"
        sudo cp -rf /hcdata/view backup/view_${CURRENT_DATE_TIME}
        log_info "备份成功"
    else
        log_warn "/hcdata/view 不存在，跳过备份"
    fi
    echo "----------------------------------------"

    # 5. 替换文件
    log_step "步骤5: 替换文件..."
    cd /hcdata
    if [ ! -d "view" ]; then
        log_error "view 不存在"
        exit 1
    fi
    log_info "替换 view..."
    sudo rm -rf /hcdata/view
    sudo cp -rf ${WORK_DIR}/code/hc-srms-view/hc-srms-view /hcdata/view
    log_info "文件替换成功"
    echo "----------------------------------------"

    # 6. 启动 sudo nohup java -jar hc-start-boot.jar >/dev/null 2>&1 &
    log_step "步骤6: 启动服务..."
    cd ${WORK_DIR}
    sudo docker restart nginx
    log_info "服务启动命令已执行"
    echo "----------------------------------------"



    echo "========================================"
    log_info "SRM前端部署完成！"
    log_info "结束时间: $(date)"
}

# 执行主函数
main "$@"
