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


# 生成唯一的备份文件夹名
generate_backup_name() {
    local base_name="backup-${CURRENT_DATE_TIME}"
    local counter=1
    local backup_name="${base_name}"
    
    while [ -d "/tmp/home/hcadmin/backup/${backup_name}" ]; do
        backup_name="${base_name}-${counter}"
        ((counter++))
    done
    
    echo "${backup_name}"
}

# 主函数
main() {
      log_info "开始执行文件准备脚本..."

      cd /hcdata/chatbi/tmp
      pwd

      # 删除文件夹

      sudo rm -rf /hcdata/chatbi/tmp/*
      log_info "旧版本文件夹删除成功"

      log_info "复制文件..."
      sudo cp /tmp/supersonic-webapp.tar.gz .
      sudo cp /tmp/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz .
      log_info "包复制成功"

      # 9. 解压先用gzip -d,再用tar -xf。
      # 10. 解压code/supersonic-webapp.tar.gz，解压后重命名文件夹为webapp
      pwd
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
          cd ../../..
          log_info "脚本格式修复成功"
      else
          log_error "找不到supersonic-start.sh脚本"
          exit 1
      fi


      log_info "步骤3: 备份旧文件..."
      cd ..
      pwd
      # 3. 备份旧文件：sudo复制launchers-standalone-1.0.0-SNAPSHOT到backup目录下，重命名为backup-[yyyyMMdd-hh:mm]，如果文件夹已存在，则在后面加上序号。
      if [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
          backup_name=$(generate_backup_name)
          log_info "备份到 backup/${backup_name}"
          sudo cp -r launchers-standalone-1.0.0-SNAPSHOT "backup/${backup_name}"
      else
          log_warn "launchers-standalone-1.0.0-SNAPSHOT目录不存在，跳过备份"
      fi

      if [ $? -eq 0 ]; then
          log_info "文件准备成功！"
          log_info "文件准备完成！"
      else
          log_error "文件准备失败"
          exit 1
      fi

}

# 执行主函数
main "$@"