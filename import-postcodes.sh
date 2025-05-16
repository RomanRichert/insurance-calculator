#!/bin/bash

# Path to the JAR file
JAR_PATH="target/insurance-calculator-1.0-SNAPSHOT-runner.jar"

# Path to the CSV file, passed as the first argument
CSV_PATH="$1"

# Check if the argument is provided
if [ -z "$CSV_PATH" ]; then
  echo "Usage: ./import-postcodes.sh <path-to-csv>"
  exit 1
fi

# Check if the file exists
if [ ! -f "$CSV_PATH" ]; then
  echo "CSV file not found: $CSV_PATH"
  exit 1
fi

# Run the import command
java -jar "$JAR_PATH" import-postcodes "$CSV_PATH"