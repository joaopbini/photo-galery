FROM openjdk:11-jre-slim

COPY target/*.jar app.jar

EXPOSE 8080

CMD java $JAVA_OPTS -jar $SPRING_PROFILE app.jar $SPRING_PROPERTIES