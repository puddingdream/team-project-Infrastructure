#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

if [[ -x "./gradlew" ]]; then
  GRADLE_CMD="./gradlew"
else
  GRADLE_CMD="./gradlew.bat"
fi

echo "=== [1/3] Gradle bootJar build ==="
"$GRADLE_CMD" bootJar

echo "=== [2/3] Docker Compose up ==="
docker compose up -d --build

echo "=== [3/3] Container status ==="
docker compose ps

echo
echo "App:   http://localhost:${SERVER_PORT:-8080}"
echo "MySQL: localhost:${MYSQL_HOST_PORT:-33306}"
