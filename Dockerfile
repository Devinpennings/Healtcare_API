FROM gcr.io/google-appengine/jetty
ADD target/healthcare-1.0.jar healthcare-1.0.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "healthcare-1.0.jar"]