FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD healthcare-1.0.jar app.jar
CMD [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]