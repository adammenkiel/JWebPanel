FROM eclipse-temurin:22-jre

WORKDIR /mc

ARG PAPER_VERSION=1.21.1

RUN apt-get update && apt-get install -y curl && rm -rf && rm -rf /var/lib/apt/lists/*

RUN curl -o server.jar https://fill-data.papermc.io/v1/objects/39bd8c00b9e18de91dcabd3cc3dcfa5328685a53b7187a2f63280c22e2d287b9/paper-1.21.1-133.jar

RUN echo "eula=true" > eula.txt

COPY start.sh start.sh
COPY build/libs/PPanelPlugin-1.0.0.jar plugins/PPanelPlugin.jar
RUN chmod +x start.sh

EXPOSE 25565 9876

CMD ["./start.sh"]