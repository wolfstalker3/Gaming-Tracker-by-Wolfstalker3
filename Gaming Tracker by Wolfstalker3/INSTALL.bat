@echo off
setlocal enabledelayedexpansion
title Gaming Session Optimizer - Installer
color 0A

echo.
echo  ==========================================
echo  ^|  Gaming Session Optimizer Installer  ^|
echo  ==========================================
echo.
echo  Welcome to the Gaming Session Optimizer!
echo  This installer will build and run the application.
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH.
    echo Please install Java 17 or higher from:
    echo https://adoptium.net/temurin/releases/
    echo.
    pause
    exit /b 1
)

echo [INFO] Java detected. Checking version...
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
)
echo [INFO] Java version: %JAVA_VERSION%

REM Set JAVA_HOME if not already set
if not defined JAVA_HOME (
    echo [INFO] JAVA_HOME not set. Attempting to detect...
    for /f "tokens=*" %%i in ('where java') do (
        set "JAVA_PATH=%%i"
        goto :found_java
    )
    :found_java
    for %%i in ("!JAVA_PATH!") do set "JAVA_HOME=%%~dpi.."
    for %%i in ("!JAVA_HOME!") do set "JAVA_HOME=%%~fi"
    echo [INFO] Using JAVA_HOME: !JAVA_HOME!
    setx JAVA_HOME "!JAVA_HOME!" >nul
)

echo.
echo [INFO] Building the application...
echo This may take a few minutes on first run...
echo.

REM Build the application using Maven wrapper
call mvnw.cmd clean package -DskipTests -q

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Build failed! Please check the error messages above.
    echo.
    pause
    exit /b 1
)

echo.
echo [SUCCESS] Build completed successfully!
echo.

REM Check if JAR file exists
if not exist "target\gaming-session-optimizer.jar" (
    echo [ERROR] JAR file not found. Build may have failed.
    pause
    exit /b 1
)

echo [INFO] Starting Gaming Session Optimizer...
echo.
echo The application will be available at: http://localhost:8080
echo.
echo Press Ctrl+C to stop the application
echo.

REM Start the application
java -jar target\gaming-session-optimizer.jar

echo.
echo [INFO] Application stopped.
pause
