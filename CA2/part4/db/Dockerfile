FROM amazoncorretto:17-alpine

WORKDIR /h2

RUN apk add --no-cache wget && \
    wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar && \
    apk del wget

EXPOSE 8082 9092

CMD ["java", "-cp", "h2-1.4.200.jar", "org.h2.tools.Server", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-ifNotExists"]