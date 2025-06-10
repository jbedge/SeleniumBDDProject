@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

REM Configuration
set "ALLURE_RESULTS_DIRECTORY=allure-results"
set "ALLURE_SERVER=http://localhost:5050"
set "PROJECT_ID=mailservice"

REM Get absolute path of current directory
for %%I in ("%~dp0.") do set "DIR=%%~fI"

REM Check if directory exists
if not exist "%DIR%\%ALLURE_RESULTS_DIRECTORY%" (
    echo No Allure result files found in %ALLURE_RESULTS_DIRECTORY%
    exit /b 1
)

REM Build list of files to upload
set "FILES="
for %%F in (%DIR%\%ALLURE_RESULTS_DIRECTORY%\*) do (
    set "FILES=!FILES! -F files[]=@%%F"
)

REM Send results to Allure server
echo ------------------ SENDING RESULTS ------------------
curl -X POST "%ALLURE_SERVER%/allure-docker-service/send-results?project_id=%PROJECT_ID%" ^
     -H "Content-Type: multipart/form-data" %FILES% -ik

REM Optional: Uncomment to generate report automatically
REM set "EXECUTION_NAME=My Test Run"
REM set "EXECUTION_FROM=http://localhost"
REM set "EXECUTION_TYPE=manual"
REM curl -X GET "%ALLURE_SERVER%/allure-docker-service/generate-report?project_id=%PROJECT_ID%^&execution_name=%EXECUTION_NAME%^&execution_from=%EXECUTION_FROM%^&execution_type=%EXECUTION_TYPE%" -s

ENDLOCAL
