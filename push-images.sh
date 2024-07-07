#!/bin/sh
set -e
images=$(docker images "*.dkr.ecr.*.amazonaws.com/*:latest" --format "{{.Repository}}")
echo "$images" | while read -r image ; do
  echo "$image"
  docker push "$image"
done
