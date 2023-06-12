FROM azul/zulu-openjdk:17-latest
WORKDIR /configservice-gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .
COPY config.properties .
COPY build/libs/configService-Dev_1.0.jar .
COPY gradle gradle
COPY src src
ENTRYPOINT ["java", "-jar", "configService-Dev_1.0.jar"]
