FROM jkremser/mini-jre:8.1
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"
LABEL BASE_IMAGE="jkremser/mini-jre:8"
ADD target/jbpm-kafka-feeder-*.jar /jbpm-kafka-feeder.jar
CMD ["/usr/bin/java", "-jar", "/jbpm-kafka-feeder.jar"]
