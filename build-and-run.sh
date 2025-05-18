#!/bin/bash

set -e

echo "Step 1: Building Quarkus Fast JAR..."
mvn clean package -Dquarkus.package.type=fast-jar -DskipTests

echo "Fast JAR build complete."

echo "Step 2: Starting Docker Compose..."
docker-compose up --build
