FROM gcr.io/google-appengine/jetty
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "healthcare-1.0.jar"]