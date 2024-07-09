#!/bin/sh
set -e
images=$(docker images "*.dkr.ecr.*.amazonaws.com/*:latest" --format "{{.Repository}}")
echo "write image list to txt file"
images > /image_list.txt
echo "$images" | while read -r image ; do
  echo "$image"
  docker push "$image"
done
