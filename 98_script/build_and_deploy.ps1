# Get version from user input
Write-Host "Please enter the version number:"
$VERSION = Read-Host
if ([string]::IsNullOrWhiteSpace($VERSION)) {
    Write-Host "[ERROR] Version cannot be empty" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
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

# Check if 360Zip exists
if (!(Test-Path $ZIP_PATH)) {
    Write-Host "[ERROR] 360Zip not found at: $ZIP_PATH" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

# Step 1: Pull latest code
Write-Host "[Step 1] Pulling latest code..."
Set-Location $PROJECT_DIR
git pull
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Failed to pull code, please check network or Git configuration" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host "[SUCCESS] Code pulled successfully" -ForegroundColor Green
Write-Host ""

# Step 2: Start build process
Write-Host "[Step 2] Starting build process..."
Set-Location $PROJECT_DIR

# Execute Maven package
Write-Host "[Step 3] Executing Maven package..."
mvn -DskipTests package
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Maven package failed" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host "[SUCCESS] Maven package completed" -ForegroundColor Green
Write-Host ""

# Build frontend
Write-Host "[Step 4] Building frontend..."
Set-Location "webapp"
if (Test-Path "start-fe-prod.bat") {
    & ".\start-fe-prod.bat"
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[ERROR] Frontend build failed" -ForegroundColor Red
        Read-Host "Press Enter to exit"
        exit 1
    }
    Write-Host "[SUCCESS] Frontend build completed" -ForegroundColor Green
} else {
    Write-Host "[ERROR] start-fe-prod.bat script not found" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host ""

# Move backend package
Write-Host "[Step 5] Moving backend package..."
$backendPackage = "$PROJECT_DIR\launchers\standalone\target\launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
if (Test-Path $backendPackage) {
    # Create output directory if it doesn't exist
    if (!(Test-Path $OUTPUT_DIR)) {
        New-Item -ItemType Directory -Path $OUTPUT_DIR -Force | Out-Null
    }
    Copy-Item $backendPackage $OUTPUT_DIR
    Write-Host "[SUCCESS] Backend package moved to $OUTPUT_DIR" -ForegroundColor Green
} else {
    Write-Host "[ERROR] Backend package file not found" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host ""

# Move frontend package
Write-Host "[Step 6] Moving frontend package..."
$frontendPackage = "$PROJECT_DIR\webapp\supersonic-webapp.tar.gz"
if (Test-Path $frontendPackage) {
    Copy-Item $frontendPackage $OUTPUT_DIR
    Write-Host "[SUCCESS] Frontend package moved to $OUTPUT_DIR" -ForegroundColor Green
} else {
    Write-Host "[ERROR] Frontend package file not found" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host ""

# Switch to output directory and extract
Write-Host "[Step 7] Switching to output directory and extracting..."
Set-Location $OUTPUT_DIR

# Extract frontend package
Write-Host "Extracting frontend package..."
if (Test-Path "supersonic-webapp.tar.gz") {
    Write-Host "Found supersonic-webapp.tar.gz, extracting..."
    try {
        # Try using Windows built-in tar first (Windows 10 1803+)
        try {
            tar -xzf "supersonic-webapp.tar.gz"
            Write-Host "[SUCCESS] Frontend package extracted using Windows tar" -ForegroundColor Green
        } catch {
            Write-Host "Windows tar failed, trying 360Zip..."
            # First extract .gz to get .tar file
            Start-Process -FilePath $ZIP_PATH -ArgumentList "x", "supersonic-webapp.tar.gz", "-o.", "-y" -Wait -NoNewWindow
            if ($LASTEXITCODE -ne 0) {
                Write-Host "[ERROR] Failed to extract .gz file" -ForegroundColor Red
                Read-Host "Press Enter to exit"
                exit 1
            }

            # Check if .tar file exists and extract it
            if (Test-Path "supersonic-webapp.tar") {
                Write-Host "Found supersonic-webapp.tar, extracting..."
                Start-Process -FilePath $ZIP_PATH -ArgumentList "x", "supersonic-webapp.tar", "-o.", "-y" -Wait -NoNewWindow
                if ($LASTEXITCODE -ne 0) {
                    Write-Host "[ERROR] Failed to extract .tar file" -ForegroundColor Red
                    Read-Host "Press Enter to exit"
                    exit 1
                }
                Remove-Item "supersonic-webapp.tar"
                Write-Host "[SUCCESS] Frontend package extracted using 360Zip" -ForegroundColor Green
            } else {
                Write-Host "[ERROR] supersonic-webapp.tar not found after .gz extraction" -ForegroundColor Red
                Read-Host "Press Enter to exit"
                exit 1
            }
        }
    } catch {
        Write-Host "[ERROR] Exception during frontend extraction: $_" -ForegroundColor Red
        Read-Host "Press Enter to exit"
        exit 1
    }
} else {
    Write-Host "[ERROR] Frontend package file not found" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "Extracting backend package..."
if (Test-Path "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz") {
    Write-Host "Found launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz, extracting..."
    try {
        # Try using Windows built-in tar first (Windows 10 1803+)
        try {
            tar -xzf "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
            Write-Host "[SUCCESS] Backend package extracted using Windows tar" -ForegroundColor Green
        } catch {
            Write-Host "Windows tar failed, trying 360Zip..."
            # First extract .gz to get .tar file
            Start-Process -FilePath $ZIP_PATH -ArgumentList "x", "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz", "-o.", "-y" -Wait -NoNewWindow
            if ($LASTEXITCODE -ne 0) {
                Write-Host "[ERROR] Failed to extract .gz file" -ForegroundColor Red
                Read-Host "Press Enter to exit"
                exit 1
            }

            # Check if .tar file exists and extract it
            if (Test-Path "launchers-standalone-1.0.0-SNAPSHOT-bin.tar") {
                Write-Host "Found launchers-standalone-1.0.0-SNAPSHOT-bin.tar, extracting..."
                Start-Process -FilePath $ZIP_PATH -ArgumentList "x", "launchers-standalone-1.0.0-SNAPSHOT-bin.tar", "-o.", "-y" -Wait -NoNewWindow
                if ($LASTEXITCODE -ne 0) {
                    Write-Host "[ERROR] Failed to extract .tar file" -ForegroundColor Red
                    Read-Host "Press Enter to exit"
                    exit 1
                }
                Remove-Item "launchers-standalone-1.0.0-SNAPSHOT-bin.tar"
                Write-Host "[SUCCESS] Backend package extracted using 360Zip" -ForegroundColor Green
            } else {
                Write-Host "[ERROR] launchers-standalone-1.0.0-SNAPSHOT-bin.tar not found after .gz extraction" -ForegroundColor Red
                Read-Host "Press Enter to exit"
                exit 1
            }
        }
    } catch {
        Write-Host "[ERROR] Exception during backend extraction: $_" -ForegroundColor Red
        Read-Host "Press Enter to exit"
        exit 1
    }
} else {
    Write-Host "[ERROR] Backend package file not found" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host ""

# Rename and move webapp
Write-Host "[Step 8] Organizing extracted files..."
if (Test-Path "supersonic-webapp") {
    Rename-Item "supersonic-webapp" "webapp"
    if (Test-Path "launchers-standalone-1.0.0-SNAPSHOT") {
        Move-Item "webapp" "launchers-standalone-1.0.0-SNAPSHOT\"
        Write-Host "[SUCCESS] webapp moved" -ForegroundColor Green
    } else {
        Write-Host "[ERROR] launchers-standalone-1.0.0-SNAPSHOT directory not found" -ForegroundColor Red
        Read-Host "Press Enter to exit"
        exit 1
    }
} else {
    Write-Host "[ERROR] supersonic-webapp directory not found" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host ""

# Copy configuration files
Write-Host "[Step 9] Copying configuration files..."
$yamlFiles = "$CONFIG_DIR\*.yaml"
if (Test-Path $yamlFiles) {
    # Create conf directory if it doesn't exist
    $confDir = "$OUTPUT_DIR\launchers-standalone-1.0.0-SNAPSHOT\conf"
    if (!(Test-Path $confDir)) {
        New-Item -ItemType Directory -Path $confDir -Force | Out-Null
    }
    Copy-Item $yamlFiles $confDir -Force
    Write-Host "[SUCCESS] YAML configuration files copied" -ForegroundColor Green
} else {
    Write-Host "[WARNING] YAML configuration files not found in $CONFIG_DIR" -ForegroundColor Yellow
}

$shFiles = "$CONFIG_DIR\*.sh"
if (Test-Path $shFiles) {
    # Create bin directory if it doesn't exist
    $binDir = "$OUTPUT_DIR\launchers-standalone-1.0.0-SNAPSHOT\bin"
    if (!(Test-Path $binDir)) {
        New-Item -ItemType Directory -Path $binDir -Force | Out-Null
    }
    Copy-Item $shFiles $binDir -Force
    Write-Host "[SUCCESS] SH script files copied" -ForegroundColor Green
} else {
    Write-Host "[WARNING] SH script files not found in $CONFIG_DIR" -ForegroundColor Yellow
}
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

# Step 15: Clean up temporary files
Write-Host "[Step 15] Cleaning up temporary files..."
Set-Location $OUTPUT_DIR

# Remove backend package file
$backendTarFile = "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
if (Test-Path $backendTarFile) {
    Write-Host "Command: Remove-Item `"$backendTarFile`""
    Remove-Item $backendTarFile -Force
    Write-Host "[SUCCESS] Removed $backendTarFile" -ForegroundColor Green
} else {
    Write-Host "[WARNING] $backendTarFile not found" -ForegroundColor Yellow
}

# Remove frontend package file
$frontendTarFile = "supersonic-webapp.tar.gz"
if (Test-Path $frontendTarFile) {
    Write-Host "Command: Remove-Item `"$frontendTarFile`""
    Remove-Item $frontendTarFile -Force
    Write-Host "[SUCCESS] Removed $frontendTarFile" -ForegroundColor Green
} else {
    Write-Host "[WARNING] $frontendTarFile not found" -ForegroundColor Yellow
}

# Remove extracted directory
$extractedDir = "launchers-standalone-1.0.0-SNAPSHOT"
if (Test-Path $extractedDir) {
    Write-Host "Command: Remove-Item `"$extractedDir`" -Recurse -Force"
    Remove-Item $extractedDir -Recurse -Force
    Write-Host "[SUCCESS] Removed $extractedDir directory" -ForegroundColor Green
} else {
    Write-Host "[WARNING] $extractedDir directory not found" -ForegroundColor Yellow
}

Write-Host "[SUCCESS] Cleanup completed" -ForegroundColor Green
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