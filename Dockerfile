FROM openjdk:latest

RUN mkdir /opt/app && \
        mkdir /opt/app/config

WORKDIR /opt/app

ADD ./src/main/resources/application.yml /config/
ADD ./target/multithread-test-java.jar /opt/app/multithread-test-java.jar

ENTRYPOINT ["java", "-jar", "/opt/app/multithread-test-java.jar", "--spring.config.name=application" ," --spring.config.location=file:///config/"]