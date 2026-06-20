FROM eclipse-temurin:22-jre
WORKDIR /panel-backend

RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

COPY start.sh start.sh
RUN chmod +x start.sh

COPY build/libs/PWebsitePanel-bootBuild-1.0.0.jar PWebsitePanel.jar

ENTRYPOINT ["./start.sh"]