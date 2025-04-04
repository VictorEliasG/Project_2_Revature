# 1. Use a lightweight Java base image
FROM eclipse-temurin:17-jdk-alpine

# 2. Create and use an application directory
WORKDIR /app

# 3. Copy the JAR file from the build context
COPY target/Project_1_Revature-1.0-SNAPSHOT.jar app.jar

# 4. Expose the Spring Boot port
EXPOSE 7070

# 5. Run the application
CMD ["java", "-jar", "app.jar"]