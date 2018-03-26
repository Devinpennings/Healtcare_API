FROM gcr.io/google-appengine/jetty
MAINTAINER stefan
VOLUME /tmp
ADD /target/healthcare-1.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]