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


# 主函数
main() {
    log_info "开始解压文件..."

    cd /hcdata/chatbi/code
    pwd
    
    # 1. 清理旧文件，sudo删除文件夹code/launchers-standalone-1.0.0-SNAPSHOT，code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz,code/launchers-standalone-1.0.0-SNAPSHOT-bin.tar,code/supersonic-webapp.tar.gz,code/supersonic-webapp.tar
    log_info "步骤1: 清理旧文件..."
    
    # 删除文件夹
    if [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
        sudo rm -rf launchers-standalone-1.0.0-SNAPSHOT
        log_info "旧版本文件夹删除成功"
    else
        log_info "没有找到旧版本文件夹，跳过删除"
    fi
    
    # 删除后端tar.gz文件
    if [ -f "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz" ]; then
        sudo rm -f launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz
        log_info "旧版本后端包删除成功"
    else
        log_info "没有找到旧版本后端包，跳过删除"
    fi
    
    # 删除后端tar文件
    if [ -f "launchers-standalone-1.0.0-SNAPSHOT-bin.tar" ]; then
        sudo rm -f launchers-standalone-1.0.0-SNAPSHOT-bin.tar
        log_info "旧版本后端tar文件删除成功"
    else
        log_info "没有找到旧版本后端tar文件，跳过删除"
    fi
    
    # 删除前端tar.gz文件
    if [ -f "supersonic-webapp.tar.gz" ]; then
        sudo rm -f supersonic-webapp.tar.gz
        log_info "旧版本前端包删除成功"
    else
        log_info "没有找到旧版本前端包，跳过删除"
    fi
    
    # 删除前端tar文件
    if [ -f "supersonic-webapp.tar" ]; then
        sudo rm -f supersonic-webapp.tar
        log_info "旧版本前端tar文件删除成功"
    else
        log_info "没有找到旧版本前端tar文件，跳过删除"
    fi
    

        
    # 7. 把code/bi/webapp目录下的supersonic-webapp.tar.gz复制到code目录
    if [ -f "bi/webapp/supersonic-webapp.tar.gz" ]; then
        sudo cp bi/webapp/supersonic-webapp.tar.gz .
        log_info "前端包复制成功"
    else
        log_error "找不到supersonic-webapp.tar.gz文件"
        exit 1
    fi

    # 8. 把code/bi/launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz复制到code目录
    log_info "步骤8: 复制jar包..."
    if [ -f "bi/launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz" ]; then
        sudo cp bi/launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz .
        log_info "jar包复制成功"
    else
        log_error "找不到jar包文件: launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
        exit 1
    fi

    # 9. 解压先用gzip -d,再用tar -xf。
    # 10. 解压code/supersonic-webapp.tar.gz，解压后重命名文件夹为webapp
    log_info "步骤9-10: 解压前端包..."
    if [ -f "supersonic-webapp.tar.gz" ]; then
        log_info "正在解压 supersonic-webapp.tar.gz..."

        # 先用gzip -d解压，再用tar -xf解包
        if sudo gzip -d supersonic-webapp.tar.gz; then
            log_info "gzip解压成功"

            # 解压tar文件
            if sudo tar -xf supersonic-webapp.tar; then
                log_info "tar解包成功"

                # 重命名为webapp
                sudo mv supersonic-webapp webapp
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
        if sudo gzip -d launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz; then
            log_info "gzip解压成功"

            # 解压tar文件
            if sudo tar -xf launchers-standalone-1.0.0-SNAPSHOT-bin.tar; then
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
        sudo mv webapp launchers-standalone-1.0.0-SNAPSHOT/
        log_info "webapp移动成功"
    else
        log_error "webapp或launchers-standalone-1.0.0-SNAPSHOT目录不存在"
        log_info "当前目录内容:"
        ls -la
        exit 1
    fi

    # 12. 在code/launchers-standalone-1.0.0-SNAPSHOT/bin目录下执行命令"sed -i 's/\r$//' supersonic-start.sh"
    log_info "步骤12: 修复脚本格式..."
    if [ -f "launchers-standalone-1.0.0-SNAPSHOT/bin/supersonic-start.sh" ]; then
        cd launchers-standalone-1.0.0-SNAPSHOT/bin
        sudo sed -i 's/\r$//' supersonic-start.sh
        sudo chmod +x supersonic-start.sh
        cd ../..
        log_info "脚本格式修复成功"
    else
        log_error "找不到supersonic-start.sh脚本"
        exit 1
    fi



    if [ $? -eq 0 ]; then
        log_info "解压成功！"
    else
        log_error "解压失败"
        exit 1
    fi

}

# 执行主函数
main "$@"