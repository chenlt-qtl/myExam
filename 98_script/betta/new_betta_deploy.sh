#!/bin/bash

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
    local pid=$(lsof -ti:${port} 2>/dev/null || netstat -tlnp 2>/dev/null | grep ":${port} " | awk '{print $7}' | cut -d'/' -f1)
    
    if [ -n "$pid" ]; then
        log_info "找到进程PID: ${pid}，正在停止..."
        kill -15 $pid 2>/dev/null || true
        sleep 3
        
        # 检查进程是否还在运行
        if kill -0 $pid 2>/dev/null; then
            log_warn "进程未响应SIGTERM，强制终止..."
            kill -9 $pid 2>/dev/null || true
        fi
        
        log_info "端口${port}上的服务已停止"
    else
        log_warn "端口${port}上没有运行的服务"
    fi
}

# 主函数
main() {
    log_info "开始执行部署脚本..."
    
    # 1. 清理旧文件，sudo删除文件夹code/launchers-standalone-1.0.0-SNAPSHOT，code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz,code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar,code/supersonic-webapp.tar.gz,code/supersonic-webapp.tar
    log_info "步骤1: 清理旧文件..."

    
    # 删除后端tar文件
    if [ -f "Betta1.0/betta-admin.jar" ]; then
        sudo rm -f Betta1.0/betta-admin.jar
        log_info "旧版本后端包删除成功"
    else
        log_info "没有找到旧版本后端包，跳过删除"
    fi
    

    # 删除前端文件
    if [ -f "/root/corn/code/Betta1.0_ui/" ]; then
        sudo rm -rf /root/corn/dockerFile/nginx/Betta1.0/*
        log_info "旧版本前端文件删除成功"
    else
        log_info "没有找到旧版本前端文件，跳过删除"
    fi
    
    # 2. 在code/bi文件夹中执行sudo git pull,拉取最新代码,使用的是https协议，Username是"da.mu",Password是"Aifa@Xmhc011"
    log_info "步骤2: 拉取最新代码..."
    
    # 检查code/Betta1.0目录是否存在
    if [ ! -d "Betta1.0" ]; then
        log_error "Betta1.0目录不存在，请检查路径"
        exit 1
    fi
    
    cd Betta1.0/betta1.0

    
    # 执行git pull，强制覆盖本地代码
    if sudo git fetch --all && sudo git reset --hard origin/$(sudo git branch --show-current) && sudo git pull; then
        log_info "代码拉取成功"
        
        # 5. 在code/bi文件夹中执行sudo mvn -DskipTests package,打包jar包
        log_info "步骤5: 执行Maven打包..."
        if ! sudo mvn -DskipTests package; then
            log_error "Maven打包失败"
            exit 1
        fi
        log_info "Maven打包成功"
        
        log_info "步骤6: 打包前端包..."
        cd betta-ui
        npm run build:prod

        
        # 7. 把code/bi/webapp目录下的supersonic-webapp.tar.gz复制到code目录
        if [ -d "dist" ]; then
            sudo cp -rf dist/* /root/corn/dockerFile/nginx/Betta1_0/
            log_info "前端包复制成功"
        else
            log_error "找不到前端文件"
            exit 1
        fi
        
        # 8. 把code/bi/launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz复制到code目录
        log_info "步骤8: 复制jar包..."
        cd ..
        if [ -f "betta-admin/target/betta-admin.jar" ]; then
            sudo cp -rf betta-admin/target/betta-admin.jar /root/corn/code/Betta1.0/Betta1_0.jar

            log_info "jar包复制成功"
        else
            log_error "找不到jar包文件"
            exit 1
        fi
        
        cd ..  # 回到code目录



        log_info "步骤13: 停止现有服务..."
        stop_service_on_port 8080


        log_info "步骤17-18: 启动新服务..."
        cd /root/corn/code/Betta1.0
        nohup java -Xms256M -Xmx512M  -jar Betta1_0.jar > /dev/null 2>&1 &
        docker restart nginx

        if [ $? -eq 0 ]; then
            log_info "服务启动成功！"
            log_info "部署完成！"
        else
            log_error "服务启动失败"
            exit 1
        fi
        
    else
        # 3. 如果拉取失败，打印错误信息
        log_error "代码拉取失败，请检查网络连接或Git配置"
        exit 1
    fi
}

# 执行主函数
main "$@"