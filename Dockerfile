FROM gcr.io/google-appengine/jetty
VOLUME /tmp
ADD target/healthcare-1.0.jar healthcare-1.0.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "healthcare-1.0.jar"]