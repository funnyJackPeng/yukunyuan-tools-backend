#!/bin/sh
set -e
# 获取所有 ECR 镜像的 URI 并写入文件
echo "write image list to txt file"
docker images "*.dkr.ecr.*.amazonaws.com/*:latest" --format "{{.Repository}}" > /image_list.txt

images=$(docker images "*.dkr.ecr.*.amazonaws.com/*:latest" --format "{{.Repository}}")
echo "$images" | while read -r image ; do
  echo "$image"
  docker push "$image"
done
