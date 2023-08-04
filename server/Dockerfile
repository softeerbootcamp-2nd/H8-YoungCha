FROM openjdk:11

WORKDIR /usr/src/app

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/server-0.0.1-SNAPSHOT.jar ${JAR_PATH}/server-0.0.1-SNAPSHOT.jar

CMD ["java", "-Dspring.profiles.active=${SERVER_MODE}", "-Dspring.datasource.url=${MYSQL_URL}", "-Dspring.datasource.username=${MYSQL_USERNAME}", "-Dspring.datasource.password=${MYSQL_PASSWORD}", "-jar","./build/libs/server-0.0.1-SNAPSHOT.jar"]