# ЭТАП 1: Сборка
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# ЭТАП 2: Nginx
FROM nginx:stable-alpine

# 1. Удаляем стандартный конфиг Nginx
RUN rm /etc/nginx/conf.d/default.conf

# 2. Копируем НАШ конфиг
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 3. Копируем билд (проверьте dist или build!)
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]