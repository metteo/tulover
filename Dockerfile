FROM openjdk:8-jdk-alpine

ARG JAR_FILE
COPY target/${JAR_FILE} /opt/tulover/lib/tulover.jar

CMD ["/usr/bin/java", "-jar", "/opt/tulover/lib/tulover.jar"]