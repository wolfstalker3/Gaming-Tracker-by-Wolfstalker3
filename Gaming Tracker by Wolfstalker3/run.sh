#!/bin/bash

# Gaming Session Optimizer - Unix/Linux/Mac Launcher
# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}"
echo "=========================================="
echo "|      Gaming Session Optimizer        |"
echo "=========================================="
echo -e "${NC}"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo -e "${RED}[ERROR] Java is not installed or not in PATH.${NC}"
    echo "Please install Java 17 or higher."
    echo "Visit: https://adoptium.net/temurin/releases/"
    exit 1
fi

echo -e "${BLUE}[INFO] Java detected. Checking version...${NC}"
java -version

# Check if JAR file exists
if [ ! -f "target/gaming-session-optimizer.jar" ]; then
    echo -e "${YELLOW}[INFO] Application not built yet. Building...${NC}"
    echo "This may take a few minutes on first run..."
    
    # Make Maven wrapper executable
    chmod +x mvnw
    
    # Build the application
    ./mvnw clean package -DskipTests -q
    
    if [ $? -ne 0 ]; then
        echo -e "${RED}[ERROR] Build failed! Please check the error messages above.${NC}"
        exit 1
    fi
    
    echo -e "${GREEN}[SUCCESS] Build completed successfully!${NC}"
fi

echo -e "${BLUE}[INFO] Starting Gaming Session Optimizer...${NC}"
echo ""
echo "  Open your browser and go to: http://localhost:8080"
echo ""
echo "  Features available:"
echo "  - Gaming Session Tracking"
echo "  - Performance Analytics"
echo "  - Schedule Management"
echo "  - System Optimization"
echo ""
echo "  Press Ctrl+C to stop the application"
echo ""

# Start the application
java -jar target/gaming-session-optimizer.jar

echo ""
echo -e "${BLUE}[INFO] Application stopped.${NC}"
