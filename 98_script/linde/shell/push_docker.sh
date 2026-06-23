#!/bin/sh
set -u

VERSION="${VERSION:-8.5}"
OUTPUT_DIR="${OUTPUT_DIR:-/Users/unagi/Documents/file/bi}"
REGISTRY_URL="${REGISTRY_URL:-registry.cn-hangzhou.aliyuncs.com}"
REGISTRY_NAMESPACE="${REGISTRY_NAMESPACE:-deepsense}"
IMAGE_NAME="${IMAGE_NAME:-chatbi}"
BASE_IMAGE="${BASE_IMAGE:-}"
BASE_IMAGE_CANDIDATES="${BASE_IMAGE_CANDIDATES:-docker.1ms.run/library/eclipse-temurin:21-jre-jammy docker.m.daocloud.io/library/eclipse-temurin:21-jre-jammy eclipse-temurin:21-jre-jammy}"
DOCKER_PLATFORM="${DOCKER_PLATFORM:-linux/amd64}"
ALIYUN_USERNAME="${ALIYUN_USERNAME:-13110928487@163.com}"
ALIYUN_PASSWORD="${ALIYUN_PASSWORD:-5710877.}"

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

echo "Building version: $VERSION"
echo "========================================"
echo "Start building and deploying BI system"
echo "========================================"
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
