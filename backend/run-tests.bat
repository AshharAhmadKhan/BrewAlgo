@echo off
echo ==========================================
echo BrewAlgo - Comprehensive Test Suite
echo ==========================================
echo.

echo Running all tests...
echo.

REM Run Maven tests
cd /d "%~dp0"
call mvnw.cmd clean test

REM Check exit code
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ==========================================
    echo All tests passed successfully!
    echo ==========================================
    echo.
    echo Test Summary:
    echo   - Unit Tests: UserServiceTest, ProblemServiceTest, SubmissionServiceTest
    echo   - Integration Tests: UserController, ProblemController, SubmissionController
    echo.
) else (
    echo.
    echo ==========================================
    echo Some tests failed!
    echo ==========================================
    echo.
    echo Please check the output above for details.
    exit /b 1
)

echo Next steps:
echo   1. Review test coverage report
echo   2. Run manual testing checklist
echo   3. Fix any issues found
echo.
