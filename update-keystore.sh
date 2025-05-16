#!/bin/bash

# Configuration
KEYSTORE_DIR="src/main/resources"
KEYSTORE_NAME="keystore.p12"
PASSWORD="password"
DAYS_VALID=825

# Check if the script is run from the root of the project
if [ ! -d "$KEYSTORE_DIR" ]; then
  echo "Error: Directory '$KEYSTORE_DIR' not found. Make sure to run this script from the project's root directory."
  exit 1
fi

# Remove old keystore file if it exists
if [ -f "$KEYSTORE_DIR/$KEYSTORE_NAME" ]; then
  echo "Removing the old keystore..."
  rm -f "$KEYSTORE_DIR/$KEYSTORE_NAME"
fi

# Generate a new self-signed certificate
echo "Generating a new self-signed certificate..."
openssl genrsa -out private.key 2048
openssl req -new -key private.key -out certificate.csr -subj "/CN=localhost"
openssl x509 -req -days $DAYS_VALID -in certificate.csr -signkey private.key -out certificate.crt
openssl pkcs12 -export -out "$KEYSTORE_DIR/$KEYSTORE_NAME" -inkey private.key -in certificate.crt -password pass:$PASSWORD

# Clean up temporary files
rm -f private.key certificate.csr certificate.crt

# Output results
if [ -f "$KEYSTORE_DIR/$KEYSTORE_NAME" ]; then
  echo "New certificate successfully created and saved to '$KEYSTORE_DIR/$KEYSTORE_NAME'."
else
  echo "Error: Failed to create the certificate."
  exit 1
fi
