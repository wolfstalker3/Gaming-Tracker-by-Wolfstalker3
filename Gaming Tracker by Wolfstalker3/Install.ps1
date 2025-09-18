# Gaming Session Optimizer - PowerShell Installer
# This script builds and runs the Gaming Session Optimizer

Write-Host ""
Write-Host "==========================================" -ForegroundColor Green
Write-Host "|  Gaming Session Optimizer Installer  |" -ForegroundColor Green  
Write-Host "==========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Welcome to the Gaming Session Optimizer!" -ForegroundColor Cyan
Write-Host "This installer will build and run the application." -ForegroundColor Cyan
Write-Host ""

# Check if Java is installed
try {
    $javaVersion = & java -version 2>&1
    Write-Host "[INFO] Java detected:" -ForegroundColor Green
    Write-Host $javaVersion[0] -ForegroundColor Yellow
} catch {
    Write-Host "[ERROR] Java is not installed or not in PATH." -ForegroundColor Red
    Write-Host "Please install Java 17 or higher from:" -ForegroundColor Red
    Write-Host "https://adoptium.net/temurin/releases/" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Set JAVA_HOME if not already set
if (-not $env:JAVA_HOME) {
    Write-Host "[INFO] JAVA_HOME not set. Attempting to detect..." -ForegroundColor Yellow
    
    # Try to find JDK installation
    $possiblePaths = @(
        "C:\Program Files\Java\jdk-17",
        "C:\Program Files\Java\jdk-17.0.12",
        "C:\Program Files\Java\latest",
        "C:\Program Files\OpenJDK\jdk-17",
        "C:\Program Files\Eclipse Adoptium\jdk-17"
    )
    
    $javaHome = $null
    foreach ($path in $possiblePaths) {
        if (Test-Path "$path\bin\java.exe") {
            $javaHome = $path
            break
        }
    }
    
    if (-not $javaHome) {
        # Fallback to parsing java command location
        try {
            $javaPath = (Get-Command java).Source
            $javaHome = Split-Path (Split-Path $javaPath -Parent) -Parent
        } catch {
            $javaHome = "C:\Program Files\Java\jdk-17"
        }
    }
    
    $env:JAVA_HOME = $javaHome
    Write-Host "[INFO] Using JAVA_HOME: $javaHome" -ForegroundColor Green
}

Write-Host ""
Write-Host "[INFO] Building the application..." -ForegroundColor Cyan
Write-Host "This may take a few minutes on first run..." -ForegroundColor Yellow
Write-Host ""

# Build the application using Maven wrapper
try {
    & .\mvnw.cmd clean package -DskipTests -q
    
    if ($LASTEXITCODE -ne 0) {
        throw "Build failed"
    }
    
    Write-Host ""
    Write-Host "[SUCCESS] Build completed successfully!" -ForegroundColor Green
    Write-Host ""
} catch {
    Write-Host ""
    Write-Host "[ERROR] Build failed! Please check the error messages above." -ForegroundColor Red
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}

# Check if JAR file exists
if (-not (Test-Path "target\gaming-session-optimizer.jar")) {
    Write-Host "[ERROR] JAR file not found. Build may have failed." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "[INFO] Starting Gaming Session Optimizer..." -ForegroundColor Cyan
Write-Host ""
Write-Host "The application will be available at: " -NoNewline -ForegroundColor Green
Write-Host "http://localhost:8080" -ForegroundColor Yellow
Write-Host ""
Write-Host "Features available:" -ForegroundColor Cyan
Write-Host "- Gaming Session Tracking" -ForegroundColor White
Write-Host "- Performance Analytics" -ForegroundColor White
Write-Host "- Schedule Management" -ForegroundColor White
Write-Host "- System Optimization" -ForegroundColor White
Write-Host ""
Write-Host "Press Ctrl+C to stop the application" -ForegroundColor Yellow
Write-Host ""

# Start the application
try {
    & java -jar target\gaming-session-optimizer.jar
} catch {
    Write-Host ""
    Write-Host "[ERROR] Failed to start the application." -ForegroundColor Red
}

Write-Host ""
Write-Host "[INFO] Application stopped." -ForegroundColor Cyan
Read-Host "Press Enter to exit"
