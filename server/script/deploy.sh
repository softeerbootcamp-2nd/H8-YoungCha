#! /bin/bash

DOCKER_COMPOSE_FILE=$1
DOCKER_USERNAME=$2
DOCKER_REPO=$3
ABS_PATH=$(readlink -f "$0")
ABS_DIR=$(dirname "$ABS_PATH")
source "$ABS_DIR"/profile.sh

IDLE_CONTAINER=$(find_idle_profile)

# $IDLE_CONTAINER의 컨테이너 ID를 찾고, 있다면 제거
if [ "$(docker ps -aqf name="^$IDLE_CONTAINER$")" ];
then
  echo "> $IDLE_CONTAINER container 제거"
  docker stop "$IDLE_CONTAINER" && docker rm "$IDLE_CONTAINER"
else
  echo "> 구동 중인 유휴 spring container가 없으므로 종료하지 않습니다."
fi

if [[ "$(docker images -q ghcr.io/"$ORGANIZATION"/"$REPOSITORY":latest 2> /dev/null)" != "" ]]; then
  echo "> latest image tag를 old로 변경"
  docker rmi "$DOCKER_USERNAME"/"$DOCKER_REPO":old
  docker tag "$DOCKER_USERNAME"/"$DOCKER_REPO":latest "$DOCKER_USERNAME"/"$DOCKER_REPO":old
  docker rmi "$DOCKER_USERNAME"/"$DOCKER_REPO":latest
fi

echo "> nginx, $IDLE_CONTAINER container 실행"
docker compose -f "$DOCKER_COMPOSE_FILE" up nginx "$IDLE_CONTAINER" -d --build