FROM jetty
ADD target/healthcare-1.0.war /var/lib/jetty/webapps/root.war
EXPOSE 8080