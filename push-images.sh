#!/bin/sh
set -e
#login alibaba cloud Docker Registry
docker login --username=funnyJack --password=s554517318 registry.cn-hangzhou.aliyuncs.com
#docker login --username=funnyJack registry.cn-hangzhou.aliyuncs.com
images=$(docker images "registry.cn-hangzhou.aliyuncs.com/yukunyuan-tools/*:latest" --format "{{.Repository}}")
echo "$images" | while read -r image ; do
  echo "$image"
  docker push "$image"
done
