#!/bin/sh
set -u

VERSION="${VERSION:-8.5}"
PROJECT_DIR="${PROJECT_DIR:-/Users/unagi/Documents/workspace/CHAT-BI/bi-linde}"
OUTPUT_DIR="${OUTPUT_DIR:-/Users/unagi/Documents/file/bi}"
CONFIG_DIR="${CONFIG_DIR:-/Users/unagi/Documents/file/bi/1}"
REGISTRY_URL="${REGISTRY_URL:-registry.cn-hangzhou.aliyuncs.com}"
REGISTRY_NAMESPACE="${REGISTRY_NAMESPACE:-deepsense}"
IMAGE_NAME="${IMAGE_NAME:-chatbi}"
BASE_IMAGE="${BASE_IMAGE:-}"
BASE_IMAGE_CANDIDATES="${BASE_IMAGE_CANDIDATES:-docker.1ms.run/library/eclipse-temurin:21-jre-jammy docker.m.daocloud.io/library/eclipse-temurin:21-jre-jammy eclipse-temurin:21-jre-jammy}"
DOCKER_PLATFORM="${DOCKER_PLATFORM:-linux/amd64}"
ALIYUN_USERNAME="${ALIYUN_USERNAME:-13110928487@163.com}"
ALIYUN_PASSWORD="${ALIYUN_PASSWORD:-5710877.}"

# setup_java_home() {
#     if [ -n "${JAVA_HOME:-}" ] && [ -x "$JAVA_HOME/bin/java" ]; then
#         return 0
#     fi

#     for java_home in \
#         /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home \
#         /opt/homebrew/Cellar/openjdk@21/*/libexec/openjdk.jdk/Contents/Home \
#         /usr/local/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home \
#         /Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home \
#         /Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home
#     do
#         if [ -x "$java_home/bin/java" ]; then
#             JAVA_HOME="$java_home"
#             export JAVA_HOME
#             PATH="$JAVA_HOME/bin:$PATH"
#             export PATH
#             return 0
#         fi
#     done

#     fail "JDK 21 not found. Please install it or set JAVA_HOME before running this script."
# }

success() {
    printf '[SUCCESS] %s\n' "$1"
}

warning() {
    printf '[WARNING] %s\n' "$1"
}

fail() {
    printf '[ERROR] %s\n' "$1" >&2
    exit 1
}

run_step() {
    "$@"
    status=$?
    if [ "$status" -ne 0 ]; then
        fail "Command failed: $*"
    fi
}

build_and_push_image() {
    target_image="$1"

    if [ -n "$BASE_IMAGE" ]; then
        candidates="$BASE_IMAGE"
    else
        candidates="$BASE_IMAGE_CANDIDATES"
    fi

    for candidate in $candidates; do
        echo "Using BASE_IMAGE: $candidate"
        echo "Using DOCKER_PLATFORM: $DOCKER_PLATFORM"
        echo "Command: docker buildx build --platform $DOCKER_PLATFORM --build-arg BASE_IMAGE=$candidate -t $target_image --push ."
        docker buildx build --platform "$DOCKER_PLATFORM" --build-arg "BASE_IMAGE=$candidate" -t "$target_image" --push .
        status=$?
        if [ "$status" -eq 0 ]; then
            BASE_IMAGE="$candidate"
            success "Docker image built and pushed"
            return 0
        fi
        warning "Docker build failed with BASE_IMAGE=$candidate, trying next candidate"
    done

    fail "Docker build failed for all BASE_IMAGE candidates"
}

copy_glob() {
    pattern="$1"
    target_dir="$2"
    shift 2

    if [ ! -d "$CONFIG_DIR" ]; then
        return 1
    fi

    file_count="$(find "$CONFIG_DIR" -maxdepth 1 -type f -name "$pattern" -print | wc -l | tr -d ' ')"
    if [ "$file_count" -eq 0 ]; then
        return 1
    fi

    mkdir -p "$target_dir"
    find "$CONFIG_DIR" -maxdepth 1 -type f -name "$pattern" -exec cp {} "$target_dir/" \;

    if [ "$#" -gt 0 ]; then
        "$@" "$target_dir"
    fi
}

make_scripts_executable() {
    target_dir="$1"
    chmod +x "$target_dir"/*.sh
}

echo "Building version: $VERSION"
echo "========================================"
echo "Start building and deploying BI system"
echo "========================================"
echo

# setup_java_home
# echo "Using JAVA_HOME: $JAVA_HOME"
# java -version
# echo

echo "[Step 15] Cleaning up temporary files..."
run_step cd "$OUTPUT_DIR"

backend_tar_file="launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
if [ -f "$backend_tar_file" ]; then
    echo "Command: rm -f \"$backend_tar_file\""
    run_step rm -f "$backend_tar_file"
    success "Removed $backend_tar_file"
else
    warning "$backend_tar_file not found"
fi

frontend_tar_file="supersonic-webapp.tar.gz"
if [ -f "$frontend_tar_file" ]; then
    echo "Command: rm -f \"$frontend_tar_file\""
    run_step rm -f "$frontend_tar_file"
    success "Removed $frontend_tar_file"
else
    warning "$frontend_tar_file not found"
fi

extracted_dir="launchers-standalone-1.0.0-SNAPSHOT"
if [ -d "$extracted_dir" ]; then
    echo "Command: rm -rf \"$extracted_dir\""
    run_step rm -rf "$extracted_dir"
    success "Removed $extracted_dir directory"
else
    warning "$extracted_dir directory not found"
fi

success "Cleanup completed"
echo

echo "[Step 1] Pulling latest code..."
run_step cd "$PROJECT_DIR"
run_step git pull
success "Code pulled successfully"
echo

echo "[Step 2] Starting build process..."
run_step cd "$PROJECT_DIR"

echo "[Step 3] Executing Maven package..."
run_step mvn -DskipTests package
success "Maven package completed"
echo

echo "[Step 4] Building frontend..."
run_step cd "$PROJECT_DIR/webapp"
if [ ! -f "start-fe-prod.sh" ]; then
    if [ -f "start-fe-prod.bat" ]; then
        fail "start-fe-prod.bat exists, but shell cannot run .bat files directly. Please add start-fe-prod.sh."
    fi
    fail "start-fe-prod.sh script not found"
fi

chmod +x start-fe-prod.sh
run_step ./start-fe-prod.sh
success "Frontend build completed"
printf '\n'

echo "[Step 5] Moving backend package..."
backend_package="$PROJECT_DIR/launchers/standalone/target/launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
if [ -f "$backend_package" ]; then
    mkdir -p "$OUTPUT_DIR"
    run_step cp "$backend_package" "$OUTPUT_DIR/"
    success "Backend package moved to $OUTPUT_DIR"
else
    fail "Backend package file not found: $backend_package"
fi
echo

echo "[Step 6] Moving frontend package..."
frontend_package="$PROJECT_DIR/webapp/supersonic-webapp.tar.gz"
if [ -f "$frontend_package" ]; then
    run_step cp "$frontend_package" "$OUTPUT_DIR/"
    success "Frontend package moved to $OUTPUT_DIR"
else
    fail "Frontend package file not found: $frontend_package"
fi
echo

echo "[Step 7] Switching to output directory and extracting..."
run_step cd "$OUTPUT_DIR"

echo "Extracting frontend package..."
if [ -f "supersonic-webapp.tar.gz" ]; then
    run_step tar -xzf "supersonic-webapp.tar.gz"
    success "Frontend package extracted"
else
    fail "Frontend package file not found"
fi

echo "Extracting backend package..."
if [ -f "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz" ]; then
    run_step tar -xzf "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
    success "Backend package extracted"
else
    fail "Backend package file not found"
fi
echo

echo "[Step 8] Organizing extracted files..."
if [ -d "supersonic-webapp" ]; then
    [ ! -e "webapp" ] || fail "webapp already exists in $OUTPUT_DIR"
    run_step mv "supersonic-webapp" "webapp"
    if [ -d "launchers-standalone-1.0.0-SNAPSHOT" ]; then
        run_step mv "webapp" "launchers-standalone-1.0.0-SNAPSHOT/"
        success "webapp moved"
    else
        fail "launchers-standalone-1.0.0-SNAPSHOT directory not found"
    fi
else
    fail "supersonic-webapp directory not found"
fi
echo

echo "[Step 9] Copying configuration files..."
conf_dir="$OUTPUT_DIR/launchers-standalone-1.0.0-SNAPSHOT/conf"
if copy_glob "*.yaml" "$conf_dir"; then
    success "YAML configuration files copied"
else
    warning "YAML configuration files not found in $CONFIG_DIR"
fi

bin_dir="$OUTPUT_DIR/launchers-standalone-1.0.0-SNAPSHOT/bin"
if copy_glob "*.sh" "$bin_dir" make_scripts_executable; then
    success "SH script files copied"
else
    warning "SH script files not found in $CONFIG_DIR"
fi
echo

echo "[Step 12] Logging into Aliyun registry..."
echo "Attempting to login to Aliyun registry..."
printf '%s' "$ALIYUN_PASSWORD" | docker login --username "$ALIYUN_USERNAME" --password-stdin "$REGISTRY_URL"
if [ "$?" -ne 0 ]; then
    fail "Failed to login to Aliyun registry"
fi
success "Logged into Aliyun registry"
echo

target_image="$REGISTRY_URL/$REGISTRY_NAMESPACE/$IMAGE_NAME:$VERSION"

echo "[Step 10] Building and pushing Docker image..."
run_step cd "$OUTPUT_DIR"
build_and_push_image "$target_image"
echo

echo
echo "========================================"
echo "Build and deployment completed successfully!"
echo "Version: $VERSION"
echo "Platform: $DOCKER_PLATFORM"
echo "Base Image: $BASE_IMAGE"
echo "Registry Image: $target_image"
echo "========================================"
