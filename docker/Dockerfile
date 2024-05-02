FROM openjdk:21-jdk-oracle

# Create a folder
VOLUME /apps

# Copy all files that end with letter .jar to folder above
COPY target/*.jar /apps/tokenJwtApp.jar

# Run the command to start the application java
ENTRYPOINT ["java", "-jar", "/apps/tokenJwtApp.jar"]