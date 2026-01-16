# 🚀 BrewAlgo

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue.svg)](https://reactjs.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> **A production-grade algorithmic problem-solving platform with Docker-isolated code execution**

Master competitive programming with secure, real-time code evaluation. Built with Clean Architecture, featuring multi-language support and comprehensive test case validation.

---

## 🎯 **Key Features**

### **Core Functionality**
- ✅ **Secure Code Execution** - Docker-isolated containers with CPU/memory limits
- ✅ **Multi-Language Support** - Java & Python (extensible architecture)
- ✅ **Real-Time Evaluation** - Instant feedback with detailed error messages
- ✅ **Comprehensive Testing** - Multiple test cases per problem with hidden tests
- ✅ **Smart Status Detection** - ACCEPTED, WRONG_ANSWER, COMPILATION_ERROR, RUNTIME_ERROR, TIME_LIMIT_EXCEEDED

### **User Experience**
- 🎨 Modern UI with Tailwind CSS
- 🔐 JWT-based authentication
- 📊 Global leaderboard with ratings
- 📈 Personal statistics tracking
- 🏆 Contest system (framework ready)

### **Technical Excellence**
- 🏗️ **Clean Architecture** - 4-layer separation (Presentation → Application → Domain → Infrastructure)
- 🔒 **Security First** - Resource limits, isolated execution, SQL injection prevention
- ⚡ **Performance** - Optimized database queries, async processing
- 📝 **API-Driven** - RESTful design with 15+ endpoints

---

## 🏗️ **System Architecture**
```
┌─────────────────────────────────────────────────────────────────┐
│                         FRONTEND (React)                         │
│  - Problem browsing UI    - Code editor    - Leaderboard        │
└────────────────────────────┬────────────────────────────────────┘
                             │ REST API (JWT)
┌────────────────────────────▼────────────────────────────────────┐
│                   BACKEND (Spring Boot 3.2)                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  Presentation Layer  - Controllers, DTOs                 │   │
│  ├──────────────────────────────────────────────────────────┤   │
│  │  Application Layer   - Business Logic, Services          │   │
│  ├──────────────────────────────────────────────────────────┤   │
│  │  Domain Layer        - Entities, Value Objects           │   │
│  ├──────────────────────────────────────────────────────────┤   │
│  │  Infrastructure      - JPA, Security, Docker SDK         │   │
│  └──────────────────────────────────────────────────────────┘   │
└────────────┬─────────────────────────────┬──────────────────────┘
             │                             │
             │ JDBC                        │ Docker Java SDK
             ▼                             ▼
┌─────────────────────┐      ┌──────────────────────────────────┐
│   PostgreSQL DB     │      │      Docker Engine               │
│  - Users            │      │  ┌────────────────────────────┐  │
│  - Problems         │      │  │ Isolated Container         │  │
│  - Submissions      │      │  │ - JDK 17 / Python 3.11    │  │
│  - Test Cases       │      │  │ - Resource Limits          │  │
└─────────────────────┘      │  │ - 5s Timeout              │  │
                             │  └────────────────────────────┘  │
                             └──────────────────────────────────┘
```

---

## 🛠️ **Tech Stack**

| Layer | Technology | Version |
|-------|-----------|---------|
| **Backend** | Spring Boot | 3.2.1 |
| | Java | 17 |
| | Spring Security | 6.x |
| | Spring Data JPA | - |
| | PostgreSQL | 15+ |
| | Docker Java SDK | 3.4.0 |
| | JWT (jjwt) | 0.12.3 |
| **Frontend** | React | 18 |
| | Vite | Latest |
| | Tailwind CSS | 3.x |
| | React Router | 6.x |
| | Axios | Latest |
| **DevOps** | Docker | 20+ |
| | Maven | 3.9+ |
| | Git | - |

---

## 🚀 **Quick Start**

### **Prerequisites**
```bash
# Required
- Java 17 or higher
- Node.js 18+
- PostgreSQL 15+
- Docker Desktop
- Maven 3.9+
```

### **1. Clone Repository**
```bash
git clone https://github.com/AshharAhmadKhan/BrewAlgo.git
cd BrewAlgo
```

### **2. Database Setup**
```sql
-- Connect to PostgreSQL
psql -U postgres

-- Create database
CREATE DATABASE brewalgo;

-- Exit psql
\q
```

### **3. Backend Setup**
```bash
cd backend

# Update application.properties with your DB credentials
# src/main/resources/application.properties

# Run backend
mvn spring-boot:run
```

**Backend runs at:** `http://localhost:8081`

### **4. Build Docker Images**
```bash
# Java executor
cd docker/java-executor
docker build -t brewalgo-java-executor:latest .

# Python executor
cd ../python-executor
docker build -t brewalgo-python-executor:latest .
```

### **5. Frontend Setup**
```bash
cd ../../frontend

# Install dependencies
npm install

# Start dev server
npm run dev
```

**Frontend runs at:** `http://localhost:5173`

### **6. Seed Data (Optional)**
```bash
# In backend directory
psql -U postgres -d brewalgo -f src/main/resources/seed.sql
psql -U postgres -d brewalgo -f src/main/resources/insert_test_cases.sql
```

---

## 📁 **Project Structure**
```
BrewAlgo/
├── backend/                    # Spring Boot application
│   ├── src/main/java/com/brewalgo/
│   │   ├── domain/            # Entities, repositories
│   │   │   ├── entity/
│   │   │   └── repository/
│   │   ├── application/       # Services, DTOs
│   │   │   ├── dto/
│   │   │   └── service/
│   │   │       └── CodeExecutionService.java  ← Core execution logic
│   │   ├── infrastructure/    # Security, persistence
│   │   │   ├── security/
│   │   │   └── persistence/
│   │   └── presentation/      # Controllers
│   │       └── controller/
│   └── pom.xml
│
├── frontend/                   # React application
│   ├── src/
│   │   ├── components/        # Reusable UI components
│   │   ├── pages/
│   │   │   ├── ProblemDetail.jsx  ← Submission page
│   │   │   ├── ProblemList.jsx
│   │   │   └── Leaderboard.jsx
│   │   ├── services/          # API integration
│   │   ├── context/           # Auth context
│   │   └── utils/
│   └── package.json
│
├── docker/                     # Execution environments
│   ├── java-executor/
│   │   └── Dockerfile
│   └── python-executor/
│       └── Dockerfile
│
├── docs/                       # Documentation
│   ├── ARCHITECTURE.md
│   ├── API.md
│   ├── SETUP.md
│   └── PROJECT_STATUS.md
│
└── README.md                   # You are here
```

---

## 🔐 **Security Features**

| Feature | Implementation |
|---------|---------------|
| **Authentication** | JWT with HMAC-SHA256 |
| **Password Storage** | BCrypt hashing (strength 10) |
| **Code Isolation** | Docker containers |
| **Resource Limits** | CPU: 50%, Memory: 256MB, Timeout: 5s |
| **SQL Injection** | JPA parameterized queries |
| **CORS** | Configured for localhost:5173 |

---

## 📊 **API Endpoints**

### **Authentication**
```http
POST /api/auth/register    # User registration
POST /api/auth/login       # User login
```

### **Problems**
```http
GET  /api/problems              # List all problems
GET  /api/problems/slug/{slug}  # Get problem by slug
GET  /api/problems/{id}         # Get problem by ID
```

### **Submissions**
```http
POST /api/submissions                      # Submit solution
GET  /api/submissions/user/{userId}        # User's submissions
GET  /api/submissions/problem/{problemId}  # Problem submissions
```

### **Leaderboard**
```http
GET /api/leaderboard        # Global rankings
GET /api/users/profile      # User profile & stats
```

**Full API documentation:** See [docs/API.md](docs/API.md)

---

## 🎓 **Key Technical Achievements**

### **1. Docker Dependency Resolution**
**Challenge:** `NoClassDefFoundError: HttpVersionPolicy`  
**Cause:** Spring Boot 3.2.1 manages `httpcore5:5.2.4`, but `docker-java:3.3.4` requires `5.3+`  
**Solution:** Upgraded to `docker-java:3.4.0` and forced `httpcore5:5.3.1` in Maven properties

### **2. Async Output Capture**
**Challenge:** Docker logs returning object reference instead of stdout  
**Implementation:** Custom `ResultCallback.Adapter<Frame>` with `StringBuilder` accumulation

### **3. Secure Input Handling**
**Challenge:** Shell injection risk with `echo` piping  
**Solution:** Write input to `input.txt`, redirect via `< input.txt`

### **4. Status Classification**
**Implementation:** Separate detection for:
- Compilation errors (check stderr for "error:")
- Runtime errors (non-zero exit code)
- Time limit exceeded (timeout)
- Wrong answer (output mismatch)

---

## 📈 **Project Statistics**

- **Total Lines of Code:** ~10,000+
- **Backend:** 40+ Java files
- **Frontend:** 25+ React components
- **API Endpoints:** 15+
- **Database Tables:** 6 entities
- **Test Problems:** 1 (Two Sum, expandable)
- **Supported Languages:** 2 (Java, Python)

---

## 🗺️ **Roadmap**

### **Phase 1: MVP** ✅ *Completed*
- [x] Clean Architecture backend
- [x] React frontend
- [x] JWT authentication
- [x] Problem CRUD
- [x] Submission system

### **Phase 2: Code Execution** ✅ *Completed*
- [x] Docker isolation
- [x] Multi-language support
- [x] Test case evaluation
- [x] Resource limits
- [x] Status detection

### **Phase 3: Enhancement** 🚧 *In Progress*
- [ ] Input format documentation
- [ ] Code templates for users
- [ ] 10+ more problems
- [ ] Contest UI implementation
- [ ] Performance metrics

### **Phase 4: Scale** 📅 *Planned*
- [ ] Container pooling
- [ ] Redis caching
- [ ] WebSocket real-time updates
- [ ] Admin dashboard
- [ ] Analytics & monitoring

---

## 🐛 **Known Issues**

| Issue | Status | Workaround |
|-------|--------|-----------|
| Execution time includes Docker overhead (~3-4s) | Known | Labeled as "Total Runtime" |
| No input format guidance for users | Planned | Add to problem page (Phase 3) |
| Class name must be "Solution" | Limitation | Document requirement |
| One container per test case | Optimization needed | Works but inefficient |

See [docs/PROJECT_STATUS.md](docs/PROJECT_STATUS.md) for detailed status.

---

## 🤝 **Contributing**

Contributions welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 **License**

This project is licensed under the MIT License - see [LICENSE](LICENSE) for details.

---

## 👨‍💻 **Developer**

**Ashhar Ahmad Khan**  
*BTech Student | Full-Stack Developer | System Architect*

- 📧 Email: itzashhar@gmail.com
- 💼 GitHub: [@AshharAhmadKhan](https://github.com/AshharAhmadKhan)
- 🔗 LinkedIn: [Connect with me](#)

---

## 🙏 **Acknowledgments**

- Spring Boot team for the robust framework
- React community for excellent documentation
- Docker for containerization technology
- All open-source contributors

---

## ⭐ **Show Your Support**

Give a ⭐️ if this project helped you!

---

<div align="center">

**Built with 💙 by Ashhar Ahmad Khan**

*Making competitive programming accessible and secure*

</div>