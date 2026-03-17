# which java run time need to be considered which jdk image need to run for jdk
FROM eclipse-temurin:17-jdk

#which port docker need to be exposed
EXPOSE 8081
#adding spring boot jar specifying the location where my jar was available and you need to confirm what is your image name
ADD target/nut-docker.jar nut-docker.jar

#command for running the jar
ENTRYPOINT ["java", "-jar", "/nut-docker.jar"]
