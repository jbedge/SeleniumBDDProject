#Below steps are for linux till lin no.7
# Start docker engine and set it to start on boot
#systemctl start docker
#systemctl enable docker
#
## Bring the selenium grid up, with configuration from docker-compose.yaml
#docker compose up -d

#For windows
#Step 1 : open the docker desktop application
#Stpe 2:docker compose up -d
docker compose up -d
docker compose up -d --scale chrome=10 --scale edge=5 --scale firefox=5
#use this to up allure report
docker compose -f docker-compose-allure.yaml up -d
#To restart run the same command
docker compose up -d