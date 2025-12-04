#!/bin/bash

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
    if [ ! -d "/www/wwwroot/java/hc/backup" ]; then
        mkdir -p /www/wwwroot/java/hc/backup
        log_info "创建backup目录"
    fi
}

# 生成唯一的备份文件夹名
generate_backup_name() {
    local base_name="backup-${CURRENT_DATE_TIME}"
    local counter=1
    local backup_name="${base_name}"
    
    while [ -d "/www/wwwroot/java/hc/backup/${backup_name}" ]; do
        backup_name="${base_name}-${counter}"
        ((counter++))
    done
    
    echo "${backup_name}"
}

# 停止指定端口的服务
stop_service_on_port() {
    local port=$1
    log_info "停止端口${port}上的服务..."
    
    # 查找占用端口的进程
    local pid=$(lsof -ti:${port} 2>/dev/null || netstat -tlnp 2>/dev/null | grep ":${port}" | awk '{print $7}' | cut -d'/' -f1)
    
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
    
    # 1. 清理旧文件，删除文件夹code/launchers-standalone-1.0.0-SNAPSHOT，code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz,code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar,code/supersonic-webapp.tar.gz,code/supersonic-webapp.tar
    log_info "步骤1: 清理旧文件..."
    
    # 删除文件夹
    if [ -d "/www/wwwroot/java/hc/code/launchers-standalone-1.0.0-SNAPSHOT" ]; then
        rm -rf /www/wwwroot/java/hc/code/launchers-standalone-1.0.0-SNAPSHOT
        log_info "旧版本文件夹删除成功"
    else
        log_info "没有找到旧版本文件夹，跳过删除"
    fi
    
    # 删除后端tar.gz文件
    if [ -f "/www/wwwroot/java/hc/code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz" ]; then
        rm -f /www/wwwroot/java/hc/code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz
        log_info "旧版本后端包删除成功"
    else
        log_info "没有找到旧版本后端包，跳过删除"
    fi
    
    # 删除后端tar文件
    if [ -f "/www/wwwroot/java/hc/code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar" ]; then
        rm -f /www/wwwroot/java/hc/code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar
        log_info "旧版本后端tar文件删除成功"
    else
        log_info "没有找到旧版本后端tar文件，跳过删除"
    fi
    
    # 删除前端tar.gz文件
    if [ -f "/www/wwwroot/java/hc/code/supersonic-webapp.tar.gz" ]; then
        rm -f /www/wwwroot/java/hc/code/supersonic-webapp.tar.gz
        log_info "旧版本前端包删除成功"
    else
        log_info "没有找到旧版本前端包，跳过删除"
    fi
    
    # 删除前端tar文件
    if [ -f "/www/wwwroot/java/hc/code/supersonic-webapp.tar" ]; then
        rm -f /www/wwwroot/java/hc/code/supersonic-webapp.tar
        log_info "旧版本前端tar文件删除成功"
    else
        log_info "没有找到旧版本前端tar文件，跳过删除"
    fi
    
    # 2. 在当前目录下code/bi文件夹中执行git pull,拉取最新代码
    log_info "步骤2: 拉取最新代码..."
    
    # 检查code/bi目录是否存在
    if [ ! -d "/www/wwwroot/java/hc/code/bi" ]; then
        log_error "/www/wwwroot/java/hc/code/bi目录不存在，请检查路径"
        exit 1
    fi
    
    cd /www/wwwroot/java/hc/code/bi
    
    # 执行git pull，强制覆盖本地代码
    if git fetch --all && git reset --hard origin/$(git branch --show-current) && git pull; then
        log_info "代码拉取成功"
        
        # 3. 备份旧文件：复制当前目录下的launchers-standalone-1.0.0-SNAPSHOT到backup目录下，重命名为backup-[yyyyMMdd-hh:mm]，如果文件夹已存在，则在后面加上序号。
        cd ../../  # 回到当前目录
        if [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
            log_info "步骤3: 备份旧文件..."
            create_backup_dir
            backup_name=$(generate_backup_name)
            log_info "备份到 backup/${backup_name}"
            cp -r launchers-standalone-1.0.0-SNAPSHOT "backup/${backup_name}"
        else
            log_warn "launchers-standalone-1.0.0-SNAPSHOT目录不存在，跳过备份"
        fi
        
        # 4. cd code，来到code目录
        log_info "步骤4: 进入code目录并打包..."
        cd code
        
        # 5. 在code/bi文件夹中执行mvn -DskipTests package,打包jar包
        log_info "执行Maven打包..."
        cd bi
        if ! mvn -DskipTests package; then
            log_error "Maven打包失败"
            exit 1
        fi
        log_info "Maven打包成功"
        
        # 6. 在code/bi/webapp目录下执行start-fe-prod.sh，打包前端包
        log_info "步骤6: 打包前端包..."
        cd webapp
        if [ -f "start-fe-prod.sh" ]; then
            chmod +x start-fe-prod.sh
            ./start-fe-prod.sh
            log_info "前端打包成功"
        else
            log_error "找不到start-fe-prod.sh脚本"
            exit 1
        fi
        
        # 8. 把code/bi/webapp目录下的supersonic-webapp.tar.gz复制到code目录
        if [ -f "supersonic-webapp.tar.gz" ]; then
            cp supersonic-webapp.tar.gz ../../
            log_info "前端包移动成功"
        else
            log_error "找不到supersonic-webapp.tar.gz文件"
            exit 1
        fi
        
        cd ..  # 回到bi目录
        
        # 7. 把code/bi/launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz复制到code目录
        log_info "步骤7: 移动jar包..."
        if [ -f "launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz" ]; then
            cp launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz ../
            log_info "jar包移动成功"
        else
            log_error "找不到jar包文件: launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
            exit 1
        fi
        
        cd ..  # 回到code目录
        
        # 9. 解压先用gzip -d,再用tar -xf。
        # 10. 解压code/supersonic-webapp.tar.gz，解压后重命名文件夹为webapp
        log_info "步骤9-10: 解压前端包..."
        if [ -f "supersonic-webapp.tar.gz" ]; then
            log_info "正在解压 supersonic-webapp.tar.gz..."
            
            # 先用gzip -d解压，再用tar -xf解包
            if gzip -d supersonic-webapp.tar.gz; then
                log_info "gzip解压成功"
                
                # 解压tar文件
                if tar -xf supersonic-webapp.tar; then
                    log_info "tar解包成功"
                    
                    # 重命名为webapp
                    mv supersonic-webapp webapp
                    log_info "重命名成功"
                else
                    log_error "tar解包失败"
                    exit 1
                fi
            else
                log_error "gzip解压失败"
                exit 1
            fi
        else
            log_error "找不到supersonic-webapp.tar.gz文件"
            exit 1
        fi
        
        # 11. 解压code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz
        log_info "步骤11: 解压后端包..."
        if [ -f "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz" ]; then
            log_info "正在解压 launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz..."
            
            # 先用gzip -d解压，再用tar -xf解包
            if gzip -d launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz; then
                log_info "gzip解压成功"
                
                # 解压tar文件
                if tar -xf launchers-standalone-1.0.0-SNAPSHOT-bin.tar; then
                    log_info "tar解包成功"
                    log_info "后端包解压成功"
                else
                    log_error "tar解包失败"
                    exit 1
                fi
            else
                log_error "gzip解压失败"
                exit 1
            fi
        else
            log_error "找不到launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz文件"
            exit 1
        fi
        
        # 12. 把code/webapp移动到code/launchers-standalone-1.0.0-SNAPSHOT目录下
        log_info "步骤12: 移动webapp到后端目录..."
        if [ -d "webapp" ] && [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
            mv webapp launchers-standalone-1.0.0-SNAPSHOT/
            log_info "webapp移动成功"
        else
            log_error "webapp或launchers-standalone-1.0.0-SNAPSHOT目录不存在"
            log_info "当前目录内容:"
            ls -la
            exit 1
        fi
        
        # 13. 在code/launchers-standalone-1.0.0-SNAPSHOT/bin目录下执行命令"sed -i 's/\r$//' supersonic-start.sh"
        log_info "步骤13: 修复脚本格式..."
        if [ -f "launchers-standalone-1.0.0-SNAPSHOT/bin/supersonic-start.sh" ]; then
            cd launchers-standalone-1.0.0-SNAPSHOT/bin
            sed -i 's/\r$//' supersonic-start.sh
            chmod +x supersonic-start.sh
            cd ../../..
            log_info "脚本格式修复成功"
        else
            log_error "找不到supersonic-start.sh脚本"
            exit 1
        fi


        
        # 14. 停止端口为9081的服务
        log_info "步骤14: 停止现有服务..."
        stop_service_on_port 9081
        
        # 15. 删除launchers-standalone-1.0.0-SNAPSHOT文件夹
        log_info "步骤15: 清理当前目录的旧版本..."
        if [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
            rm -rf launchers-standalone-1.0.0-SNAPSHOT
            log_info "当前目录旧版本删除成功"
        else
            log_info "当前目录没有找到旧版本文件，跳过删除"
        fi
        
        # 16. 移动code/launchers-standalone-1.0.0-SNAPSHOT到当前文件夹中
        log_info "步骤16: 更新部署文件..."
        if [ -d "/www/wwwroot/java/hc/code/launchers-standalone-1.0.0-SNAPSHOT" ]; then
            mv /www/wwwroot/java/hc/code/launchers-standalone-1.0.0-SNAPSHOT ./
            log_info "新版本移动成功"
        else
            log_error "找不到新的launchers-standalone-1.0.0-SNAPSHOT目录"
            exit 1
        fi
        
        # 17. cd launchers-standalone-1.0.0-SNAPSHOT/bin
        # 18. 启动程序"./supersonic-start.sh"
        log_info "步骤17-18: 启动新服务..."
        cd launchers-standalone-1.0.0-SNAPSHOT/bin
        ./supersonic-start.sh
        
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