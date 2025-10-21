#build
FROM maven:4.0.0-rc-4-eclipse-temurin-21 AS build
ENV HOME=/home/app
WORKDIR ${HOME}
COPY . ${HOME}
RUN mvn -f ${HOME}/pom.xml clean package -DskipTests

#launch
FROM openjdk:21
ENV HOME=/home/app

EXPOSE 80
COPY --from=build ${HOME}/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]