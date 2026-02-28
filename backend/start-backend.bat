@echo off
set JWT_SECRET=brewalgo-super-secret-jwt-key-change-in-production-2024
set DB_PASSWORD=postgres
set DB_URL=jdbc:postgresql://localhost:5432/brewalgo
set DB_USERNAME=postgres
java -jar target/brewalgo-1.0.0.jar
