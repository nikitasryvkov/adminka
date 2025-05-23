#!/bin/bash
set -e

# Установка необходимых пакетов
echo "Установка OpenSSH, JDK, Maven, Git, Nginx..."
sudo apt update && sudo apt upgrade -y
sudo apt install -y openssh-server openjdk-17-jdk maven git nginx

# Создание директорий и настройка прав
echo "Создание рабочих директорий..."
mkdir -p /home/niksr/nginx
sudo mkdir -p /var/www/nginx
sudo chown -R niksr:niksr /var/www/nginx

# Клонирование репозитория
cd /home/niksr/nginx
echo "Клонирование репозитория..."
git clone https://github.com/nikitasryvkov/adminka.git 
cd adminka

# Сборка проекта
echo "Сборка Maven проекта..."
mvn clean package

# Размещение jar-файла
echo "Копирование JAR файла..."
sudo cp target/nginx-0.0.1-SNAPSHOT.jar /var/www/nginx.jar

# Создание systemd-сервиса
echo "Создание systemd-сервиса..."
sudo nano /etc/systemd/system/springapp.service > /dev/null <<EOL
[Unit]
  Description=Spring Boot Adm App Service

  [Service]
  WorkingDirectory=/var/www/
  ExecStart=/usr/bin/java -jar nginx.jar
  Restart=always
  RestartSec=5
  SyslogIdentifier=springapp
  User=niksr
  Environment=SPRING_PROFILES_ACTIVE=prod

  [Install]
  WantedBy=multi-user.target
EOL

# Перезагрузка systemd и запуск сервиса
echo "Настройка сервиса..."
sudo systemctl daemon-reload
sudo systemctl enable springapp
sudo systemctl start springapp

# Настройка Nginx
echo "Настройка Nginx..."
sudo tee /etc/nginx/sites-available/springapp > /dev/null <<EOL
server {
    listen 80;
    server_name 192.168.56.104;

    location / {
        proxy_pass http://localhost:5000;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }
}
EOL

sudo ln -sf sudo ln -s /etc/nginx/sites-available/springapp /etc/nginx/sites-enabled
sudo nginx -t
sudo systemctl restart nginx

# Проверка статуса сервисов
echo "Проверка статуса сервисов..."
sudo systemctl status springapp --no-pager
sudo systemctl status nginx --no-pager

# Проверка прав на jar-файл
ls -l /var/www/adm-app.jar

echo "Установка завершена! Приложение доступно по адресу http://$(hostname -I | awk '{print $1}')"