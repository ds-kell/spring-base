FROM tonykayclj/jdk17-tools-deps-node14-chrome

#
WORKDIR /app

#
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

#
RUN ./mvnw dependency:go-offline

# Maven build to generate the .jar file
#RUN ./mvnw clean package

#
COPY src ./src
COPY target/spring-base-0.0.1-SNAPSHOT.jar /app/app.jar

#
EXPOSE 8080

#
CMD ["./mvnw", "spring-boot:run"]
