#!/bin/bash

# 设置脚本在遇到错误时立即退出
set -e

# 部署路径和备份时间戳
CURRENT_DATE_TIME=$(date +%Y%m%d-%H%M%S)
APP_DIR="/data/app"
APP_JAR="${APP_DIR}/Betta1_0.jar"
APP_BACKUP_DIR="${APP_DIR}/backup"
ENV_FILE="${APP_DIR}/betta.env"
APP_LOG="${APP_DIR}/Betta1_0.log"
STARTUP_TIMEOUT_SECONDS=60
STARTUP_CHECK_INTERVAL_SECONDS=2
WEB_DIR="/data/web/Betta1_0"
WEB_BACKUP_DIR="/data/web/backup"

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

# 部署开始时读取并校验配置，但在启动Java前不导出，避免运行配置传递给构建子进程。
load_application_environment() {
    local required_variables=(
        "BETTA_FRONT_BASE_URL"
        "ENG_PRONUNCIATION_ENABLED"
        "ENG_PRONUNCIATION_TENCENT_APP_ID"
        "ENG_PRONUNCIATION_TENCENT_SECRET_ID"
        "ENG_PRONUNCIATION_TENCENT_SECRET_KEY"
    )
    local variable_name

    if [ ! -r "${ENV_FILE}" ]; then
        log_error "环境配置文件不存在或不可读: ${ENV_FILE}"
        return 1
    fi

    unset BETTA_FRONT_BASE_URL
    unset ENG_PRONUNCIATION_ENABLED
    unset ENG_PRONUNCIATION_TENCENT_APP_ID
    unset ENG_PRONUNCIATION_TENCENT_SECRET_ID
    unset ENG_PRONUNCIATION_TENCENT_SECRET_KEY
    unset ENG_PRONUNCIATION_TENCENT_APPID
    unset ENG_PRONUNCIATION_TENCENT_SECRETID
    unset ENG_PRONUNCIATION_TENCENT_SECRETKEY

    # shellcheck disable=SC1090
    if ! source "${ENV_FILE}"; then
        log_error "环境配置文件读取失败: ${ENV_FILE}"
        return 1
    fi

    # 即使配置文件使用了export，也先撤销导出，直到真正启动Java。
    export -n BETTA_FRONT_BASE_URL 2>/dev/null || true
    export -n ENG_PRONUNCIATION_ENABLED 2>/dev/null || true
    export -n ENG_PRONUNCIATION_TENCENT_APP_ID 2>/dev/null || true
    export -n ENG_PRONUNCIATION_TENCENT_SECRET_ID 2>/dev/null || true
    export -n ENG_PRONUNCIATION_TENCENT_SECRET_KEY 2>/dev/null || true
    export -n ENG_PRONUNCIATION_TENCENT_APPID 2>/dev/null || true
    export -n ENG_PRONUNCIATION_TENCENT_SECRETID 2>/dev/null || true
    export -n ENG_PRONUNCIATION_TENCENT_SECRETKEY 2>/dev/null || true

    for variable_name in "${required_variables[@]}"; do
        if [ -z "${!variable_name:-}" ]; then
            log_error "环境变量未配置: ${variable_name}"
            return 1
        fi
    done
}

# 启动前确认日志可写，避免旧服务停止后才发现无法记录启动日志。
prepare_application_log() {
    if ! touch "${APP_LOG}" 2>/dev/null; then
        log_error "应用日志文件不可写: ${APP_LOG}"
        return 1
    fi
}

# 判断指定Java进程是否已经监听目标端口，兼容常见的lsof、ss和netstat环境。
process_listens_on_port() {
    local pid=$1
    local port=$2

    if command -v lsof >/dev/null 2>&1; then
        lsof -nP -a -p "${pid}" -iTCP:"${port}" -sTCP:LISTEN >/dev/null 2>&1
        return $?
    fi

    if command -v ss >/dev/null 2>&1; then
        ss -ltnp 2>/dev/null | grep -E "LISTEN.+:${port}([^0-9]|$).+pid=${pid}([^0-9]|$)" >/dev/null
        return $?
    fi

    if command -v netstat >/dev/null 2>&1; then
        netstat -tlnp 2>/dev/null | grep -E ":${port}([^0-9]|$).+LISTEN.+${pid}/" >/dev/null
        return $?
    fi

    log_error "缺少lsof、ss或netstat，无法确认端口监听状态"
    return 1
}

show_startup_log_tail() {
    log_error "应用启动日志末尾如下："
    tail -n 80 "${APP_LOG}" 2>/dev/null || log_warn "无法读取应用日志: ${APP_LOG}"
}

# 仅在启动Java时临时导出运行配置，避免敏感配置传递给其他部署子进程。
start_backend() {
    # Spring Boot 环境变量会移除属性名中的连字符，启动时转换为其可识别的规范名称。
    ENG_PRONUNCIATION_TENCENT_APPID="${ENG_PRONUNCIATION_TENCENT_APP_ID}"
    ENG_PRONUNCIATION_TENCENT_SECRETID="${ENG_PRONUNCIATION_TENCENT_SECRET_ID}"
    ENG_PRONUNCIATION_TENCENT_SECRETKEY="${ENG_PRONUNCIATION_TENCENT_SECRET_KEY}"
    export BETTA_FRONT_BASE_URL
    export ENG_PRONUNCIATION_ENABLED
    export ENG_PRONUNCIATION_TENCENT_APPID
    export ENG_PRONUNCIATION_TENCENT_SECRETID
    export ENG_PRONUNCIATION_TENCENT_SECRETKEY
    nohup java -Xms256M -Xmx512M -jar "${APP_JAR}" >> "${APP_LOG}" 2>&1 &
    STARTED_BACKEND_PID=$!
    export -n BETTA_FRONT_BASE_URL
    export -n ENG_PRONUNCIATION_ENABLED
    export -n ENG_PRONUNCIATION_TENCENT_APPID
    export -n ENG_PRONUNCIATION_TENCENT_SECRETID
    export -n ENG_PRONUNCIATION_TENCENT_SECRETKEY
    unset ENG_PRONUNCIATION_TENCENT_APPID
    unset ENG_PRONUNCIATION_TENCENT_SECRETID
    unset ENG_PRONUNCIATION_TENCENT_SECRETKEY
}

process_is_active() {
    local pid=$1
    local process_state

    if ! kill -0 "${pid}" 2>/dev/null; then
        return 1
    fi
    process_state=$(ps -o stat= -p "${pid}" 2>/dev/null) || return 1
    case "${process_state}" in
        *Z*) return 1 ;;
        *) return 0 ;;
    esac
}

# 停止本轮启动的指定进程，避免启动失败后残留进程继续占用端口或旧制品。
stop_backend_process() {
    local pid=$1
    local wait_count=0

    if ! process_is_active "${pid}"; then
        wait "${pid}" 2>/dev/null || true
        return 0
    fi

    kill -15 "${pid}" 2>/dev/null || true
    while [ "${wait_count}" -lt 5 ]; do
        if ! process_is_active "${pid}"; then
            wait "${pid}" 2>/dev/null || true
            return 0
        fi
        sleep 1
        ((wait_count++))
    done
    if ! process_is_active "${pid}"; then
        wait "${pid}" 2>/dev/null || true
        return 0
    fi

    log_warn "进程未响应SIGTERM，尝试强制终止，PID: ${pid}"
    kill -9 "${pid}" 2>/dev/null || true
    wait_count=0
    while [ "${wait_count}" -lt 5 ]; do
        if ! process_is_active "${pid}"; then
            wait "${pid}" 2>/dev/null || true
            return 0
        fi
        sleep 1
        ((wait_count++))
    done
    if ! process_is_active "${pid}"; then
        wait "${pid}" 2>/dev/null || true
        return 0
    fi

    return 1
}

# 在有限时间内同时确认新进程存活且已监听8080端口。
wait_for_backend_startup() {
    local pid=$1
    local elapsed_seconds=0

    while [ "${elapsed_seconds}" -lt "${STARTUP_TIMEOUT_SECONDS}" ]; do
        if ! kill -0 "${pid}" 2>/dev/null; then
            log_error "后端进程已退出，PID: ${pid}"
            show_startup_log_tail
            return 1
        fi

        if process_listens_on_port "${pid}" 8080; then
            log_info "后端进程已启动并监听8080端口，PID: ${pid}"
            return 0
        fi

        sleep "${STARTUP_CHECK_INTERVAL_SECONDS}"
        ((elapsed_seconds += STARTUP_CHECK_INTERVAL_SECONDS))
    done

    log_error "后端在${STARTUP_TIMEOUT_SECONDS}秒内未监听8080端口，PID: ${pid}"
    show_startup_log_tail
    return 1
}

# 生成唯一备份路径，同一秒重复部署时在名称后追加序号，避免覆盖已有备份。
generate_backup_path() {
    local base_path=$1
    local backup_path="${base_path}"
    local counter=1

    while [ -e "${backup_path}" ]; do
        backup_path="${base_path}-${counter}"
        ((counter++))
    done

    echo "${backup_path}"
}

# 恢复本轮部署前的后端状态；单个恢复命令失败时继续执行后续恢复并汇总结果。
rollback_backend() {
    local app_backup_path=$1
    local rollback_failed=0
    BACKEND_RESTORED=0

    if ! sudo rm -f "${APP_JAR}"; then
        log_error "清理未完整部署的jar包失败: ${APP_JAR}"
        rollback_failed=1
    fi

    if [ -n "${app_backup_path}" ]; then
        if [ -f "${app_backup_path}" ]; then
            if sudo mv "${app_backup_path}" "${APP_JAR}"; then
                BACKEND_RESTORED=1
                log_info "旧版本后端包恢复成功"
            else
                log_error "旧版本后端包恢复失败: ${app_backup_path}"
                rollback_failed=1
            fi
        else
            log_error "后端备份文件不存在，无法恢复: ${app_backup_path}"
            rollback_failed=1
        fi
    fi

    return "${rollback_failed}"
}

# 前端替换失败时恢复前端，并继续恢复本轮已替换的后端，避免只回滚一端。
rollback_frontend_and_backend() {
    local app_backup_path=$1
    local web_backup_path=$2
    local rollback_failed=0
    local web_target_cleared=1

    if ! sudo rm -rf "${WEB_DIR}"; then
        log_error "清理未完整部署的前端目录失败: ${WEB_DIR}"
        rollback_failed=1
        web_target_cleared=0
    fi

    if [ -n "${web_backup_path}" ]; then
        if [ ! -d "${web_backup_path}" ]; then
            log_error "前端备份目录不存在，无法恢复: ${web_backup_path}"
            rollback_failed=1
        elif [ "${web_target_cleared}" -eq 1 ]; then
            if sudo mv "${web_backup_path}" "${WEB_DIR}"; then
                log_info "旧版本前端文件恢复成功"
            else
                log_error "旧版本前端文件恢复失败: ${web_backup_path}"
                rollback_failed=1
            fi
        else
            log_error "正式前端目录清理失败，跳过移动备份以避免形成嵌套目录"
        fi
    fi

    if ! rollback_backend "${app_backup_path}"; then
        rollback_failed=1
    fi

    return "${rollback_failed}"
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

    # 必须在拉取、构建和替换制品前完成配置与日志校验。
    if ! load_application_environment; then
        exit 1
    fi
    if ! prepare_application_log; then
        exit 1
    fi

    # 2. 拉取最新代码
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
#        npm run build:prod
        NODE_OPTIONS="--max-old-space-size=1536" npm run build:prod

        # 在移动线上旧版本前同时校验前后端制品，避免构建产物不完整导致服务不可用。
        frontend_dist_dir="$(pwd)/dist"
        if [ ! -d "${frontend_dist_dir}" ]; then
            log_error "找不到前端文件"
            exit 1
        fi

        cd ..
        new_jar_path="$(pwd)/betta-admin/target/betta-admin.jar"
        if [ ! -f "${new_jar_path}" ]; then
            log_error "找不到jar包文件"
            exit 1
        fi

        log_info "步骤7: 备份并替换后端jar包..."
        app_backup_path=""
        sudo mkdir -p "${APP_BACKUP_DIR}"
        if [ -f "${APP_JAR}" ]; then
            app_backup_path=$(generate_backup_path "${APP_BACKUP_DIR}/Betta1_0.jar_${CURRENT_DATE_TIME}")
            sudo mv "${APP_JAR}" "${app_backup_path}"
            log_info "旧版本后端包已移动到: ${app_backup_path}"
        else
            log_warn "没有找到旧版本后端包，按首次部署继续"
        fi
        if ! sudo cp -f "${new_jar_path}" "${APP_JAR}"; then
            log_error "新jar包复制失败，开始恢复旧版本"
            if ! rollback_backend "${app_backup_path}"; then
                log_error "后端回滚未完整完成，请人工检查"
            fi
            exit 1
        fi
        log_info "jar包复制成功"

        log_info "步骤8: 备份并替换前端文件..."
        web_backup_path=""
        if ! sudo mkdir -p "${WEB_BACKUP_DIR}"; then
            log_error "创建前端备份目录失败，开始恢复后端"
            if ! rollback_backend "${app_backup_path}"; then
                log_error "后端回滚未完整完成，请人工检查"
            fi
            exit 1
        fi
        if [ -d "${WEB_DIR}" ]; then
            web_backup_path=$(generate_backup_path "${WEB_BACKUP_DIR}/Betta1_0_${CURRENT_DATE_TIME}")
            if ! sudo mv "${WEB_DIR}" "${web_backup_path}"; then
                log_error "旧版本前端文件备份失败，开始恢复后端"
                if ! rollback_backend "${app_backup_path}"; then
                    log_error "后端回滚未完整完成，请人工检查"
                fi
                exit 1
            fi
            log_info "旧版本前端文件已移动到: ${web_backup_path}"
        else
            log_warn "没有找到旧版本前端文件，按首次部署继续"
        fi
        if ! sudo mkdir -p "${WEB_DIR}" || ! sudo cp -rf "${frontend_dist_dir}/." "${WEB_DIR}/"; then
            log_error "新前端文件复制失败，开始恢复本次部署前的版本"
            if ! rollback_frontend_and_backend "${app_backup_path}" "${web_backup_path}"; then
                log_error "前后端回滚未完整完成，请人工检查"
            fi
            exit 1
        fi
        log_info "前端包复制成功"

        log_info "步骤13: 停止现有服务..."
        stop_service_on_port 8080

        log_info "步骤17-18: 启动新服务..."
        cd "${APP_DIR}"
        start_backend
        app_pid=${STARTED_BACKEND_PID}

        if ! wait_for_backend_startup "${app_pid}"; then
            log_error "新服务启动失败，开始停止新进程并恢复部署前制品"
            recovery_failed=0
            new_process_stopped=1

            if ! stop_backend_process "${app_pid}"; then
                log_error "新后端进程无法停止，PID: ${app_pid}"
                recovery_failed=1
                new_process_stopped=0
            fi

            if ! rollback_frontend_and_backend "${app_backup_path}" "${web_backup_path}"; then
                log_error "前后端制品未完整恢复，请人工检查"
                recovery_failed=1
            fi

            if [ -n "${app_backup_path}" ]; then
                if [ "${new_process_stopped}" -eq 1 ] && [ "${BACKEND_RESTORED:-0}" -eq 1 ] && [ -f "${APP_JAR}" ]; then
                    log_warn "正在启动恢复后的旧版后端服务"
                    start_backend
                    recovered_app_pid=${STARTED_BACKEND_PID}
                    if wait_for_backend_startup "${recovered_app_pid}"; then
                        log_warn "旧版后端服务已恢复；Nginx未重启"
                    else
                        recovery_failed=1
                        log_error "旧版后端服务恢复启动失败，请立即人工处理"
                        if ! stop_backend_process "${recovered_app_pid}"; then
                            log_error "恢复后的旧版后端进程也无法停止，PID: ${recovered_app_pid}"
                        fi
                    fi
                else
                    recovery_failed=1
                    log_error "旧版后端包或安全启动条件不满足，无法自动恢复服务，请立即人工处理"
                fi
            elif [ "${recovery_failed}" -eq 0 ]; then
                log_warn "首次部署前没有旧版后端，已恢复为无后端服务状态；Nginx未重启"
            else
                log_error "首次部署回滚未完整完成，请立即人工处理"
            fi

            if [ "${recovery_failed}" -ne 0 ]; then
                log_error "启动失败后的自动恢复未完整完成，请人工检查进程和部署制品"
            fi
            exit 1
        fi

        if ! docker restart nginx; then
            log_error "后端已启动，但Nginx重启失败"
            exit 1
        fi

        log_info "服务启动成功！"
        log_info "部署完成！"
        
    else
        # 3. 如果拉取失败，打印错误信息
        log_error "代码拉取失败，请检查网络连接或Git配置"
        exit 1
    fi
}

# 执行主函数
main "$@"
