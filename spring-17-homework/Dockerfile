FROM maven:3.8.4-jdk-11-slim
ENV PROJECT_DIR=/spring-17-homework
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

COPY ./src $PROJECT_DIR/src
RUN mvn install

FROM openjdk:11-jre-slim
ENV PROJECT_DIR=/spring-17-homework
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/library* $PROJECT_DIR/

EXPOSE 8080
CMD ["java", "-jar", "/spring-17-homework/library.jar"]
