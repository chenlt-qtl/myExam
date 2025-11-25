# Get version from user input
# Write-Host "Please enter the version number:"
# $VERSION = Read-Host
# if ([string]::IsNullOrWhiteSpace($VERSION)) {
#     Write-Host "[ERROR] Version cannot be empty" -ForegroundColor Red
#     Read-Host "Press Enter to exit"
#     exit 1
# }
# Set version number
$VERSION = "3.6"
Write-Host "Building version: $VERSION"

# Set working directories
$PROJECT_DIR = "E:\workspace\DPS\bi-linde"
$OUTPUT_DIR = "E:\bi"
$CONFIG_DIR = "E:\bi\1"
$ZIP_PATH = "C:\Program Files (x86)\360\360zip\360zip.exe"

Write-Host "========================================"
Write-Host "Start building and deploying BI system"
Write-Host "========================================"
Write-Host ""

# Build Docker image
Write-Host "[Step 10] Building Docker image..."
Set-Location $OUTPUT_DIR
docker build -t chatbi .
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Docker image build failed" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host "[SUCCESS] Docker image build completed" -ForegroundColor Green
Write-Host ""

# Get image ID
Write-Host "[Step 11] Getting image information..."
$imageInfo = docker images chatbi --format "{{.ID}}"
if ($imageInfo) {
    $IMAGE_ID = $imageInfo.Trim()
    Write-Host "IMAGE_ID: $IMAGE_ID" -ForegroundColor Yellow
} else {
    Write-Host "[ERROR] Failed to get IMAGE_ID for chatbi image" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

# Step 12: Login to Aliyun registry
Write-Host "[Step 12] Logging into Aliyun registry..."
$registryUrl = "registry.cn-hangzhou.aliyuncs.com"
$username = "13110928487@163.com"
$password = "5710877."

Write-Host "Attempting to login to Aliyun registry..."
try {
    # Use PowerShell's secure string method to pass password
    $securePassword = ConvertTo-SecureString -String $password -AsPlainText -Force
    $credential = New-Object System.Management.Automation.PSCredential($username, $securePassword)

    Write-Host "Trying alternative login method..."
    docker login --username $username --password $password $registryUrl
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[ERROR] Alternative login method also failed" -ForegroundColor Red
        Read-Host "Press Enter to exit"
        exit 1
    }

    Write-Host "[SUCCESS] Logged into Aliyun registry" -ForegroundColor Green
} catch {
    Write-Host "[ERROR] Exception during login: $_" -ForegroundColor Red
    Write-Host "Trying direct password method..."
    docker login --username $username --password $password $registryUrl
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[ERROR] Direct login failed" -ForegroundColor Red
        Read-Host "Press Enter to exit"
        exit 1
    }
    Write-Host "[SUCCESS] Logged into Aliyun registry using direct method" -ForegroundColor Green
}Write-Host ""

# Step 13: Tag image for registry
Write-Host "[Step 13] Tagging image for registry..."
$targetImage = "$registryUrl/deepsense/chatbi:$VERSION"
Write-Host "Command: docker tag $IMAGE_ID $targetImage"

docker tag $IMAGE_ID $targetImage
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Failed to tag image" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host "[SUCCESS] Image tagged as: $targetImage" -ForegroundColor Green
Write-Host ""

# Step 14: Push image to registry
Write-Host "[Step 14] Pushing image to Aliyun registry..."
Write-Host "Command: docker push $targetImage"
docker push $targetImage
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Failed to push image to registry" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host "[SUCCESS] Image pushed to registry" -ForegroundColor Green
Write-Host ""


Write-Host ""
Write-Host "========================================"
Write-Host "Build and deployment completed successfully!"
Write-Host "Version: $VERSION"
Write-Host "IMAGE_ID: $IMAGE_ID"
Write-Host "Registry Image: $targetImage"
Write-Host "========================================"
Write-Host ""

# Keep window open
Read-Host "Press Enter to exit"