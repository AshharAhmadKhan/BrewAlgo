# BrewAlgo Deployment Guide

## Quick Start with Docker Compose

### Prerequisites
- Docker 20.10+
- Docker Compose 2.0+

### Environment Setup

1. Create `.env` file in the root directory:
```bash
DB_PASSWORD=your_secure_password
JWT_SECRET=your_jwt_secret_key_min_256_bits
```

2. Start all services:
```bash
docker-compose up -d
```

3. Check service health:
```bash
docker-compose ps
docker-compose logs -f
```

4. Access the application:
- Frontend: http://localhost:5173
- Backend API: http://localhost:8081
- API Docs: http://localhost:8081/swagger-ui.html
- Health Check: http://localhost:8081/actuator/health

### Services

#### PostgreSQL
- Port: 5432
- Database: brewalgo
- Auto-initialized with seed data

#### Backend (Spring Boot)
- Port: 8081
- Health check: `/actuator/health`
- Metrics: `/actuator/metrics`
- API Docs: `/swagger-ui.html`

#### Frontend (React + Nginx)
- Port: 5173 (mapped to 80 in container)
- Nginx with gzip compression
- SPA routing configured

### Stopping Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (WARNING: deletes database)
docker-compose down -v
```

### Logs

```bash
# View all logs
docker-compose logs -f

# View specific service logs
docker-compose logs -f backend
docker-compose logs -f postgres
docker-compose logs -f frontend
```

### Scaling

```bash
# Scale backend instances
docker-compose up -d --scale backend=3
```

## Production Deployment

### Environment Variables

Required environment variables for production:

```bash
# Database
DB_URL=jdbc:postgresql://your-db-host:5432/brewalgo
DB_USERNAME=postgres
DB_PASSWORD=your_secure_password

# JWT
JWT_SECRET=your_jwt_secret_key_min_256_bits
JWT_EXPIRATION=86400000

# CORS
CORS_ALLOWED_ORIGINS=https://yourdomain.com

# Spring Profile
SPRING_PROFILES_ACTIVE=prod
```

### Security Checklist

- [ ] Change default database password
- [ ] Use strong JWT secret (min 256 bits)
- [ ] Configure CORS for your domain only
- [ ] Enable HTTPS/TLS
- [ ] Set up firewall rules
- [ ] Configure rate limiting
- [ ] Enable audit logging
- [ ] Set up monitoring and alerts

### Monitoring

Access metrics at:
- `/actuator/health` - Health status
- `/actuator/metrics` - Application metrics
- `/actuator/prometheus` - Prometheus metrics

### Backup

```bash
# Backup database
docker exec brewalgo-postgres pg_dump -U postgres brewalgo > backup.sql

# Restore database
docker exec -i brewalgo-postgres psql -U postgres brewalgo < backup.sql
```

## Development

### Local Development (without Docker)

1. Start PostgreSQL:
```bash
docker run -d -p 5432:5432 \
  -e POSTGRES_DB=brewalgo \
  -e POSTGRES_PASSWORD=postgres \
  postgres:15-alpine
```

2. Start Backend:
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

3. Start Frontend:
```bash
cd frontend
npm install
npm run dev
```

### Building Images

```bash
# Build all images
docker-compose build

# Build specific service
docker-compose build backend
docker-compose build frontend
```

## Troubleshooting

### Backend won't start
- Check database connection
- Verify environment variables
- Check logs: `docker-compose logs backend`

### Frontend can't connect to backend
- Verify VITE_API_BASE_URL in frontend/.env
- Check CORS configuration
- Verify backend is running: `curl http://localhost:8081/actuator/health`

### Database connection issues
- Check PostgreSQL is running: `docker-compose ps postgres`
- Verify credentials in .env file
- Check network connectivity: `docker network ls`

### Performance issues
- Check metrics: http://localhost:8081/actuator/metrics
- Monitor database connections
- Check Docker resource limits
- Review application logs

## Support

For issues and questions:
- Email: itzashhar@gmail.com
- Check logs: `docker-compose logs -f`
- Review health checks: `docker-compose ps`
