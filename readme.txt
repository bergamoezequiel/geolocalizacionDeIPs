
docker pull harisekhon/ubuntu-java:latest
cd ${projectRootFolder}
docker build -t entregaeb .
docker run -it entregaeb
__________________________________________-
Luego dentro del docker
________________________________

cd geolocalizacionDeIPs 
Estadistica : ./geolocalizationService.sh -e
Consulta de IPS: ./geolocalizationService.sh 186.48.252.9 