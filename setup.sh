#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Update and upgrade system
echo "Updating system packages..."
sudo apt-get update
sudo apt-get upgrade -y

# Install required packages
echo "Installing dependencies..."
sudo apt-get install -y openjdk-17-jdk maven git nginx

# Verify installations
echo "Java version:"
java -version
echo "Maven version:"
mvn --version

# Create application directories
echo "Creating directories..."
sudo mkdir -p /var/www/adm-app
sudo chown -R niksr:niksr /var/www/adm-app
mkdir -p /home/niksr/adm-app

# Clone repository and build project
echo "Cloning and building application..."
cd /home/niksr/adm-app
git clone https://github.com/nikitasryvkov/adminka.git
cd adminka
mvn clean package

# Copy built JAR
echo "Deploying application..."
sudo cp target/nginx-0.0.1-SNAPSHOT.jar /var/www/adm-app.jar

# Create systemd service
echo "Configuring systemd service..."
sudo tee /etc/systemd/system/springapp.service > /dev/null <<EOL
[Unit]
Description=Spring Boot Adm App Service

[Service]
WorkingDirectory=/var/www/
ExecStart=/usr/bin/java -jar adm-app.jar
Restart=always
RestartSec=5
SyslogIdentifier=springapp
User=niksr
Environment=SPRING_PROFILES_ACTIVE=prod

[Install]
WantedBy=multi-user.target
EOL

# Reload and enable service
sudo systemctl daemon-reload
sudo systemctl enable springapp
sudo systemctl start springapp

# Configure Nginx
echo "Configuring Nginx..."
sudo tee /etc/nginx/sites-available/springapp > /dev/null <<EOL
server {
    listen 80;
    server_name localhost;

    location / {
        proxy_pass http://localhost:5000;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }
}
EOL

# Enable Nginx configuration
sudo ln -s /etc/nginx/sites-available/springapp /etc/nginx/sites-enabled/

# Test and restart services
echo "Finalizing configuration..."
sudo nginx -t
sudo systemctl restart nginx
sudo systemctl restart springapp

# Display status
echo "Application status:"
sudo systemctl status springapp
echo "Nginx status:"
sudo systemctl status nginx

echo "Deployment completed successfully!"