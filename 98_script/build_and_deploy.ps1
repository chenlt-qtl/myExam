# Set working directories
$PROJECT_DIR = "E:\workspace\DPS\bi-linde"
$OUTPUT_DIR = "E:\bi"
$CONFIG_DIR = "E:\bi\1"

# Add error handling to prevent window from closing
$ErrorActionPreference = "Stop"

try {
    Write-Host "========================================"
    Write-Host "Start building and deploying BI system"
    Write-Host "========================================"
    Write-Host ""

    # Step 1: Pull latest code
    Write-Host "[Step 1] Pulling latest code in E:\workspace\DPS\bi-linde..."
    
    # Check if directory exists
    if (!(Test-Path $PROJECT_DIR)) {
        Write-Host "[ERROR] Project directory not found: $PROJECT_DIR" -ForegroundColor Red
        throw "Project directory not found"
    }
    
    Set-Location $PROJECT_DIR
    Write-Host "Current directory: $(Get-Location)"
    
    git pull
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[ERROR] Failed to pull code, please check network or Git configuration" -ForegroundColor Red
        throw "Git pull failed"
    }
    Write-Host "[SUCCESS] Code pulled successfully" -ForegroundColor Green
    Write-Host ""

    # Step 2: Execute Maven package
    Write-Host "[Step 2] Executing Maven package in E:\workspace\DPS\bi-linde..."
    Set-Location $PROJECT_DIR
    Write-Host "Current directory: $(Get-Location)"
    
    mvn -DskipTests package
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[ERROR] Maven package failed" -ForegroundColor Red
        throw "Maven package failed"
    }
    Write-Host "[SUCCESS] Maven package completed" -ForegroundColor Green
    Write-Host ""

    # Step 3: Build frontend
    Write-Host "[Step 3] Building frontend in webapp directory..."
    Set-Location "$PROJECT_DIR\webapp"
    Write-Host "Current directory: $(Get-Location)"
    
    if (Test-Path "start-fe-prod.bat") {
        & ".\start-fe-prod.bat"
        if ($LASTEXITCODE -ne 0) {
            Write-Host "[ERROR] Frontend build failed" -ForegroundColor Red
            throw "Frontend build failed"
        }
        Write-Host "[SUCCESS] Frontend build completed" -ForegroundColor Green
    } else {
        Write-Host "[ERROR] start-fe-prod.bat script not found" -ForegroundColor Red
        throw "start-fe-prod.bat script not found"
    }
    Write-Host ""

    # Step 4: Move backend package
    Write-Host "[Step 4] Moving backend package to E:\bi..."
    $backendPackage = "$PROJECT_DIR\launchers\standalone\target\launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
    Write-Host "Checking backend package: $backendPackage"
    
    if (Test-Path $backendPackage) {
        # Create output directory if it doesn't exist
        if (!(Test-Path $OUTPUT_DIR)) {
            New-Item -ItemType Directory -Path $OUTPUT_DIR -Force | Out-Null
            Write-Host "Created output directory: $OUTPUT_DIR"
        }
        Move-Item $backendPackage $OUTPUT_DIR -Force
        Write-Host "[SUCCESS] Backend package moved to E:\bi" -ForegroundColor Green
    } else {
        Write-Host "[ERROR] Backend package file not found" -ForegroundColor Red
        throw "Backend package file not found"
    }
    Write-Host ""

    # Step 5: Move frontend package
    Write-Host "[Step 5] Moving frontend package to E:\bi..."
    $frontendPackage = "$PROJECT_DIR\webapp\supersonic-webapp.tar.gz"
    Write-Host "Checking frontend package: $frontendPackage"
    
    if (Test-Path $frontendPackage) {
        Move-Item $frontendPackage $OUTPUT_DIR -Force
        Write-Host "[SUCCESS] Frontend package moved to E:\bi" -ForegroundColor Green
    } else {
        Write-Host "[ERROR] Frontend package file not found" -ForegroundColor Red
        throw "Frontend package file not found"
    }
    Write-Host ""

    # Step 6: Switch to E:\bi and extract packages
    Write-Host "[Step 6] Switching to E:\bi and extracting packages..."
    Set-Location $OUTPUT_DIR
    Write-Host "Current directory: $(Get-Location)"

    # Extract frontend package
    Write-Host "Extracting supersonic-webapp.tar.gz..."
    if (Test-Path "supersonic-webapp.tar.gz") {
        try {
            tar -xzf "supersonic-webapp.tar.gz"
            Write-Host "[SUCCESS] Frontend package extracted" -ForegroundColor Green
        } catch {
            Write-Host "[ERROR] Failed to extract supersonic-webapp.tar.gz: $_" -ForegroundColor Red
            throw "Failed to extract frontend package"
        }
    } else {
        Write-Host "[ERROR] supersonic-webapp.tar.gz not found" -ForegroundColor Red
        throw "supersonic-webapp.tar.gz not found"
    }

    # Extract backend package
    Write-Host "Extracting launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz..."
    if (Test-Path "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz") {
        try {
            tar -xzf "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz"
            Write-Host "[SUCCESS] Backend package extracted" -ForegroundColor Green
        } catch {
            Write-Host "[ERROR] Failed to extract launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz: $_" -ForegroundColor Red
            throw "Failed to extract backend package"
        }
    } else {
        Write-Host "[ERROR] launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz not found" -ForegroundColor Red
        throw "launchers-standalone-1.0.0-SNAPSHOT-bin.tar.gz not found"
    }
    Write-Host ""

    # Step 7: Rename and move webapp
    Write-Host "[Step 7] Renaming and moving webapp..."
    if (Test-Path "supersonic-webapp") {
        Rename-Item "supersonic-webapp" "webapp"
        if (Test-Path "launchers-standalone-1.0.0-SNAPSHOT") {
            Move-Item "webapp" "launchers-standalone-1.0.0-SNAPSHOT\" -Force
            Write-Host "[SUCCESS] webapp renamed and moved to launchers-standalone-1.0.0-SNAPSHOT directory" -ForegroundColor Green
        } else {
            Write-Host "[ERROR] launchers-standalone-1.0.0-SNAPSHOT directory not found" -ForegroundColor Red
            throw "launchers-standalone-1.0.0-SNAPSHOT directory not found"
        }
    } else {
        Write-Host "[ERROR] supersonic-webapp directory not found" -ForegroundColor Red
        throw "supersonic-webapp directory not found"
    }
    Write-Host ""

    # Step 8: Copy YAML configuration files
    Write-Host "[Step 8] Copying YAML configuration files..."
    Write-Host "Checking config directory: $CONFIG_DIR"
    
    if (!(Test-Path $CONFIG_DIR)) {
        Write-Host "[ERROR] Config directory not found: $CONFIG_DIR" -ForegroundColor Red
        throw "Config directory not found"
    }
    
    $yamlFiles = Get-ChildItem -Path $CONFIG_DIR -Filter "*.yaml" -ErrorAction SilentlyContinue
    Write-Host "Found $($yamlFiles.Count) YAML files"
    
    if ($yamlFiles.Count -ge 2) {
        $confDir = "$OUTPUT_DIR\launchers-standalone-1.0.0-SNAPSHOT\conf"
        if (!(Test-Path $confDir)) {
            New-Item -ItemType Directory -Path $confDir -Force | Out-Null
        }
        foreach ($yamlFile in $yamlFiles) {
            Copy-Item $yamlFile.FullName $confDir -Force
            Write-Host "Copied $($yamlFile.Name) to conf directory" -ForegroundColor Green
        }
        Write-Host "[SUCCESS] YAML configuration files copied and overwritten" -ForegroundColor Green
    } else {
        Write-Host "[ERROR] Less than 2 YAML files found in $CONFIG_DIR" -ForegroundColor Red
        throw "Not enough YAML files found"
    }
    Write-Host ""

    # Step 9: Copy SH script files
    Write-Host "[Step 9] Copying SH script files..."
    $shFiles = Get-ChildItem -Path $CONFIG_DIR -Filter "*.sh" -ErrorAction SilentlyContinue
    Write-Host "Found $($shFiles.Count) SH files"
    
    if ($shFiles.Count -ge 3) {
        $binDir = "$OUTPUT_DIR\launchers-standalone-1.0.0-SNAPSHOT\bin"
        if (!(Test-Path $binDir)) {
            New-Item -ItemType Directory -Path $binDir -Force | Out-Null
        }
        foreach ($shFile in $shFiles) {
            Copy-Item $shFile.FullName $binDir -Force
            Write-Host "Copied $($shFile.Name) to bin directory" -ForegroundColor Green
        }
        Write-Host "[SUCCESS] SH script files copied and overwritten" -ForegroundColor Green
    } else {
        Write-Host "[ERROR] Less than 3 SH files found in $CONFIG_DIR" -ForegroundColor Red
        throw "Not enough SH files found"
    }
    Write-Host ""

    # Step 10: Build Docker image
    Write-Host "[Step 10] Building Docker image in E:\bi..."
    Set-Location $OUTPUT_DIR
    Write-Host "Current directory: $(Get-Location)"
    
    docker build -t chatbi .
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[ERROR] Docker image build failed" -ForegroundColor Red
        throw "Docker image build failed"
    }
    Write-Host "[SUCCESS] Docker image build completed" -ForegroundColor Green
    Write-Host ""

    # Step 11: Get IMAGE_ID
    Write-Host "[Step 11] Getting IMAGE_ID for chatbi image..."
    $imageInfo = docker images chatbi --format "{{.ID}}"
    if ($imageInfo) {
        $IMAGE_ID = $imageInfo.Trim()
        Write-Host "IMAGE_ID: $IMAGE_ID" -ForegroundColor Yellow
    } else {
        Write-Host "[ERROR] Failed to get IMAGE_ID for chatbi image" -ForegroundColor Red
        throw "Failed to get IMAGE_ID"
    }

    Write-Host ""
    Write-Host "========================================"
    Write-Host "Build and deployment completed successfully!"
    Write-Host "IMAGE_ID: $IMAGE_ID"
    Write-Host "========================================"
    Write-Host ""

} catch {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "SCRIPT FAILED WITH ERROR:" -ForegroundColor Red
    Write-Host $_ -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
}

# Keep window open
Read-Host "Press Enter to exit"
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Build completed!" -ForegroundColor Green
    Write-Host "Image name: chatbi"
    Write-Host "Image ID: $IMAGE_ID" -ForegroundColor Yellow
    Write-Host "========================================" -ForegroundColor Cyan
} else {
    Write-Host "[WARNING] Failed to get image ID" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "All operations completed!" -ForegroundColor Green
Read-Host "Press Enter to exit"