#!/bin/sh
set -e
# 创建一个空的 JSON 数组
echo "[" > /imagedefinitions.json

# 获取所有 ECR 镜像的 URI 并写入 JSON 文件
images=$(docker images "*.dkr.ecr.*.amazonaws.com/*:latest" --format "{{.Repository}}")
image_count=$(echo "$images" | wc -l)
i=1

echo "$images" | while read -r image; do
  echo "{\"name\": \"${image##*/}\", \"imageUri\": \"$image:latest\"}" >> /imagedefinitions.json
  # 推送镜像到 ECR
  docker push "$image"
  if [ $i -lt $image_count ]; then
    echo "," >> /imagedefinitions.json
  fi
  i=$((i+1))
done

# 补全 JSON 数组
echo "]" >> /imagedefinitions.json
