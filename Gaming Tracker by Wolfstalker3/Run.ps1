# Gaming Session Optimizer - PowerShell Launcher
# This script runs the Gaming Session Optimizer if already built

Write-Host ""
Write-Host "==========================================" -ForegroundColor Green
Write-Host "|      Gaming Session Optimizer        |" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
Write-Host ""

# Check if JAR file exists
if (-not (Test-Path "target\gaming-session-optimizer.jar")) {
    Write-Host "[INFO] Application not built yet. Running installer..." -ForegroundColor Yellow
    & .\Install.ps1
    exit
}

Write-Host "[INFO] Starting Gaming Session Optimizer..." -ForegroundColor Cyan
Write-Host ""
Write-Host "Open your browser and go to: " -NoNewline -ForegroundColor Green
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
    Write-Host "Make sure Java 17+ is installed and try running Install.ps1 first." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "[INFO] Application stopped." -ForegroundColor Cyan
Read-Host "Press Enter to exit"
