#!/bin/zsh

docker buildx create --name multiarch-builder --use || true
docker buildx inspect --bootstrap

PLATFORMS="linux/amd64,linux/arm64"
services=("edge-service" "alert-watcher")

for service in $services; do
    docker buildx build --platform $PLATFORMS -t lolman135/${service}:latest ./${service} --push
done;