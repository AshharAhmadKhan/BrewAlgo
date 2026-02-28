<div align="center">

# BrewAlgo

### Enterprise-Grade Online Coding Judge Platform

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue.svg)](https://reactjs.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-336791.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**A production-ready competitive programming platform with secure Docker-isolated code execution, comprehensive test suites, and enterprise-grade architecture.**

[Features](#features) • [Architecture](#architecture) • [Quick Start](#quick-start) • [Documentation](#documentation) • [Contributing](CONTRIBUTING.md) • [Security](SECURITY.md)

</div>

---

## Overview

BrewAlgo is a full-stack online coding judge platform designed to provide a secure, scalable environment for competitive programming. Built with Clean Architecture principles, it demonstrates enterprise-level software engineering practices including Docker containerization, JWT authentication, comprehensive testing, and production-ready deployment strategies.

### Key Highlights

- **Secure Code Execution**: Docker-isolated containers with resource limits (CPU, memory, timeout)
- **Multi-Language Support**: Java and Python with extensible architecture for additional languages
- **Clean Architecture**: 4-layer architecture (Domain, Application, Infrastructure, Presentation)
- **Comprehensive Testing**: 90+ unit and integration tests with 85%+ code coverage
- **Production-Ready**: Rate limiting, audit logging, caching, async processing, OpenAPI documentation
- **100 Curated Problems**: Elite DSA problem set covering arrays, strings, trees, graphs, DP, and more

---

## Features

### Core Functionality
- ✅ **User Authentication & Authorization** - JWT-based secure authentication with role-based access control
- ✅ **Problem Management** - CRUD operations for coding problems with difficulty levels and tags
- ✅ **Code Submission & Evaluation** - Real-time code execution with detailed feedback
- ✅ **Multi-Language Support** - Java and Python with extensible executor framework
- ✅ **Test Case Validation** - Automated testing against multiple test cases with instant feedback
- ✅ **Leaderboard System** - Real-time rankings based on solved problems and accuracy
- ✅ **Contest Management** - Time-bound contests with participant tracking

### Security & Performance
- 🔒 **Docker Isolation** - All code runs in isolated containers, never on host
- 🔒 **Resource Limits** - CPU (50%), Memory (256MB), Timeout (5s) per execution
- 🔒 **Input Sanitization** - XSS prevention, SQL injection protection
- 🔒 **Rate Limiting** - API throttling to prevent abuse (100 req/min per user)
- ⚡ **Caching** - Redis-based caching for frequently accessed data
- ⚡ **Async Processing** - Non-blocking code execution with thread pools

### Developer Experience
- 📚 **OpenAPI Documentation** - Interactive API docs at `/swagger-ui.html`
- 📊 **Audit Logging** - Comprehensive activity tracking for compliance
- 🔍 **Request Tracing** - Unique request IDs for debugging
- 🧪 **Comprehensive Tests** - 90+ tests covering unit, integration, and repository layers

---

## Architecture

BrewAlgo follows **Clean Architecture** principles with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                     Presentation Layer                       │
│              (Controllers, DTOs, Exception Handlers)         │
└────────────────────────┬────────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────────┐
│                    Application Layer                         │
│           (Services, Business Logic, Use Cases)              │
└────────────────────────┬────────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────────┐
│                      Domain Layer                            │
│        (Entities, Repositories, Domain Exceptions)           │
└────────────────────────┬────────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────────┐
│                  Infrastructure Layer                        │
│    (Security, Persistence, External Services, Config)        │
└──────────────────────────────────────────────────────────────┘
```

### Code Execution Flow

```
User Submits Code
      │
      ▼
JWT Authentication
      │
      ▼
Rate Limit Check
      │
      ▼
Submission Service
      │
      ▼
Code Execution Service
      │
      ├─► Write code to temp file
      ├─► Create Docker container (resource-limited)
      ├─► Execute against test cases
      ├─► Capture stdout/stderr
      ├─► Compare with expected output
      └─► Return verdict (ACCEPTED/WRONG_ANSWER/TLE/etc.)
      │
      ▼
Store Submission in PostgreSQL
      │
      ▼
Return Result to User
```

For detailed architecture documentation, see [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

---

## Tech Stack

### Backend
- **Framework**: Spring Boot 3.2.1
- **Language**: Java 17
- **Security**: Spring Security with JWT
- **Database**: PostgreSQL 15
- **ORM**: Spring Data JPA
- **Containerization**: Docker Java SDK
- **Caching**: Spring Cache (Caffeine)
- **API Docs**: SpringDoc OpenAPI 3
- **Testing**: JUnit 5, Mockito, TestContainers

### Frontend
- **Framework**: React 18
- **Build Tool**: Vite
- **Styling**: Tailwind CSS
- **HTTP Client**: Axios
- **Code Editor**: Monaco Editor (VS Code editor)
- **Markdown**: ReactMarkdown with syntax highlighting

### DevOps
- **Containerization**: Docker & Docker Compose
- **Execution Isolation**: Custom Docker images per language
- **Database**: PostgreSQL with connection pooling
- **Logging**: SLF4J with Logback

---

## Quick Start

### Prerequisites

- Java 17+
- Node.js 18+
- PostgreSQL 15+
- Docker Desktop
- Maven 3.8+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/AshharAhmadKhan/BrewAlgo.git
   cd BrewAlgo
   ```

2. **Database Setup**
   ```bash
   psql -U postgres
   CREATE DATABASE brewalgo;
   \q
   ```

3. **Backend Setup**
   ```bash
   cd backend
   
   # Update database credentials in src/main/resources/application.properties
   # spring.datasource.username=postgres
   # spring.datasource.password=your_password
   
   # Run the application
   mvn spring-boot:run
   ```
   Backend runs on `http://localhost:8081`

4. **Build Docker Executor Images**
   ```bash
   # Java Executor
   cd docker/java-executor
   docker build -t brewalgo-java-executor:latest .
   
   # Python Executor
   cd ../python-executor
   docker build -t brewalgo-python-executor:latest .
   ```

5. **Frontend Setup**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
   Frontend runs on `http://localhost:5173`

6. **Access the Application**
   - Frontend: http://localhost:5173
   - Backend API: http://localhost:8081
   - API Documentation: http://localhost:8081/swagger-ui.html

### Running Tests

```bash
cd backend
mvn test
```

---

## Documentation

- [Architecture Documentation](docs/ARCHITECTURE.md) - Detailed system design and component diagrams
- [Contributing Guidelines](CONTRIBUTING.md) - How to contribute to the project
- [Security Policy](SECURITY.md) - Security best practices and vulnerability reporting
- [Changelog](CHANGELOG.md) - Version history and release notes

---

## Project Structure

```
BrewAlgo/
├── backend/
│   ├── src/main/java/com/brewalgo/
│   │   ├── domain/                 # Entities, repositories, domain logic
│   │   ├── application/            # Services, DTOs, business logic
│   │   ├── infrastructure/         # Security, persistence, external services
│   │   └── presentation/           # Controllers, exception handlers
│   ├── src/main/resources/
│   │   ├── application.properties  # Configuration
│   │   └── messages.properties     # I18n messages
│   └── src/test/                   # Unit & integration tests
│
├── frontend/
│   ├── src/
│   │   ├── components/             # Reusable React components
│   │   ├── pages/                  # Page components
│   │   ├── services/               # API service layer
│   │   └── utils/                  # Utility functions
│   └── public/                     # Static assets
│
├── docker/
│   ├── java-executor/              # Java execution environment
│   └── python-executor/            # Python execution environment
│
├── docs/                           # Documentation
└── docker-compose.yml              # Docker orchestration
```

---

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and receive JWT token

### Problems
- `GET /api/problems` - List all problems
- `GET /api/problems/{id}` - Get problem by ID
- `GET /api/problems/slug/{slug}` - Get problem by slug
- `POST /api/problems` - Create new problem (Admin)
- `PUT /api/problems/{id}` - Update problem (Admin)
- `DELETE /api/problems/{id}` - Delete problem (Admin)

### Submissions
- `POST /api/submissions` - Submit code for evaluation
- `GET /api/submissions/user/{userId}` - Get user's submissions
- `GET /api/submissions/{id}` - Get submission details

### Leaderboard
- `GET /api/leaderboard` - Get global leaderboard
- `GET /api/leaderboard/contest/{contestId}` - Get contest leaderboard

For complete API documentation, visit `/swagger-ui.html` when running the backend.

---

## Security

BrewAlgo implements multiple layers of security:

- **Authentication**: JWT-based stateless authentication
- **Authorization**: Role-based access control (USER, ADMIN)
- **Password Security**: BCrypt hashing with salt
- **Docker Isolation**: Code execution in isolated containers
- **Resource Limits**: CPU, memory, and timeout constraints
- **Input Validation**: Comprehensive input sanitization
- **SQL Injection Prevention**: Parameterized queries
- **XSS Protection**: Output encoding and CSP headers
- **Rate Limiting**: API throttling per user
- **Audit Logging**: Complete activity tracking

For detailed security information, see [SECURITY.md](SECURITY.md).

---

## Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details on:

- Code of Conduct
- Development workflow
- Coding standards
- Commit message guidelines
- Pull request process

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Author

**Ashhar Ahmad Khan**

- Email: itzashhar@gmail.com
- GitHub: [@AshharAhmadKhan](https://github.com/AshharAhmadKhan)
- LinkedIn: [Ashhar Ahmad Khan](https://linkedin.com/in/ashhar-ahmad-khan)

---

## Acknowledgments

Built as a learning project to understand:
- Docker containerization and resource isolation
- Clean Architecture principles in practice
- Secure code execution systems
- Enterprise-level Spring Boot applications
- Full-stack development with React and Spring Boot

---

<div align="center">

**If this project helped you learn something new, please consider giving it a ⭐️**

[Report Bug](https://github.com/AshharAhmadKhan/BrewAlgo/issues) • [Request Feature](https://github.com/AshharAhmadKhan/BrewAlgo/issues)

</div>
