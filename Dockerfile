FROM maven:3.8.3-jdk-11 AS build
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:16-jdk-slim

# update sources
RUN apt-get update

# install some packages
RUN apt-get install -y curl

# run app
COPY --from=build /usr/src/app/target/*.jar /usr/app/ctf.jar 
EXPOSE 8080

RUN mkdir /var/backup
RUN echo "flag{1644dad6-6109-11ec-90d6-0242ac120003}" > /var/backup/secret.txt

# if you want to start the app using an application.properties file located
#ENTRYPOINT ["java","-jar","/app.jar", "--spring.config.location=file:/application.properties"]

ENTRYPOINT ["java","-jar","/usr/app/ctf.jar"]