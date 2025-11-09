FROM gradle:8.5-jdk21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy your Gradle project into the container
COPY . .

# Run Gradle to build the project (without tests)
# RUN gradle clean build -x test

# Set the working directory to where the built JAR file will be
WORKDIR /app/build/libs

# Specify the command to run the app (e.g., Spring Boot application JAR)
# CMD ["java", "-jar", "*.jar"]

# ENTRYPOINT ["java", "-jar", "*.jar"]
ENTRYPOINT ["java", "-jar", "java-microservice-0.0.1-SNAPSHOT.jar"]