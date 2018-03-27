FROM gcr.io/google-appengine/jetty
MAINTAINER stefan
VOLUME /tmp
ADD ${JAR_FILE} /usr/local/healthcare-1.0.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/local/healthcare-1.0.jar"]
