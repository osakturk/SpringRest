FROM adoptopenjdk:8-jre-hotspot

RUN ["apt-get", "update"]
RUN ["apt-get", "install", "-y", "vim"]

ARG JAR_FILE=target/SpringRest-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/application.jar"]