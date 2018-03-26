FROM openjdk:8-jdk-alpine
COPY target/healthcare-1.0.jar /home/healthcare-1.0.jar
EXPOSE 8080
CMD ["java", "-jar", "/home/healthcare-1.0.jar"]