FROM openjdk:11.0.2-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD /usr/bin/java -Xmx400m -Xms400m  -XX:TieredStopAtLevel=1 -noverify -jar app.jar
EXPOSE 8080