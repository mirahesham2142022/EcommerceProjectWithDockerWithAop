FROM openjdk:17-oracle
COPY target/*.jar major.jar
EXPOSE 4335
ENTRYPOINT ["java","-jar","major.jar"]