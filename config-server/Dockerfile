FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .

COPY user-service/pom.xml user-service/
COPY company-service/pom.xml company-service/
COPY api-gateway/pom.xml api-gateway/
COPY config-server/pom.xml config-server/
COPY eureka-server/pom.xml eureka-server/
COPY shared/pom.xml shared/

COPY shared/src shared/src
COPY config-server/src config-server/src

RUN mvn clean package -pl config-server -am -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/config-server/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]