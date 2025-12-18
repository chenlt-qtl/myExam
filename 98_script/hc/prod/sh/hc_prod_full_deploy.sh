#!/bin/bash

# 设置脚本在遇到错误时立即退出
set -e

# 获取当前日期和时间
CURRENT_DATE_TIME=$(date +%Y%m%d-%H%M)

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

# 解析参数，获取要执行的脚本索引
parse_script_indices() {
    local param="$1"
    
    if [ -z "$param" ]; then
        # 参数为空，执行所有脚本
        echo "1 2 3 4 5"
        return
    fi
    
    # 移除空格并按逗号分割
    param=$(echo "$param" | tr -d ' ')
    
    local valid_indices=""
    IFS=',' read -ra indices <<< "$param"
    for index in "${indices[@]}"; do
        # 验证索引是否有效
        if echo "$index" | grep -qE '^[1-5]$'; then
            if [ -z "$valid_indices" ]; then
                valid_indices="$index"
            else
                valid_indices="$valid_indices $index"
            fi
        else
            log_error "无效的脚本索引: $index (请输入1-5之间的数字)"
            exit 1
        fi
    done
    
    if [ -z "$valid_indices" ]; then
        log_error "没有有效的脚本索引"
        exit 1
    fi
    
    echo "$valid_indices"
}

# 检查指定的脚本文件是否存在
check_scripts() {
    local indices="$1"
    
    # 定义所有脚本
    local script1="./a1_hc_prod_package_backend.sh"
    local script2="./a2_hc_prod_package_frontend.sh" 
    local script3="./a3_hc_prod_unzip.sh"
    local script4="./a4_hc_prod_backup.sh"
    local script5="./a5_hc_prod_deploy.sh"
    
    log_info "检查脚本文件..."
    for index in $indices; do
        local script=""
        case $index in
            1) script="$script1" ;;
            2) script="$script2" ;;
            3) script="$script3" ;;
            4) script="$script4" ;;
            5) script="$script5" ;;
        esac
        
        if [ ! -f "$script" ]; then
            log_error "脚本文件不存在: $script"
            exit 1
        else
            log_info "✓ $script"
        fi
    done
}

# 执行单个脚本
execute_script() {
    local script_name=$1
    
    log_step "执行脚本: ${script_name}"
    
    # 确保脚本有执行权限
    chmod +x "${script_name}"
    
    # 执行脚本
    if bash "${script_name}"; then
        log_info "✓ ${script_name} 执行成功"
        echo "----------------------------------------"
    else
        log_error "✗ ${script_name} 执行失败"
        exit 1
    fi
}

# 主函数
main() {
    local script_indices
    script_indices=($(parse_script_indices "$1"))
    
    log_info "开始执行HC生产环境部署流程..."
    log_info "开始时间: $(date)"
    log_info "当前目录: $(pwd)"
    if [ -n "$1" ]; then
        log_info "执行脚本索引: $1"
    else
        log_info "执行所有脚本"
    fi
    echo "========================================"
    
    # 检查脚本文件
    check_scripts "${script_indices[*]}"
    echo "========================================"
    
    # 所有可用的脚本
    local script1="./a1_hc_prod_package_backend.sh"
    local script2="./a2_hc_prod_package_frontend.sh"
    local script3="./a3_hc_prod_unzip.sh"
    local script4="./a4_hc_prod_backup.sh"
    local script5="./a5_hc_prod_deploy.sh"
    
    local total_scripts=0
    for index in ${script_indices[*]}; do
        ((total_scripts++))
    done
    
    local current_step=0
    
    for index in ${script_indices[*]}; do
        ((current_step++))
        local script=""
        case $index in
            1) script="$script1" ;;
            2) script="$script2" ;;
            3) script="$script3" ;;
            4) script="$script4" ;;
            5) script="$script5" ;;
        esac
        log_step "第 ${current_step}/${total_scripts} 步"
        execute_script "${script}"
    done
    
    echo "========================================"
    log_info "所有脚本执行完成！"
    log_info "结束时间: $(date)"
}

# 执行主函数
main "$@"