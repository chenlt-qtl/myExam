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

# 主函数
main() {
    log_info "开始打包前端..."

    cd /hcdata/chat
    
    # 2. 在code/bi文件夹中执行sudo git pull,拉取最新代码,使用的是https协议，Username是"da.mu",Password是"Aifa@Xmhc011"
    log_info "步骤2: 拉取最新代码..."
    
    # 检查code/bi目录是否存在
    if [ ! -d "code/bi" ]; then
        log_error "code/bi目录不存在，请检查路径"
        exit 1
    fi
    
    cd code/bi
    
    # 配置git凭据
    log_info "配置Git凭据..."
    git config --global credential.helper store
    echo "https://da.mu:Aifa@Xmhc011@github.com" > ~/.git-credentials
    
    # 执行git pull，强制覆盖本地代码
    if sudo git fetch --all && sudo git reset --hard origin/$(sudo git branch --show-current) && sudo git pull; then
        log_info "代码拉取成功"
        
        # 6. 在code/bi/webapp目录下执行sudo start-fe-prod.sh，打包前端包
        log_info "步骤6: 打包前端包..."
        cd webapp
        if [ -f "start-fe-prod.sh" ]; then
            sudo chmod +x start-fe-prod.sh
            sudo ./start-fe-prod.sh
            log_info "前端打包成功"
        else
            log_error "找不到start-fe-prod.sh脚本"
            exit 1
        fi

        
        if [ $? -eq 0 ]; then
            log_info "前端打包成功！"
        else
            log_error "前端打包失败"
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