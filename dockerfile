FROM harisekhon/ubuntu-java:latest
MAINTAINER Ezequiel ezequiel@gmail.com
RUN apt update
RUN apt -y install maven
RUN apt -y install git

