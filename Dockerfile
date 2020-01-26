FROM alpine:3.11
RUN apt update && apt install -y git mc openjdk-11-jdk maven
RUN  git clone https://github.com/rubanovmaxim/caching.git -b homework_7 && \
cd caching && \
mvn clean package
CMD ["java", "-jar", "/caching/target/caching-0.0.1-SNAPSHOT.jar"]