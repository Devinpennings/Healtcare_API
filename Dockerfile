FROM openjdk:8-jre-alpine
COPY ./target/healthcare-1.0.jar /usr/src/healthcare/
WORKDIR /usr/src/healthcare
EXPOSE 8080
CMD ["java", "-jar", "healthcare-1.0.jar"]