FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/statistics-rest-api-1.0.0.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar --debug