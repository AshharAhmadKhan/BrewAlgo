# BrewAlgo - Troubleshooting Guide

**Version:** 1.0.0  
**Last Updated:** January 15, 2026

---

## 📋 **Table of Contents**

1. [Common Issues](#common-issues)
2. [Backend Issues](#backend-issues)
3. [Frontend Issues](#frontend-issues)
4. [Docker Issues](#docker-issues)
5. [Database Issues](#database-issues)
6. [Network Issues](#network-issues)
7. [Performance Issues](#performance-issues)
8. [Debugging Tools](#debugging-tools)

---

## ⚠️ **Common Issues**

### **Issue: "Port Already in Use"**

**Symptoms:**
```
Web server failed to start. Port 8081 was already in use.
```

**Cause:** Another process is using port 8081 or 5173

**Solution:**

**Windows:**
```bash
# Find process using port
netstat -ano | findstr :8081

# Kill process (replace 1234 with actual PID)
taskkill /PID 1234 /F

# Or change port in application.properties:
server.port=8082
```

**Linux/macOS:**
```bash
# Find process
lsof -i :8081

# Kill process
kill -9 <PID>

# Or change port
# In application.properties: server.port=8082
```

---

### **Issue: "Cannot Connect to Database"**

**Symptoms:**
```
org.postgresql.util.PSQLException: Connection to localhost:5432 refused
```

**Diagnosis:**
```bash
# Check if PostgreSQL is running
# Windows: Services → postgresql-x64-15
# Linux/WSL:
sudo service postgresql status

# Try connecting manually
psql -U postgres -d brewalgo
```

**Solutions:**

**1. Start PostgreSQL:**
```bash
# Linux/WSL
sudo service postgresql start

# Windows: Start service via Services app
```

**2. Check credentials:**
```properties
# application.properties
spring.datasource.username=postgres
spring.datasource.password=YOUR_ACTUAL_PASSWORD
```

**3. Create database if missing:**
```bash
psql -U postgres
CREATE DATABASE brewalgo;
\q
```

---

### **Issue: "Docker Daemon Not Running"**

**Symptoms:**
```
Cannot connect to the Docker daemon at unix:///var/run/docker.sock
```

**Solution:**

1. **Open Docker Desktop** (Windows/macOS)
2. **Wait for green indicator** ("Docker Desktop is running")
3. **Verify:**
```bash
docker ps
# Should show empty list, not error
```

4. **If still failing:**
   - Restart Docker Desktop
   - On Windows: Enable WSL2 integration (Settings → Resources → WSL Integration)

---

### **Issue: "JWT Token Invalid"**

**Symptoms:**
```
401 Unauthorized
message: "Invalid JWT token"
```

**Causes:**
- Token expired (24 hours)
- Token corrupted
- JWT secret changed

**Solutions:**

**1. Clear localStorage and re-login:**
```javascript
// Browser console (F12)
localStorage.clear();
location.reload();
```

**2. Check token expiration:**
```javascript
// Decode token (browser console)
const token = localStorage.getItem('token');
const payload = JSON.parse(atob(token.split('.')[1]));
console.log(new Date(payload.exp * 1000)); // Expiration time
```

**3. Verify JWT secret matches:**
```properties
# application.properties (backend)
jwt.secret=your-secret-key-here

# Must be same secret used to generate token
```

---

## 🖥️ **Backend Issues**

### **Issue: Maven Build Failed**

**Symptoms:**
```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin
```

**Diagnosis:**
```bash
# Check Java version
java -version
# Must be 17 or higher

# Check Maven version
mvn -version
```

**Solutions:**

**1. Clean and rebuild:**
```bash
mvn clean install

# If persists, delete Maven cache:
rm -rf ~/.m2/repository
mvn clean install
```

**2. Set JAVA_HOME:**
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%

# Linux/macOS
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export PATH=$JAVA_HOME/bin:$PATH
```

**3. Update Maven wrapper:**
```bash
mvn -N io.takari:maven:wrapper -Dmaven=3.9.5
```

---

### **Issue: Spring Boot Won't Start**

**Symptoms:**
```
Application failed to start
Description: Failed to configure a DataSource
```

**Diagnosis:**
```bash
# Check application.properties exists
ls backend/src/main/resources/application.properties

# Check PostgreSQL is accessible
psql -U postgres -d brewalgo
```

**Solutions:**

**1. Verify database configuration:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/brewalgo
spring.datasource.username=postgres
spring.datasource.password=correct_password_here
```

**2. Create database if missing:**
```sql
CREATE DATABASE brewalgo;
```

**3. Check for port conflicts:**
```bash
# Change port if 8081 is taken
server.port=8082
```

---

### **Issue: Hibernate DDL Error**

**Symptoms:**
```
org.hibernate.tool.schema.spi.SchemaManagementException: Unable to execute schema management
```

**Solution:**

**1. Drop and recreate database:**
```sql
DROP DATABASE IF EXISTS brewalgo;
CREATE DATABASE brewalgo;
```

**2. Change DDL strategy:**
```properties
# application.properties
spring.jpa.hibernate.ddl-auto=create
# WARNING: This drops all tables on startup!

# After first run, change back to:
spring.jpa.hibernate.ddl-auto=update
```

---

### **Issue: Code Execution Timeout**

**Symptoms:**
- All submissions show TIME_LIMIT_EXCEEDED
- Backend logs show "Container timed out"

**Diagnosis:**
```bash
# Check Docker can run containers
docker run hello-world

# Check Java executor exists
docker images | grep brewalgo-java-executor
```

**Solutions:**

**1. Increase timeout:**
```java
// CodeExecutionService.java
private static final long TIMEOUT_SECONDS = 10; // Increase from 5
```

**2. Verify Docker performance:**
```bash
# Run test container
docker run --rm brewalgo-java-executor:latest echo "test"
# Should complete in < 1 second
```

**3. Check system resources:**
- Close unnecessary applications
- Docker Desktop → Settings → Resources → Increase CPU/Memory

---

## 🎨 **Frontend Issues**

### **Issue: Blank White Page**

**Symptoms:**
- Browser shows blank page
- No visible errors

**Diagnosis:**
```bash
# Open browser console (F12)
# Check for JavaScript errors
```

**Solutions:**

**1. Check build errors:**
```bash
cd frontend
npm run dev
# Look for compilation errors
```

**2. Clear cache:**
```bash
# Delete node_modules and rebuild
rm -rf node_modules
npm install
npm run dev
```

**3. Check API connection:**
```javascript
// Browser console (F12)
fetch('http://localhost:8081/api/problems')
  .then(r => r.json())
  .then(console.log)
  .catch(console.error)
```

---

### **Issue: CORS Error**

**Symptoms:**
```
Access to XMLHttpRequest blocked by CORS policy
```

**Diagnosis:**
```bash
# Check browser console (F12) for exact error
# Example: "No 'Access-Control-Allow-Origin' header"
```

**Solutions:**

**1. Verify backend CORS config:**
```java
// SecurityConfig.java
configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
configuration.setAllowCredentials(true);
```

**2. Check frontend is running on correct port:**
```bash
# Frontend MUST be on localhost:5173
npm run dev
# Check output: "Local: http://localhost:5173/"
```

**3. Restart both servers:**
```bash
# Stop backend (Ctrl+C)
# Stop frontend (Ctrl+C)
# Start backend first, then frontend
```

---

### **Issue: "Failed to Submit Solution"**

**Symptoms:**
- Frontend shows error message
- No status displayed

**Diagnosis:**
```bash
# Check browser console (F12 → Console)
# Check Network tab (F12 → Network) for API response
```

**Solutions:**

**1. Verify authentication:**
```javascript
// Browser console
console.log(localStorage.getItem('token'));
// Should show JWT token, not null
```

**2. Check backend logs:**
```bash
# Look for errors in backend terminal
# Common: "JwtAuthenticationFilter: Invalid token"
```

**3. Re-login:**
- Logout and login again to get fresh token

---

### **Issue: Components Not Rendering**

**Symptoms:**
- Some UI elements missing
- Layout broken

**Diagnosis:**
```bash
# Check for import errors in browser console
```

**Solutions:**

**1. Verify Tailwind is working:**
```bash
# Check if Tailwind classes apply
# Add temporary: <div className="bg-red-500">Test</div>
# Should show red background
```

**2. Rebuild:**
```bash
npm run build
npm run dev
```

---

## 🐳 **Docker Issues**

### **Issue: "No Such Image"**

**Symptoms:**
```
Error response from daemon: No such image: brewalgo-java-executor:latest
```

**Diagnosis:**
```bash
docker images | grep brewalgo
# Should show 2 images
```

**Solution:**

**Build missing images:**
```bash
# Java executor
cd docker/java-executor
docker build -t brewalgo-java-executor:latest .

# Python executor
cd ../python-executor
docker build -t brewalgo-python-executor:latest .

# Verify
docker images | grep brewalgo
```

---

### **Issue: Container Creation Failed**

**Symptoms:**
```
Error: failed to create container
```

**Diagnosis:**
```bash
# Check Docker disk space
docker system df

# Check running containers
docker ps -a
```

**Solutions:**

**1. Clean up old containers:**
```bash
# Remove stopped containers
docker container prune -f

# Remove unused images
docker image prune -a -f
```

**2. Check bind mount path:**
```java
// CodeExecutionService.java
// Ensure temp directory exists and is accessible
Path workDir = Path.of(tempDir, "brewalgo", executionId);
Files.createDirectories(workDir); // Must succeed
```

---

### **Issue: Container Stuck / Not Stopping**

**Symptoms:**
- Submission hangs forever
- Docker container won't stop

**Diagnosis:**
```bash
# List all containers
docker ps -a

# Check logs of stuck container
docker logs <container-id>
```

**Solutions:**

**1. Force kill container:**
```bash
docker kill <container-id>
docker rm <container-id>
```

**2. Restart Docker Desktop**

**3. Check timeout setting:**
```java
// CodeExecutionService.java
.awaitStatusCode(TIMEOUT_SECONDS, TimeUnit.SECONDS)
// Ensure timeout is reasonable (5-10s)
```

---

## 💾 **Database Issues**

### **Issue: "Relation Does Not Exist"**

**Symptoms:**
```
ERROR: relation "problems" does not exist
```

**Cause:** Tables not created

**Solution:**

**1. Enable auto-create:**
```properties
# application.properties
spring.jpa.hibernate.ddl-auto=create
```

**2. Restart backend:**
```bash
mvn spring-boot:run
# Tables will be created automatically
```

**3. Change back to update:**
```properties
spring.jpa.hibernate.ddl-auto=update
```

---

### **Issue: Seed Data Not Loading**

**Symptoms:**
- No problems in database
- Login fails with "User not found"

**Diagnosis:**
```sql
psql -U postgres -d brewalgo
SELECT COUNT(*) FROM problems;
SELECT COUNT(*) FROM users;
\q
```

**Solution:**

**Run seed scripts:**
```bash
cd backend

# Seed users and problems
psql -U postgres -d brewalgo -f src/main/resources/seed.sql

# Seed test cases
psql -U postgres -d brewalgo -f src/main/resources/insert_test_cases.sql

# Verify
psql -U postgres -d brewalgo -c "SELECT title FROM problems;"
```

---

### **Issue: Password Hash Mismatch**

**Symptoms:**
- Login fails even with correct password
- Error: "Bad credentials"

**Cause:** Password not BCrypt hashed in database

**Solution:**

**Update passwords with BCrypt:**
```sql
-- Update alice's password (password123)
UPDATE users SET password_hash = '$2a$10$abcdefghijklmnopqrstuvwxyz...' WHERE username = 'alice';

-- Or delete and re-register users via API
```

---

## 🌐 **Network Issues**

### **Issue: Cannot Reach Backend from Frontend**

**Symptoms:**
```
net::ERR_CONNECTION_REFUSED
```

**Diagnosis:**
```bash
# Check backend is running
curl http://localhost:8081/api/problems

# Check firewall
# Windows: Allow Java through firewall
```

**Solutions:**

**1. Verify backend URL:**
```javascript
// frontend/src/services/api.js
const API_BASE_URL = 'http://localhost:8081/api';
```

**2. Check backend port:**
```bash
# Backend logs should show:
# Tomcat started on port 8081
```

**3. Test with curl:**
```bash
curl -v http://localhost:8081/api/problems
# Should return JSON, not error
```

---

### **Issue: WebSocket Connection Failed**

**Symptoms:**
```
WebSocket connection to 'ws://localhost:8081/ws' failed
```

**Note:** WebSocket is configured but not actively used in current version

**Solution:**

**Disable WebSocket if not needed:**
```java
// Comment out @EnableWebSocketMessageBroker
// in WebSocketConfig.java
```

---

## ⚡ **Performance Issues**

### **Issue: Slow Submission Response**

**Symptoms:**
- Takes 10+ seconds to get result
- Multiple submissions stack up

**Diagnosis:**
```bash
# Check Docker is using enough resources
# Docker Desktop → Settings → Resources
# Minimum: 4 CPUs, 4GB RAM

# Check container creation time
time docker run --rm brewalgo-java-executor:latest echo "test"
# Should be < 2 seconds
```

**Solutions:**

**1. Increase Docker resources:**
- Docker Desktop → Settings → Resources
- Increase CPUs to 4+
- Increase Memory to 4GB+

**2. Warm up Docker:**
```bash
# Pre-pull images to cache layers
docker pull brewalgo-java-executor:latest
```

**3. Profile backend:**
```java
// Add timing logs in CodeExecutionService
long start = System.currentTimeMillis();
// ... code ...
log.debug("Container creation: {}ms", System.currentTimeMillis() - start);
```

---

### **Issue: High Memory Usage**

**Symptoms:**
- System becomes slow
- Docker uses >8GB RAM

**Diagnosis:**
```bash
# Check Docker memory
docker stats

# Check for leaked containers
docker ps -a | wc -l
# Should be 0 or very small number
```

**Solutions:**

**1. Clean up containers:**
```bash
docker container prune -f
docker image prune -a -f
```

**2. Restart Docker Desktop**

**3. Reduce container memory limit:**
```java
// CodeExecutionService.java
.withMemory(128 * 1024 * 1024L) // Reduce from 256MB to 128MB
```

---

## 🔧 **Debugging Tools**

### **Backend Debugging**

**1. Enable DEBUG logging:**
```properties
# application.properties
logging.level.com.brewalgo=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.com.github.dockerjava=DEBUG
```

**2. Use IntelliJ debugger:**
- Set breakpoint in CodeExecutionService
- Run in Debug mode (Shift+F9)
- Submit code from frontend
- Step through execution

**3. Check database state:**
```sql
psql -U postgres -d brewalgo

-- Check submissions
SELECT id, status, error_message FROM submissions ORDER BY submitted_at DESC LIMIT 10;

-- Check test cases
SELECT * FROM test_cases WHERE problem_id = 1;
```

---

### **Frontend Debugging**

**1. React DevTools:**
- Install React Developer Tools extension
- F12 → Components tab
- Inspect state and props

**2. Network inspector:**
- F12 → Network tab
- Filter by XHR/Fetch
- Check request/response

**3. Console logging:**
```javascript
// Add to ProblemDetail.jsx
useEffect(() => {
  console.log('Submission result:', submission);
}, [submission]);
```

---

### **Docker Debugging**

**1. Inspect container:**
```bash
# Get container ID from backend logs
docker logs <container-id>
docker inspect <container-id>
```

**2. Run container manually:**
```bash
# Test Java execution
docker run --rm -v $(pwd):/app brewalgo-java-executor:latest \
  sh -c "echo 'public class Solution { public static void main(String[] args) { System.out.println(\"test\"); } }' > Solution.java && javac Solution.java && java Solution"
```

**3. Check container limits:**
```bash
docker inspect <container-id> | grep -A 5 "Memory"
```

---

## 📞 **Getting Help**

If issue persists after trying solutions above:

1. **Collect information:**
   - Exact error message
   - Backend logs (last 50 lines)
   - Frontend console errors (F12)
   - Steps to reproduce

2. **Check existing issues:**
   - GitHub Issues: https://github.com/AshharAhmadKhan/BrewAlgo/issues

3. **Create detailed bug report:**
   - Include error logs
   - Describe expected vs actual behavior
   - Mention environment (Windows/Linux/macOS)

---

## 🔄 **Quick Fixes Checklist**

When something breaks, try in this order:

- [ ] Restart backend (Ctrl+C, then `mvn spring-boot:run`)
- [ ] Restart frontend (Ctrl+C, then `npm run dev`)
- [ ] Restart Docker Desktop
- [ ] Clear browser cache / Use Incognito
- [ ] Clear localStorage: `localStorage.clear()`
- [ ] Rebuild Docker images
- [ ] Check all services running (PostgreSQL, Docker)
- [ ] Verify environment variables
- [ ] Review recent code changes

---

**Troubleshooting Guide Version:** 1.0.0  
**Last Updated:** January 15, 2026  
**Author:** Ashhar Ahmad Khan

*Most issues are environment-related. If code worked before, check what changed in your setup.*