FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/web-matriculas-0.0.1-SNAPSHOT.jar web-matriculas.jar
ENTRYPOINT ["java","-jar","/web-matriculas.jar"]
