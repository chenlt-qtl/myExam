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
    if [ ! -d "backup" ]; then
        mkdir -p backup
        log_info "创建backup目录"
    fi
}

# 生成唯一的备份文件夹名
generate_backup_name() {
    local base_name="backup-${CURRENT_DATE_TIME}"
    local counter=1
    local backup_name="${base_name}"
    
    while [ -d "backup/${backup_name}" ]; do
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
    
    # 1. 在当前目录下code/bi文件夹中执行git pull
    log_info "步骤1: 拉取最新代码..."
    
    # 检查code/bi目录是否存在
    if [ ! -d "code/bi" ]; then
        log_error "code/bi目录不存在，请检查路径"
        exit 1
    fi
    
    cd code/bi
    
    # 执行git pull
    if git pull; then
        log_info "代码拉取成功"
        
        # 2. 如果拉取成功，执行后续步骤
        cd ../../  # 回到当前目录
        
        # 复制当前目录下的launchers-standalone-1.0.0-SNAPSHOT到backup目录下
        if [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
            log_info "步骤2: 备份现有文件..."
            create_backup_dir
            backup_name=$(generate_backup_name)
            log_info "备份到 backup/${backup_name}"
            cp -r launchers-standalone-1.0.0-SNAPSHOT "backup/${backup_name}"
        else
            log_warn "launchers-standalone-1.0.0-SNAPSHOT目录不存在，跳过备份"
        fi
        
        # cd code，来到code目录
        log_info "步骤3: 进入code目录并打包..."
        cd code
        
        # 在bi文件夹中执行mvn -DskipTests package,打包jar包
        log_info "执行Maven打包..."
        cd bi
        if ! mvn -DskipTests package; then
            log_error "Maven打包失败"
            exit 1
        fi
        log_info "Maven打包成功"
        
        # 把bi/launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz移动到code目录
        log_info "步骤4: 移动jar包..."
        if [ -f "launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz" ]; then
            mv launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz ../
            log_info "jar包移动成功"
        else
            log_error "找不到jar包文件: launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
            exit 1
        fi
        
        # 在bi/webapp目录下执行start-fe-prod.sh
        log_info "步骤5: 构建前端..."
        cd webapp
        if [ -f "start-fe-prod.sh" ]; then
            chmod +x start-fe-prod.sh
            ./start-fe-prod.sh
            log_info "前端构建成功"
        else
            log_error "找不到start-fe-prod.sh脚本"
            exit 1
        fi
        
        # 把bi/webapp目录下的supersonic-webapp.tar.gz移动到code目录
        if [ -f "supersonic-webapp.tar.gz" ]; then
            mv supersonic-webapp.tar.gz ../
            log_info "前端包移动成功"
        else
            log_error "找不到supersonic-webapp.tar.gz文件"
            exit 1
        fi
        
        cd ..  # 回到code目录
        
        # 在code目录，解压supersonic-webapp.tar.gz，解压后重命名文件夹为webapp
        log_info "步骤6: 解压前端包..."
        if [ -f "supersonic-webapp.tar.gz" ]; then
            log_info "正在解压 supersonic-webapp.tar.gz..."
            
            # 先用gzip -d解压，再用tar -xf解包
            if gzip -d supersonic-webapp.tar.gz; then
                log_info "gzip解压成功"
                
                # 解压tar文件
                if tar -xf supersonic-webapp.tar; then
                    log_info "tar解包成功"
                    
                    # 查找解压后的目录并重命名为webapp
                    extracted_dir=$(tar -tf supersonic-webapp.tar 2>/dev/null | head -1 | cut -f1 -d"/")
                    if [ "$extracted_dir" != "webapp" ] && [ -n "$extracted_dir" ]; then
                        mv "$extracted_dir" webapp
                        log_info "重命名目录: $extracted_dir -> webapp"
                    fi
                    log_info "前端包解压成功"
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
        
        # 在code目录，解压launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz，解压完成后把webapp移动到launchers-standalone-1.0.0-SNAPSHOT目录下
        log_info "步骤7: 解压后端包..."
        if [ -f "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz" ]; then
            log_info "正在解压 launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz..."
            
            # 先用gzip -d解压，再用tar -xf解包
            if gzip -d launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz; then
                log_info "gzip解压成功"
                
                # 解压tar文件
                if tar -xf launchers-standalone-1.0.0-SNAPSHOT-bin.tar; then
                    log_info "tar解包成功"
                    log_info "后端包解压成功"
                    
                    # 把webapp移动到launchers-standalone-1.0.0-SNAPSHOT目录下
                    if [ -d "webapp" ] && [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
                        mv webapp launchers-standalone-1.0.0-SNAPSHOT/
                        log_info "webapp移动成功"
                    else
                        log_error "webapp或launchers-standalone-1.0.0-SNAPSHOT目录不存在"
                        log_info "当前目录内容:"
                        ls -la
                        exit 1
                    fi
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
        
        # 在launchers-standalone-1.0.0-SNAPSHOT/bin目录下执行命令"sed -i 's/\r$//' supersonic-start.sh"
        log_info "步骤8: 修复脚本格式..."
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
        
        # 停止端口为9080的服务
        log_info "步骤9: 停止现有服务..."
        stop_service_on_port 9080
        
        # cd ..
        cd ..
        
        # 删除文件夹launchers-standalone-1.0.0-SNAPSHOT
        log_info "步骤10: 更新部署文件..."
        if [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
            rm -rf launchers-standalone-1.0.0-SNAPSHOT
            log_info "旧版本删除成功"
        fi
        
        # 移动code/launchers-standalone-1.0.0-SNAPSHOT到当前文件夹中
        if [ -d "code/launchers-standalone-1.0.0-SNAPSHOT" ]; then
            mv code/launchers-standalone-1.0.0-SNAPSHOT ./
            log_info "新版本移动成功"
        else
            log_error "找不到新的launchers-standalone-1.0.0-SNAPSHOT目录"
            exit 1
        fi
        
        # cd launchers-standalone-1.0.0-SNAPSHOT/bin
        log_info "步骤11: 启动新服务..."
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