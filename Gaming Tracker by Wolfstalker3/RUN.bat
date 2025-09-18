@echo off
title Gaming Session Optimizer
color 0A

echo.
echo  ==========================================
echo  ^|      Gaming Session Optimizer        ^|
echo  ==========================================
echo.

REM Check if JAR file exists
if not exist "target\gaming-session-optimizer.jar" (
    echo [INFO] Application not built yet. Running installer...
    call INSTALL.bat
    exit /b %errorlevel%
)

echo [INFO] Starting Gaming Session Optimizer...
echo.
echo  Open your browser and go to: http://localhost:8080
echo.
echo  Features available:
echo  - Gaming Session Tracking
echo  - Performance Analytics  
echo  - Schedule Management
echo  - System Optimization
echo.
echo  Press Ctrl+C to stop the application
echo.

REM Start the application
java -jar target\gaming-session-optimizer.jar

echo.
echo [INFO] Application stopped.
pause
