environments=("dev" "main")
services=("alert-watcher" "edge-service")

for env in "${environments[@]}"; do
    for service in "${services[@]}"; do
        docker logs "${service}-${env}" > "${service}-${env}.txt"
    done
done