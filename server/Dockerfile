FROM openjdk:11

WORKDIR /usr/src/app

ARG JAR_PATH=build/libs/youngcha-0.0.1-SNAPSHOT.jar

COPY $JAR_PATH $JAR_PATH

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "$JAR_PATH"]