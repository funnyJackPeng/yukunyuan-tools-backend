#!/bin/sh
set -e
./gradlew email-service:bootBuildImage
docker tag email-service:0.0.1-SNAPSHOT $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$IMAGE_REPO_NAME/email-service:$IMAGE_TAG
./gradlew migration-job:bootBuildImage
docker tag migration-job:0.0.1-SNAPSHOT $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$IMAGE_REPO_NAME/migration-job:$IMAGE_TAG
#./gradlew monolith-service:bootBuildImage
