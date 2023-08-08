FROM openjdk:11

WORKDIR /usr/src/app

ARG JAR_PATH=build/libs/youngcha-0.0.1-SNAPSHOT.jar

COPY $JAR_PATH app.jar

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]