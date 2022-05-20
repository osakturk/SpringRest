FROM ubuntu:18.04
FROM adoptopenjdk:8-jre-hotspot

RUN ["apt-get", "update"]
RUN ["apt-get", "install", "-y", "vim"]

RUN apt-get update && apt-get install -y gnupg
RUN DEBIAN_FRONTEND=noninteractive
RUN apt-get install -y vim
RUN wget http://archive.ubuntu.com/ubuntu/pool/main/m/mysql-5.7/mysql-server-5.7_5.7.21-1ubuntu1_amd64.deb

RUN apt-get update
RUN apt-get purge mysql-server
RUN apt-get purge mysql-common
RUN rm -rf /var/log/mysql
RUN rm -rf /var/log/mysql.*
RUN rm -rf /var/lib/mysql
RUN rm -rf /etc/mysql
RUN # and then:
RUN apt-get install mysql-server -y --fix-missing --fix-broken
EXPOSE 3306
CMD chown -R mysql:mysql /var/lib/mysql \
&& service mysql start \
&& mysql -ppassword -e "CREATE DATABASE IF NOT EXISTS ted;GRANT ALL PRIVILEGES on ted.* TO 'root'@'localhost' WITH GRANT OPTION; ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';FLUSH PRIVILEGES;SET GLOBAL connect_timeout=28800;SET GLOBAL sql_mode='NO_ENGINE_SUBSTITUTION';" \
&& npm start

ARG JAR_FILE=target/SpringRest-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/application.jar"]