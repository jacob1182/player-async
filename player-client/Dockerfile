FROM openjdk:11-jdk

ADD target/player-client-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["/bin/bash", "-c", "java -jar /app.jar $PLAYER_NAME"]