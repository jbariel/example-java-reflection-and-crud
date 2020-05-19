FROM openjdk:8-alpine

RUN mkdir -p /app && apk update

WORKDIR /app
COPY target/app-jar-with-dependencies.jar app.jar

CMD ["java","-jar","app.jar"]
