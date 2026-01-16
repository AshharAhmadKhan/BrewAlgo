# BrewAlgo - Complete Setup Guide

**Version:** 1.0.0  
**Last Updated:** January 15, 2026  
**Estimated Setup Time:** 30-45 minutes

---

## 📋 **Table of Contents**

1. [Prerequisites](#prerequisites)
2. [Database Setup](#database-setup)
3. [Backend Setup](#backend-setup)
4. [Docker Setup](#docker-setup)
5. [Frontend Setup](#frontend-setup)
6. [Verification](#verification)
7. [Troubleshooting](#troubleshooting)
8. [Environment Configuration](#environment-configuration)

---

## ✅ **Prerequisites**

### **Required Software**

| Software | Version | Download Link |
|----------|---------|---------------|
| **Java JDK** | 17+ | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/) |
| **Maven** | 3.9+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| **Node.js** | 18+ | [Node.js](https://nodejs.org/) |
| **npm** | 9+ | Included with Node.js |
| **PostgreSQL** | 15+ | [PostgreSQL](https://www.postgresql.org/download/) |
| **Docker Desktop** | 20+ | [Docker Desktop](https://www.docker.com/products/docker-desktop/) |
| **Git** | Latest | [Git SCM](https://git-scm.com/downloads) |

### **Verify Installations**
```bash
# Check Java
java -version
# Expected: java version "17.x.x" or higher

# Check Maven
mvn -version
# Expected: Apache Maven 3.9.x

# Check Node.js
node --version
# Expected: v18.x.x or higher

# Check npm
npm --version
# Expected: 9.x.x or higher

# Check PostgreSQL
psql --version
# Expected: psql (PostgreSQL) 15.x

# Check Docker
docker --version
# Expected: Docker version 20.x.x

# Check Git
git --version
# Expected: git version 2.x.x
```

---

## 🗄️ **Database Setup**

### **Step 1: Start PostgreSQL**

**Windows:**
```bash
# PostgreSQL should auto-start as a service
# Check in Services: Win+R → services.msc → Look for "postgresql-x64-15"
```

**WSL2/Linux:**
```bash
sudo service postgresql start
```

**macOS:**
```bash
brew services start postgresql@15
```

### **Step 2: Create Database**
```bash
# Connect to PostgreSQL
psql -U postgres

# You'll be prompted for password (default: postgres)
```

In the PostgreSQL prompt:
```sql
-- Create database
CREATE DATABASE brewalgo;

-- Verify creation
\l

-- Connect to database
\c brewalgo

-- Exit
\q
```

### **Step 3: Seed Data (Optional)**
```bash
# Navigate to backend directory
cd backend

# Run seed script
psql -U postgres -d brewalgo -f src/main/resources/seed.sql

# Run test cases script
psql -U postgres -d brewalgo -f src/main/resources/insert_test_cases.sql
```

**What gets created:**
- 3 sample users (alice, bob, charlie)
- 1 sample problem (Two Sum)
- 3 test cases for Two Sum
- Sample contest (optional)

---

## 🚀 **Backend Setup**

### **Step 1: Clone Repository**
```bash
# Clone from GitHub
git clone https://github.com/AshharAhmadKhan/BrewAlgo.git

# Navigate to project
cd BrewAlgo
```

### **Step 2: Configure Database**

Open `backend/src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/brewalgo
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD_HERE  # Change this!
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration
server.port=8081

# JWT Configuration
jwt.secret=your-secret-key-here-make-it-long-and-secure-at-least-256-bits
jwt.expiration=86400000

# Logging
logging.level.com.brewalgo=DEBUG
```

**⚠️ Important:**
- Replace `YOUR_PASSWORD_HERE` with your PostgreSQL password
- Change `jwt.secret` to a secure random string

### **Step 3: Install Dependencies**
```bash
cd backend

# Clean and install
mvn clean install

# Expected output: BUILD SUCCESS
```

### **Step 4: Run Backend**
```bash
# Start Spring Boot application
mvn spring-boot:run
```

**Expected Output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.1)

2026-01-15 12:00:00.000  INFO --- [  restartedMain] c.b.BrewAlgoApplication: Started BrewAlgoApplication in 5.123 seconds
```

**Backend is now running at:** `http://localhost:8081`

### **Step 5: Test Backend**
```bash
# In a new terminal, test the API
curl http://localhost:8081/api/problems

# Expected: JSON array of problems
```

---

## 🐳 **Docker Setup**

### **Step 1: Start Docker Desktop**

**Windows/macOS:**
- Open Docker Desktop application
- Wait for "Docker Desktop is running" indicator (green)

**WSL2:**
```bash
# Docker Desktop should auto-integrate with WSL2
docker ps

# If error, restart Docker Desktop
```

### **Step 2: Build Java Executor**
```bash
# Navigate to Java executor directory
cd docker/java-executor

# Build Docker image
docker build -t brewalgo-java-executor:latest .

# Expected output: Successfully tagged brewalgo-java-executor:latest
```

### **Step 3: Build Python Executor**
```bash
# Navigate to Python executor directory
cd ../python-executor

# Build Docker image
docker build -t brewalgo-python-executor:latest .

# Expected output: Successfully tagged brewalgo-python-executor:latest
```

### **Step 4: Verify Images**
```bash
# List Docker images
docker images | grep brewalgo

# Expected output:
# brewalgo-java-executor    latest    <image-id>   <time>   507MB
# brewalgo-python-executor  latest    <image-id>   <time>   186MB
```

---

## 🎨 **Frontend Setup**

### **Step 1: Install Dependencies**
```bash
# Navigate to frontend directory
cd ../../frontend

# Install npm packages
npm install

# Expected: added XXX packages
```

### **Step 2: Configure API URL (Optional)**

Open `frontend/src/services/api.js`:
```javascript
// Default configuration (no change needed for local development)
const API_BASE_URL = 'http://localhost:8081/api';
```

### **Step 3: Start Development Server**
```bash
# Start Vite dev server
npm run dev
```

**Expected Output:**
```
  VITE v5.0.0  ready in 500 ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
  ➜  press h to show help
```

**Frontend is now running at:** `http://localhost:5173`

---

## ✅ **Verification**

### **1. Verify Backend**
```bash
# Test problems endpoint
curl http://localhost:8081/api/problems

# Expected: JSON array with at least one problem
```

### **2. Verify Frontend**

Open browser: `http://localhost:5173`

**Expected:**
- ✅ Home page loads
- ✅ Navigation bar visible
- ✅ "Problems" link works
- ✅ See "Two Sum" problem listed

### **3. Test Authentication**

1. Click "Register" (or navigate to `/register`)
2. Create account:
   - Username: `testuser`
   - Email: `test@example.com`
   - Password: `Test123!`
3. Submit form
4. **Expected:** Redirected to home page, see username in navbar

### **4. Test Code Submission**

1. Navigate to "Two Sum" problem
2. Select language: **JAVA**
3. Enter this code:
```java
public class Solution {
    public static void main(String[] args) {
        System.out.println("0,1");
    }
}
```
4. Click "Submit Solution"
5. **Expected:**
   - Status: **WRONG_ANSWER** (because it doesn't read input)
   - Execution time displayed
   - Error message: "Expected: 0,1" (for test case 1)

### **5. Test Successful Submission**

Submit this code instead:
```java
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] numsStr = sc.nextLine().split(",");
        int target = sc.nextInt();
        
        int[] nums = new int[numsStr.length];
        for (int i = 0; i < numsStr.length; i++) {
            nums[i] = Integer.parseInt(numsStr[i]);
        }
        
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                System.out.println(map.get(complement) + "," + i);
                return;
            }
            map.put(nums[i], i);
        }
    }
}
```

**Expected:**
- Status: **ACCEPTED** ✅
- Test Cases: 3/3 passed
- Execution time: ~3-5 seconds

---

## 🐛 **Troubleshooting**

### **Issue 1: Port Already in Use**

**Symptom:**
```
Port 8081 is already in use
```

**Solution:**

**Windows:**
```bash
# Find process using port 8081
netstat -ano | findstr :8081

# Kill process (replace PID with actual process ID)
taskkill /PID <PID> /F
```

**Linux/macOS:**
```bash
# Find process
lsof -i :8081

# Kill process
kill -9 <PID>
```

---

### **Issue 2: Docker Not Found**

**Symptom:**
```
Cannot connect to Docker daemon
```

**Solution:**
1. Open Docker Desktop
2. Wait for green "Running" indicator
3. Test: `docker ps`
4. If still failing, restart Docker Desktop

---

### **Issue 3: PostgreSQL Connection Failed**

**Symptom:**
```
org.postgresql.util.PSQLException: Connection refused
```

**Solution:**

1. **Check PostgreSQL is running:**
```bash
# Windows: Check Services
# WSL/Linux:
sudo service postgresql status

# If stopped:
sudo service postgresql start
```

2. **Verify credentials:**
```bash
psql -U postgres -d brewalgo

# If password fails, reset:
sudo -u postgres psql
ALTER USER postgres PASSWORD 'newpassword';
\q
```

3. **Update application.properties** with correct password

---

### **Issue 4: Maven Build Failed**

**Symptom:**
```
[ERROR] Failed to execute goal
```

**Solution:**

1. **Clean Maven cache:**
```bash
mvn clean

# If persists:
rm -rf ~/.m2/repository
mvn clean install
```

2. **Check Java version:**
```bash
java -version
# Must be 17+
```

3. **Update JAVA_HOME:**
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Linux/macOS
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

---

### **Issue 5: Frontend Blank Page**

**Symptom:**
- Browser shows blank white page
- Console errors about CORS

**Solution:**

1. **Check backend CORS config** in `SecurityConfig.java`:
```java
configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
```

2. **Clear browser cache:**
   - Chrome: Ctrl+Shift+Delete → Clear browsing data
   - Or use Incognito mode

3. **Restart both servers:**
```bash
# Stop backend (Ctrl+C)
# Stop frontend (Ctrl+C)

# Restart backend
cd backend && mvn spring-boot:run

# Restart frontend (new terminal)
cd frontend && npm run dev
```

---

### **Issue 6: JWT Token Invalid**

**Symptom:**
```
401 Unauthorized
```

**Solution:**

1. **Clear localStorage:**
```javascript
// In browser console (F12)
localStorage.clear();
location.reload();
```

2. **Check JWT secret matches** in `application.properties`

3. **Re-login** to get new token

---

### **Issue 7: Docker Container Creation Failed**

**Symptom:**
```
Error response from daemon: No such image
```

**Solution:**

1. **Rebuild Docker images:**
```bash
cd docker/java-executor
docker build -t brewalgo-java-executor:latest .

cd ../python-executor
docker build -t brewalgo-python-executor:latest .
```

2. **Verify images exist:**
```bash
docker images | grep brewalgo
```

---

### **Issue 8: Submission Stuck on "Submitting..."**

**Symptom:**
- Frontend shows "Submitting..." indefinitely
- No response

**Solution:**

1. **Check backend logs** for errors
2. **Verify Docker is running:**
```bash
docker ps
```
3. **Check test cases exist:**
```sql
psql -U postgres -d brewalgo
SELECT * FROM test_cases WHERE problem_id = 1;
\q
```

---

## 🔧 **Environment Configuration**

### **Development (Local)**

**Backend:** `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/brewalgo
server.port=8081
logging.level.com.brewalgo=DEBUG
```

**Frontend:** `.env.development` (create if doesn't exist)
```env
VITE_API_URL=http://localhost:8081/api
```

---

### **Production (Optional)**

**Backend:** `application-prod.properties`
```properties
spring.datasource.url=jdbc:postgresql://<RDS_ENDPOINT>:5432/brewalgo
server.port=8080
logging.level.com.brewalgo=INFO
```

**Frontend:** `.env.production`
```env
VITE_API_URL=https://api.brewalgo.com
```

---

## 📚 **Quick Reference**

### **Start All Services**
```bash
# Terminal 1: PostgreSQL (if not auto-start)
sudo service postgresql start

# Terminal 2: Docker Desktop
# (Start via GUI)

# Terminal 3: Backend
cd ~/BrewAlgo/backend
mvn spring-boot:run

# Terminal 4: Frontend
cd ~/BrewAlgo/frontend
npm run dev
```

### **Stop All Services**
```bash
# Stop backend: Ctrl+C in Terminal 3
# Stop frontend: Ctrl+C in Terminal 4
# Stop Docker Desktop: Close application
```

### **Default URLs**

| Service | URL |
|---------|-----|
| Frontend | http://localhost:5173 |
| Backend API | http://localhost:8081 |
| API Docs | http://localhost:8081/api |
| PostgreSQL | localhost:5432 |

### **Default Credentials (Seed Data)**

| Username | Password | Role |
|----------|----------|------|
| alice | password123 | USER |
| bob | password123 | USER |
| charlie | password123 | USER |

---

## 🎯 **Next Steps**

After successful setup:

1. ✅ Read [ARCHITECTURE.md](ARCHITECTURE.md) to understand the system
2. ✅ Review [API.md](API.md) for endpoint documentation
3. ✅ Check [PROJECT_STATUS.md](PROJECT_STATUS.md) for current state
4. ✅ Try submitting different solutions
5. ✅ Explore the leaderboard
6. ✅ Check user profile

---

## 📞 **Getting Help**

If you encounter issues not covered here:

1. Check [TROUBLESHOOTING.md](TROUBLESHOOTING.md) (if exists)
2. Review backend logs: `backend/logs/` or console output
3. Check browser console (F12) for frontend errors
4. Search GitHub issues: https://github.com/AshharAhmadKhan/BrewAlgo/issues

---

**Setup Guide Version:** 1.0.0  
**Last Updated:** January 15, 2026  
**Author:** Ashhar Ahmad Khan

*This guide covers local development setup. For deployment instructions, see DEPLOYMENT.md (if available).*