FROM adoptopenjdk/openjdk16
EXPOSE 8080
ARG JAR_FILE=target/leave-a-mark-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","/application.jar"]