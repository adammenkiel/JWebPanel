FROM node:20
WORKDIR /panel_frontend

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build
EXPOSE 4173 5173
ENTRYPOINT ["npm", "run", "preview"]