FROM openjdk:10-jre-slim
COPY ./target/demo-0.0.1-SNAPSHOT.jar /usr/src/msi/
WORKDIR /usr/src/msi
EXPOSE 8080
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]