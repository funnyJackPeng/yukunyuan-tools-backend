#!/bin/sh
set -e
images=$(docker images "*:latest" --format "{{.Repository}}")
echo "$images" | while read -r image ; do
  echo "$image"
  docker push "$image"
done
