#! /bin/bash

DOCKER_COMPOSE_FILE=$1
DOCKER_USERNAME=$2
DOCKER_REPO=$3
ABS_PATH=$(readlink -f "$0")
ABS_DIR=$(dirname "$ABS_PATH")
source "$ABS_DIR"/profile.sh
source "$ABS_DIR"/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health check 시작"
echo "> IDLE_CONTAINER: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profiles"
sleep 10

for RETRY_COUNT in $(seq 1 10)
do
  RESPONSE=$(curl -s http://localhost:"$IDLE_PORT"/profiles)
  UP_COUNT=$(echo "$RESPONSE" | grep -c "spring") # spring이 들어간 행의 개수

  if [ "$UP_COUNT" -ge 1 ]
  then
    echo "> Health check 성공"
    switch_proxy
    if [ "$?" -ge 1 ]
    then
      exit 1
    fi
    # 성공 시 break
    break
  else
    echo "> 응답 실패"
    echo "> Health check: $RESPONSE"
  fi

  if [ "$RETRY_COUNT" -eq 10 ]
  then
    echo "> Health 실패"
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    echo "> 배포에 실패한 container 삭제"
    IDLE_CONTAINER=$(find_idle_profile)
    docker stop "$IDLE_CONTAINER" && docker rm "$IDLE_CONTAINER"
    echo "> 실패한 docker latest image 삭제"
    docker rmi "$DOCKER_USERNAME"/"$DOCKER_REPO":latest
    exit 1
  fi

  echo "> Health check 실패, 5초 후 재시도..."
  sleep 5
done