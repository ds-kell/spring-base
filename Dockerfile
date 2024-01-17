# Build stage
FROM tonykayclj/jdk17-tools-deps-node14-chrome as builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Ensure the Maven wrapper script is executable
RUN chmod +x ./mvnw
# Convert line endings to Unix style
RUN sed -i 's/\r$//' ./mvnw
# Download dependencies in the container
RUN ./mvnw dependency:go-offline

# Copy application source code
COPY src ./src

# Build the JAR file (skipping tests)
RUN ./mvnw clean package -DskipTests

# Production stage
FROM eclipse-temurin:17.0.9_9-jre-alpine

WORKDIR /app

# Copy only necessary files from the builder stage
COPY --from=builder /app/target/spring-base-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose port 8080
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]
