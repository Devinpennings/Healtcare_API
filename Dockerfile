FROM anapsix/alpine-java
MAINTAINER gieforce
COPY healthcare-1.0.jar /home/healthcare-1.0.jar
CMD ["java","-jar","/home/healthcare-1.0.jar"]