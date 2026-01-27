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
    log_info "开始执行SRM后端部署流程..."
    log_info "开始时间: $(date)"
    echo "========================================"

    # 1. 在code/dbs/hc-srms目录拉取最新的git代码
    log_step "步骤1: 拉取最新代码..."
    cd ${WORK_DIR}
    if [ ! -d "code/dbs/hc-srms" ]; then
        log_error "code/dbs/hc-srms目录不存在，请检查路径"
        exit 1
    fi
    cd code/dbs/hc-srms
    log_info "当前目录: $(pwd)"

    # 配置git凭据
    log_info "配置Git凭据..."
    sudo git config --global credential.helper store
    sudo echo "https://da.mu:Aifa@Xmhc011@github.com" > ~/.git-credentials

    # 执行git pull，强制覆盖本地代码
    if sudo git fetch --all && sudo git reset --hard origin/$(sudo git branch --show-current) && sudo git pull; then
        log_info "代码拉取成功"
    else
        log_error "代码拉取失败"
        exit 1
    fi
    echo "----------------------------------------"

    # 2. 在code/dbs/hc-srms目录下执行mvn clean -DskipTests package打包
    log_step "步骤2: Maven打包..."
    log_info "执行mvn clean -DskipTests package..."
    if sudo mvn clean -DskipTests package; then
        log_info "Maven打包成功"
    else
        log_error "Maven打包失败"
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

    if [ -f "hc-start-boot.jar" ]; then
        log_info "备份 hc-start-boot.jar 到 backup/hc-start-boot.jar_${CURRENT_DATE_TIME}"
        sudo cp hc-start-boot.jar backup/hc-start-boot.jar_${CURRENT_DATE_TIME}
        log_info "备份成功"
    else
        log_warn "hc-start-boot.jar 不存在，跳过备份"
    fi
    echo "----------------------------------------"

    # 4. 停掉服务 ps -ef|grep hc-start-boot.jar ，kill对应的进程ID
    log_step "步骤4: 停止服务..."
    log_info "查找 hc-start-boot.jar 进程..."
    PID=$(ps -ef | grep "hc-start-boot.jar" | grep -v grep | awk '{print $2}')
    if [ -n "$PID" ]; then
        log_info "找到进程 PID: ${PID}，正在停止..."
        sudo kill $PID
        sleep 3
        # 检查进程是否还在运行
        if ps -p $PID > /dev/null 2>&1; then
            log_warn "进程未响应，强制终止..."
            sudo kill -9 $PID
        fi
        log_info "服务已停止"
    else
        log_warn "未找到 hc-start-boot.jar 进程"
    fi
    echo "----------------------------------------"

    # 5. 替换文件 sudo mv code/hcadmin/hc-start-boot.jar .
    log_step "步骤5: 替换文件..."
    cd ${WORK_DIR}
    if [ ! -f "hc-start-boot.jar" ]; then
        log_error "hc-start-boot.jar 不存在，打包可能失败"
        exit 1
    fi
    log_info "替换 hc-start-boot.jar..."
    sudo mv code/dbs/hc-srms/hc-start/hc-start-boot/target/hc-start-boot.jar .
    log_info "文件替换成功"
    echo "----------------------------------------"

    # 6. 启动 sudo nohup java -jar hc-start-boot.jar >/dev/null 2>&1 &
    log_step "步骤6: 启动服务..."
    cd ${WORK_DIR}
    log_info "启动 hc-start-boot.jar..."
    sudo nohup java -jar hc-start-boot.jar >/dev/null 2>&1 &
    log_info "服务启动命令已执行"
    echo "----------------------------------------"

    # 7. 一分钟后看看进程还在不在
    log_step "步骤7: 检查服务状态..."
    log_info "等待60秒后检查服务状态..."
    sleep 60
    NEW_PID=$(ps -ef | grep "hc-start-boot.jar" | grep -v grep | awk '{print $2}')
    if [ -n "$NEW_PID" ]; then
        log_info "✓ 服务启动成功，进程 PID: ${NEW_PID}"
    else
        log_error "✗ 服务启动失败，未找到进程"
        log_info "请检查日志文件以获取详细信息"
        exit 1
    fi
    echo "----------------------------------------"

    echo "========================================"
    log_info "SRM后端部署完成！"
    log_info "结束时间: $(date)"
}

# 执行主函数
main "$@"
