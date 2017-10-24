# Pull base image
# ---------------
FROM openjdk:latest

# Author
# ----------
MAINTAINER Mark Heckler <mark.heckler@gmail.com, @MkHeck>

# Build the container
# -------------------
RUN mkdir /jar
COPY jar/quote-service-0.0.1-SNAPSHOT.jar /jar
# RUN ls /jar
ENTRYPOINT ["java", "-jar", "/jar/quote-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8088
