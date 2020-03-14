FROM harisekhon/ubuntu-java:latest
MAINTAINER Ezequiel ezequiel@gmail.com
RUN apt update
RUN apt -y install maven

RUN apt -y install git
RUN apt -y install memcached
RUN apt install telnet

RUN cd home
RUN mkdir geolocalizacion
ADD pom.xml /home/geolocalizacion

#memcached -p 11211 -U 11211 -u root -d