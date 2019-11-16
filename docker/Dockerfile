FROM java:8
EXPOSE 8080
ADD oauth-1.0.0-SNAPSHOT.jar /oauth-1.0.0-SNAPSHOT.jar
WORKDIR /
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/oauth-1.0.0-SNAPSHOT.jar"]