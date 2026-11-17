@echo off
REM Batch script to run Registration Tests

echo =============================================
echo Tealium Ecommerce Demo - Registration Tests
echo =============================================
echo.

if "%1%"=="" (
    echo Running all tests in normal mode...
    mvn clean test -DsuiteXmlFile=testng.xml
) else if "%1%"=="headless" (
    echo Running tests in headless mode...
    mvn clean test -Dheadless=true
) else if "%1%"=="firefox" (
    echo Running tests with Firefox...
    mvn clean test -Dbrowser=firefox
) else if "%1%"=="edge" (
    echo Running tests with Edge...
    mvn clean test -Dbrowser=edge
) else if "%1%"=="compile" (
    echo Compiling the project...
    mvn clean compile
) else if "%1%"=="clean" (
    echo Cleaning the project...
    mvn clean
) else (
    echo.
    echo Usage: run_tests.bat [option]
    echo.
    echo Options:
    echo   (default)    - Run tests in normal Chrome mode
    echo   headless     - Run tests in headless mode
    echo   firefox      - Run tests with Firefox
    echo   edge         - Run tests with Edge
    echo   compile      - Compile the project only
    echo   clean        - Clean the project
    echo.
)

pause
