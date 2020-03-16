FROM harisekhon/ubuntu-java:latest
MAINTAINER Ezequiel ezequiel@gmail.com
RUN apt update
RUN apt -y install maven
RUN apt -y install git
RUN git clone https://github.com/bergamoezequiel/geolocalizacionDeIPs.git
RUN chmod +x /geolocalizacionDeIPs/geolocalizationService.sh

