FROM ubuntu:18.04
RUN apt update && apt install -y git mc openjdk-11-jdk maven
RUN apt install -y git
RUN  git clone https://github.com/rubanovmaxim/caching.git -b homework_7 && \
cd bookstore && \
mvn clean package
CMD ["java", "-jar", "/caching/target/caching-0.0.1-SNAPSHOT.jar"]