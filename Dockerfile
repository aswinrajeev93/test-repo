FROM openjdk:11
VOLUME /tmpdsf 
ADD target/soa-data-batch-information-0.0.1-SNAPSHOT.war app.jar
EXPOSE 8080
ENV ROOT_LOG_LEVEL=infof
RUN sh -c 'touch /app.jar'
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]
