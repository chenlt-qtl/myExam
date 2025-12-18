hc_prod_full.sh#!/bin/bash

# 设置脚本在遇到错误时立即退出
set -e

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

# 停止指定端口的服务
stop_service_on_port() {
    local port=$1
    log_info "停止端口${port}上的服务..."
    
    # 查找占用端口的进程
    local pid=$(sudo lsof -ti:${port} 2>/dev/null || sudo netstat -tlnp 2>/dev/null | grep ":${port} " | awk '{print $7}' | cut -d'/' -f1)
    
    if [ -n "$pid" ]; then
        log_info "找到进程PID: ${pid}，正在停止..."
        sudo kill -15 $pid 2>/dev/null || true
        sleep 3
        
        # 检查进程是否还在运行
        if sudo kill -0 $pid 2>/dev/null; then
            log_warn "进程未响应SIGTERM，强制终止..."
            sudo kill -9 $pid 2>/dev/null || true
        fi
        
        log_info "端口${port}上的服务已停止"
    else
        log_warn "端口${port}上没有运行的服务"
    fi
}

# 主函数
main() {
        log_info "开始执行部署脚本..."

        cd /hcdata/chatbi
        pwd

        # 13. 停止端口为9080的服务
        log_info "步骤13: 停止现有服务..."
        stop_service_on_port 9080
        
        # 14. 删除launchers-standalone-1.0.0-SNAPSHOT文件夹
        log_info "步骤14: 清理当前目录的旧版本..."
        if [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
            sudo rm -rf launchers-standalone-1.0.0-SNAPSHOT
            log_info "当前目录旧版本删除成功"
        else
            log_info "当前目录没有找到旧版本文件，跳过删除"
        fi
        
        # 16. 移动code/launchers-standalone-1.0.0-SNAPSHOT到当前文件夹中
        log_info "步骤16: 更新部署文件..."
        if [ -d "code/launchers-standalone-1.0.0-SNAPSHOT" ]; then
            sudo cp -r code/launchers-standalone-1.0.0-SNAPSHOT .
            log_info "新版本移动成功"
        else
            log_error "找不到新的launchers-standalone-1.0.0-SNAPSHOT目录"
            exit 1
        fi
        
        # 17. cd launchers-standalone-1.0.0-SNAPSHOT/bin
        # 18. 启动程序"./supersonic-start.sh"
        log_info "步骤17-18: 启动新服务..."
        cd launchers-standalone-1.0.0-SNAPSHOT/bin
        sudo ./supersonic-start.sh
        
        if [ $? -eq 0 ]; then
            log_info "服务启动成功！"
        else
            log_error "服务启动失败"
            exit 1
        fi
}

# 执行主函数
main "$@"