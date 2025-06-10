#!/bin/bash

###############################################
# Configuration
###############################################

# Local directory containing Allure result files
ALLURE_RESULTS_DIRECTORY='allure-results'

# Allure Docker Service URL (e.g., running on localhost:5050)
ALLURE_SERVER='http://localhost:5050'

# Unique project ID as defined in your Allure Docker container
PROJECT_ID='mailservice'

# (Optional) Execution metadata for generating report
EXECUTION_NAME='My Test Execution'
EXECUTION_FROM='http://localhost'
EXECUTION_TYPE='manual'

###############################################
# Prepare File Paths
###############################################

# Get current script directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# List all files in the results directory (ignore directories)
FILES_TO_SEND=$(ls -dp "$DIR/$ALLURE_RESULTS_DIRECTORY"/* | grep -v /$)

# Exit if no files found
if [ -z "$FILES_TO_SEND" ]; then
  echo "No Allure result files found in $ALLURE_RESULTS_DIRECTORY"
  exit 1
fi

###############################################
# Upload Result Files
###############################################

# Build the curl -F parameters for each file
FILES=''
for FILE in $FILES_TO_SEND; do
  FILES+="-F files[]=@$FILE "
done

# Print command being executed (for debug)
set -o xtrace

echo "------------------ SENDING RESULTS ------------------"

# Send result files to the Allure Docker Service
curl -X POST "$ALLURE_SERVER/allure-docker-service/send-results?project_id=$PROJECT_ID" \
     -H 'Content-Type: multipart/form-data' \
     $FILES -ik

###############################################
# (Optional) Trigger Report Generation
###############################################

# Uncomment below if you want to auto-generate the report right after upload
# echo "------------------ GENERATING REPORT ------------------"
# RESPONSE=$(curl -X GET "$ALLURE_SERVER/allure-docker-service/generate-report?project_id=$PROJECT_ID&execution_name=$EXECUTION_NAME&execution_from=$EXECUTION_FROM&execution_type=$EXECUTION_TYPE" -s)
# echo "Response: $RESPONSE"

# Extract and print report URL (requires jq OR grep)
# REPORT_URL=$(echo "$RESPONSE" | grep -o '"report_url":"[^"]*' | grep -o '[^"]*$')
# echo "Allure Report URL: $ALLURE_SERVER$REPORT_URL"
