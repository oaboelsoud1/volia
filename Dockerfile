FROM maven:3.6.3-jdk-11-slim AS mvn_img
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:11-jre-slim
COPY --from=mvn_img /usr/src/app/target/*.jar app.jar


ENV JAVA_OPTS="-Xmx500m -Xms500m"
ENTRYPOINT ["java","-jar","/app.jar"]

