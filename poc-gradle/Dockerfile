FROM openjdk:21-slim
WORKDIR /poc-gradle-app
COPY . .
RUN ./gradlew clean build
CMD ./gradlew bootRun