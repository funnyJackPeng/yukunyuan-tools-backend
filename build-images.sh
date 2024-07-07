#!/bin/sh
set -e
#./gradlew email-service:bootBuildImage
./gradlew migration-job:bootBuildImage
#./gradlew monolith-service:bootBuildImage
