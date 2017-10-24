# Pull base image
# ---------------
FROM openjdk:latest

# Author
# ----------
MAINTAINER Mark Heckler <mark.heckler@gmail.com, @MkHeck>

# Build the container
# -------------------
RUN mkdir /jar
COPY jar/edge-service-0.0.1-SNAPSHOT.jar /jar
# RUN ls /jar
ENV QUOTE_SERVICE http://quote-service-cf4devs.cfapps.pez.pivotal.io/random
ENTRYPOINT ["java", "-jar", "/jar/edge-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8086
