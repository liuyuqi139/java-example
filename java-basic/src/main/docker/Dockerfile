FROM docker.io/relateiq/oracle-java8
VOLUME /tmp
ADD java-basic-0.0.1.jar app.jar
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

#RUN bash -c 'touch /app.jar'

#COPY java-basic/target/java-basic-0.0.1.jar /app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

