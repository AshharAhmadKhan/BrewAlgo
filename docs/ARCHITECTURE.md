# BrewAlgo - System Architecture

## Table of Contents

- [Overview](#overview)
- [High-Level Architecture](#high-level-architecture)
- [Clean Architecture Layers](#clean-architecture-layers)
- [Component Diagram](#component-diagram)
- [Data Flow](#data-flow)
- [Security Architecture](#security-architecture)
- [Deployment Architecture](#deployment-architecture)
- [Technology Stack](#technology-stack)

---

## Overview

BrewAlgo is built using **Clean Architecture** principles, ensuring separation of concerns, testability, and maintainability. The system consists of three main components:

1. **Frontend (React)** - User interface and client-side logic
2. **Backend (Spring Boot)** - Business logic and API
3. **Docker Engine** - Isolated code execution environment

---

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                               │
│                              CLIENT LAYER                                     │
│                                                                               │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │                     React Frontend (Port 5173)                       │    │
│  │                                                                       │    │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐              │    │
│  │  │   Problem    │  │     Code     │  │  Leaderboard │              │    │
│  │  │   Browser    │  │    Editor    │  │    View      │              │    │
│  │  └──────────────┘  └──────────────┘  └──────────────┘              │    │
│  │                                                                       │    │
│  │  ┌──────────────────────────────────────────────────────────────┐   │    │
│  │  │              State Management (Context API)                   │   │    │
│  │  └──────────────────────────────────────────────────────────────┘   │    │
│  │                                                                       │    │
│  │  ┌──────────────────────────────────────────────────────────────┐   │    │
│  │  │           API Client (Axios + JWT Interceptor)                │   │    │
│  │  └──────────────────────────────────────────────────────────────┘   │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
│                                                                               │
└───────────────────────────────────┬───────────────────────────────────────────┘
                                    │
                                    │ HTTPS/REST API
                                    │ JWT Authentication
                                    │
┌───────────────────────────────────▼───────────────────────────────────────────┐
│                                                                               │
│                            APPLICATION LAYER                                  │
│                                                                               │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │              Spring Boot Backend (Port 8081)                         │    │
│  │                                                                       │    │
│  │  ┌─────────────────────────────────────────────────────────────┐    │    │
│  │  │                  PRESENTATION LAYER                          │    │    │
│  │  │                                                               │    │    │
│  │  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │    │    │
│  │  │  │   Problem    │  │  Submission  │  │     User     │      │    │    │
│  │  │  │  Controller  │  │  Controller  │  │  Controller  │      │    │    │
│  │  │  └──────────────┘  └──────────────┘  └──────────────┘      │    │    │
│  │  │                                                               │    │    │
│  │  │  ┌────────────────────────────────────────────────────┐     │    │    │
│  │  │  │  Global Exception Handler | Request/Response Log   │     │    │    │
│  │  │  └────────────────────────────────────────────────────┘     │    │    │
│  │  └─────────────────────────────────────────────────────────────┘    │    │
│  │                              │                                        │    │
│  │  ┌───────────────────────────▼───────────────────────────────────┐  │    │
│  │  │                  APPLICATION LAYER                             │  │    │
│  │  │                                                                 │  │    │
│  │  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │  │    │
│  │  │  │   Problem    │  │  Submission  │  │     User     │        │  │    │
│  │  │  │   Service    │  │   Service    │  │   Service    │        │  │    │
│  │  │  └──────────────┘  └──────────────┘  └──────────────┘        │  │    │
│  │  │                                                                 │  │    │
│  │  │  ┌────────────────────────────────────────────────────┐       │  │    │
│  │  │  │         Code Execution Service (Core Logic)         │       │  │    │
│  │  │  │  - Docker container management                      │       │  │    │
│  │  │  │  - Test case validation                             │       │  │    │
│  │  │  │  - Multi-language support                           │       │  │    │
│  │  │  └────────────────────────────────────────────────────┘       │  │    │
│  │  │                                                                 │  │    │
│  │  │  ┌────────────────────────────────────────────────────┐       │  │    │
│  │  │  │              DTOs (Data Transfer Objects)           │       │  │    │
│  │  │  └────────────────────────────────────────────────────┘       │  │    │
│  │  └─────────────────────────────────────────────────────────────┘  │    │
│  │                              │                                        │    │
│  │  ┌───────────────────────────▼───────────────────────────────────┐  │    │
│  │  │                     DOMAIN LAYER                               │  │    │
│  │  │                                                                 │  │    │
│  │  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │  │    │
│  │  │  │     User     │  │   Problem    │  │  Submission  │        │  │    │
│  │  │  │    Entity    │  │    Entity    │  │    Entity    │        │  │    │
│  │  │  └──────────────┘  └──────────────┘  └──────────────┘        │  │    │
│  │  │                                                                 │  │    │
│  │  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │  │    │
│  │  │  │     User     │  │   Problem    │  │  Submission  │        │  │    │
│  │  │  │  Repository  │  │  Repository  │  │  Repository  │        │  │    │
│  │  │  └──────────────┘  └──────────────┘  └──────────────┘        │  │    │
│  │  │                                                                 │  │    │
│  │  │  ┌────────────────────────────────────────────────────┐       │  │    │
│  │  │  │          Business Exceptions & Rules                │       │  │    │
│  │  │  └────────────────────────────────────────────────────┘       │  │    │
│  │  └─────────────────────────────────────────────────────────────┘  │    │
│  │                              │                                        │    │
│  │  ┌───────────────────────────▼───────────────────────────────────┐  │    │
│  │  │                 INFRASTRUCTURE LAYER                           │  │    │
│  │  │                                                                 │  │    │
│  │  │  ┌────────────────────────────────────────────────────┐       │  │    │
│  │  │  │              Security Configuration                 │       │  │    │
│  │  │  │  - JWT Authentication Filter                        │       │  │    │
│  │  │  │  - Security Filter Chain                            │       │  │    │
│  │  │  │  - Password Encoder (BCrypt)                        │       │  │    │
│  │  │  └────────────────────────────────────────────────────┘       │  │    │
│  │  │                                                                 │  │    │
│  │  │  ┌────────────────────────────────────────────────────┐       │  │    │
│  │  │  │           Persistence Implementation                │       │  │    │
│  │  │  │  - JPA Repository Implementations                   │       │  │    │
│  │  │  │  - Database Connection (HikariCP)                   │       │  │    │
│  │  │  └────────────────────────────────────────────────────┘       │  │    │
│  │  │                                                                 │  │    │
│  │  │  ┌────────────────────────────────────────────────────┐       │  │    │
│  │  │  │            Configuration & Middleware               │       │  │    │
│  │  │  │  - CORS Configuration                               │       │  │    │
│  │  │  │  - Rate Limiting                                    │       │  │    │
│  │  │  │  - Logging Interceptor                              │       │  │    │
│  │  │  │  - Audit Service                                    │       │  │    │
│  │  │  └────────────────────────────────────────────────────┘       │  │    │
│  │  └─────────────────────────────────────────────────────────────┘  │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
│                                                                               │
└───────────────┬───────────────────────────────────┬───────────────────────────┘
                │                                   │
                │ JDBC                              │ Docker Java SDK
                │                                   │
┌───────────────▼───────────────┐   ┌───────────────▼───────────────────────────┐
│                               │   │                                           │
│      DATA LAYER               │   │        EXECUTION LAYER                    │
│                               │   │                                           │
│  ┌─────────────────────────┐  │   │  ┌─────────────────────────────────────┐ │
│  │   PostgreSQL Database   │  │   │  │         Docker Engine               │ │
│  │                         │  │   │  │                                     │ │
│  │  ┌──────────────────┐   │  │   │  │  ┌───────────────────────────────┐ │ │
│  │  │  users           │   │  │   │  │  │  Java Executor Container      │ │ │
│  │  │  problems        │   │  │   │  │  │  - OpenJDK 17                 │ │ │
│  │  │  submissions     │   │  │   │  │  │  - Resource Limits            │ │ │
│  │  │  test_cases      │   │  │   │  │  │  - 5s Timeout                 │ │ │
│  │  │  contests        │   │  │   │  │  │  - Isolated Filesystem        │ │ │
│  │  │  audit_logs      │   │  │   │  │  └───────────────────────────────┘ │ │
│  │  └──────────────────┘   │  │   │  │                                     │ │
│  │                         │  │   │  │  ┌───────────────────────────────┐ │ │
│  │  ┌──────────────────┐   │  │   │  │  │  Python Executor Container    │ │ │
│  │  │  Indexes         │   │  │   │  │  │  - Python 3.11                │ │ │
│  │  │  Constraints     │   │  │   │  │  │  - Resource Limits            │ │ │
│  │  │  Foreign Keys    │   │  │   │  │  │  - 5s Timeout                 │ │ │
│  │  └──────────────────┘   │  │   │  │  │  - Isolated Filesystem        │ │ │
│  └─────────────────────────┘  │   │  │  └───────────────────────────────┘ │ │
│                               │   │  │                                     │ │
│  Connection Pool: HikariCP    │   │  │  Container Lifecycle:               │ │
│  Max Connections: 10          │   │  │  1. Create → 2. Start →            │ │
│  Min Idle: 5                  │   │  │  3. Execute → 4. Stop →            │ │
│                               │   │  │  5. Remove                          │ │
└───────────────────────────────┘   └─────────────────────────────────────────┘
```

---

## Clean Architecture Layers

### 1. Presentation Layer (Controllers)

**Responsibility:** Handle HTTP requests/responses, input validation, authentication

**Components:**
- `ProblemController` - Problem CRUD operations
- `SubmissionController` - Code submission handling
- `UserController` - User management
- `LeaderboardController` - Rankings and statistics

**Key Features:**
- RESTful API design
- JWT authentication
- Request/Response DTOs
- Global exception handling
- Request logging

### 2. Application Layer (Services)

**Responsibility:** Business logic, orchestration, use cases

**Components:**
- `ProblemService` - Problem management logic
- `SubmissionService` - Submission processing
- `UserService` - User operations
- `CodeExecutionService` - Core execution logic
- `ContestService` - Contest management

**Key Features:**
- Transaction management
- Business rule enforcement
- DTO mapping
- Service orchestration

### 3. Domain Layer (Entities & Repositories)

**Responsibility:** Core business entities, domain logic, data contracts

**Components:**
- **Entities:** User, Problem, Submission, TestCase, Contest, AuditLog
- **Repositories:** Data access interfaces
- **Exceptions:** Domain-specific exceptions
- **Value Objects:** Immutable domain objects

**Key Features:**
- JPA entities
- Repository interfaces
- Domain exceptions
- Business rules

### 4. Infrastructure Layer (Implementation Details)

**Responsibility:** External concerns, frameworks, tools

**Components:**
- **Security:** JWT, BCrypt, Spring Security
- **Persistence:** JPA implementations, HikariCP
- **Configuration:** CORS, Rate Limiting, Logging
- **External Services:** Docker SDK integration

**Key Features:**
- Framework configurations
- External API integrations
- Security implementations
- Database connections

---

## Component Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        Frontend Components                       │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐     │
│  │   Navbar     │    │  Auth Pages  │    │   Problem    │     │
│  │  Component   │    │  - Login     │    │   List Page  │     │
│  └──────────────┘    │  - Register  │    └──────────────┘     │
│                      └──────────────┘                           │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐     │
│  │   Problem    │    │  Leaderboard │    │    User      │     │
│  │ Detail Page  │    │     Page     │    │   Profile    │     │
│  └──────────────┘    └──────────────┘    └──────────────┘     │
│                                                                  │
│  ┌────────────────────────────────────────────────────────┐    │
│  │              Shared Components                          │    │
│  │  - CodeEditor  - Toast  - LoadingSkeleton              │    │
│  │  - ErrorBoundary  - ProtectedRoute                     │    │
│  └────────────────────────────────────────────────────────┘    │
│                                                                  │
│  ┌────────────────────────────────────────────────────────┐    │
│  │                  Services Layer                         │    │
│  │  - authService  - problemService  - apiClient          │    │
│  └────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘
```

---

## Data Flow

### 1. User Authentication Flow

```
User → Login Form → authService.login()
                         ↓
                    POST /api/v1/users/login
                         ↓
                    UserController
                         ↓
                    UserService.authenticate()
                         ↓
                    UserRepository.findByUsername()
                         ↓
                    BCrypt.checkPassword()
                         ↓
                    JwtUtil.generateToken()
                         ↓
                    Return JWT + User Data
                         ↓
                    Store in localStorage
                         ↓
                    Redirect to Problems Page
```

### 2. Code Submission Flow

```
User → Code Editor → Submit Button
                         ↓
                    problemService.submitSolution()
                         ↓
                    POST /api/v1/submissions
                    (JWT in Authorization header)
                         ↓
                    JwtAuthenticationFilter
                    (Validate token, extract user)
                         ↓
                    SubmissionController.submitSolution()
                         ↓
                    SubmissionService.submitSolution()
                    (Create submission record)
                         ↓
                    CodeExecutionService.executeCode()
                         ↓
                    ┌─────────────────────────────────┐
                    │  For each test case:            │
                    │  1. Create temp directory       │
                    │  2. Write code file             │
                    │  3. Write input file            │
                    │  4. Create Docker container     │
                    │  5. Start container             │
                    │  6. Wait for completion         │
                    │  7. Capture output              │
                    │  8. Compare with expected       │
                    │  9. Clean up container          │
                    └─────────────────────────────────┘
                         ↓
                    Aggregate results
                         ↓
                    Update submission status
                         ↓
                    Return ExecutionResult
                         ↓
                    Display result to user
                    (Confetti if ACCEPTED)
```

### 3. Problem Browsing Flow

```
User → Problems Page → problemService.getAllProblems()
                            ↓
                       GET /api/v1/problems
                            ↓
                       ProblemController.getAllProblems()
                            ↓
                       ProblemService.getAllProblems()
                            ↓
                       ProblemRepository.findAll()
                            ↓
                       Map to ProblemDTO
                            ↓
                       Return paginated list
                            ↓
                       Render problem cards
```

---

## Security Architecture

### Authentication Flow

```
┌──────────────┐
│    Client    │
└──────┬───────┘
       │ 1. POST /login (username, password)
       ▼
┌──────────────────────┐
│  UserController      │
└──────┬───────────────┘
       │ 2. Authenticate
       ▼
┌──────────────────────┐
│  UserService         │
│  - Load user         │
│  - Verify password   │
└──────┬───────────────┘
       │ 3. Generate JWT
       ▼
┌──────────────────────┐
│  JwtUtil             │
│  - Create token      │
│  - Sign with secret  │
└──────┬───────────────┘
       │ 4. Return token
       ▼
┌──────────────┐
│    Client    │
│  Store token │
└──────────────┘
```

### Request Authorization Flow

```
┌──────────────┐
│    Client    │
└──────┬───────┘
       │ 1. Request with JWT in header
       ▼
┌──────────────────────────┐
│ JwtAuthenticationFilter  │
│  - Extract token         │
│  - Validate signature    │
│  - Check expiration      │
└──────┬───────────────────┘
       │ 2. Load user details
       ▼
┌──────────────────────────┐
│ UserDetailsService       │
│  - Load from database    │
└──────┬───────────────────┘
       │ 3. Set authentication
       ▼
┌──────────────────────────┐
│ SecurityContext          │
│  - Store authentication  │
└──────┬───────────────────┘
       │ 4. Proceed to controller
       ▼
┌──────────────────────────┐
│  Controller              │
│  - Access authenticated  │
│    user from context     │
└──────────────────────────┘
```

### Code Execution Security

```
┌──────────────────────────────────────────────────────────┐
│                   Security Layers                         │
├──────────────────────────────────────────────────────────┤
│                                                           │
│  Layer 1: Input Validation                               │
│  - Code size limit (50KB)                                │
│  - Language validation                                   │
│  - Problem ID validation                                 │
│                                                           │
│  Layer 2: Docker Isolation                               │
│  - Separate container per execution                      │
│  - No network access                                     │
│  - No host filesystem access                             │
│  - Non-root user                                         │
│                                                           │
│  Layer 3: Resource Limits                                │
│  - CPU: 50% of single core                               │
│  - Memory: 256MB                                         │
│  - Execution timeout: 5 seconds                          │
│  - Disk I/O limits                                       │
│                                                           │
│  Layer 4: Cleanup                                        │
│  - Container removed after execution                     │
│  - Temp files deleted                                    │
│  - No persistent storage                                 │
│                                                           │
└──────────────────────────────────────────────────────────┘
```

---

## Deployment Architecture

### Development Environment

```
┌─────────────────────────────────────────────────────────┐
│                    Developer Machine                     │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  ┌──────────────┐    ┌──────────────┐                  │
│  │   Frontend   │    │   Backend    │                  │
│  │  npm run dev │    │  mvn spring  │                  │
│  │  Port: 5173  │    │  Port: 8081  │                  │
│  └──────────────┘    └──────────────┘                  │
│                                                          │
│  ┌──────────────┐    ┌──────────────┐                  │
│  │  PostgreSQL  │    │    Docker    │                  │
│  │  Port: 5432  │    │   Desktop    │                  │
│  └──────────────┘    └──────────────┘                  │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

### Production Environment (Recommended)

```
┌─────────────────────────────────────────────────────────────┐
│                      Load Balancer                           │
│                    (NGINX / AWS ALB)                         │
└────────────────────┬────────────────────────────────────────┘
                     │
        ┌────────────┴────────────┐
        │                         │
┌───────▼────────┐       ┌────────▼───────┐
│   Frontend     │       │    Backend     │
│   (Static)     │       │   (Docker)     │
│   - NGINX      │       │   - Spring     │
│   - CDN        │       │   - Multiple   │
│                │       │     instances  │
└────────────────┘       └────────┬───────┘
                                  │
                    ┌─────────────┴─────────────┐
                    │                           │
            ┌───────▼────────┐         ┌────────▼───────┐
            │   PostgreSQL   │         │     Docker     │
            │   (RDS/Cloud)  │         │     Engine     │
            │   - Replicas   │         │   - Swarm/K8s  │
            │   - Backups    │         │   - Scaling    │
            └────────────────┘         └────────────────┘
```

---

## Technology Stack

### Backend Technologies

| Component | Technology | Purpose |
|-----------|-----------|---------|
| Framework | Spring Boot 3.2.1 | Application framework |
| Language | Java 17 | Programming language |
| Security | Spring Security 6.x | Authentication & authorization |
| Database | PostgreSQL 15+ | Data persistence |
| ORM | Spring Data JPA | Database abstraction |
| Connection Pool | HikariCP | Database connections |
| JWT | jjwt 0.12.3 | Token generation |
| Docker | Docker Java SDK 3.4.0 | Container management |
| Build Tool | Maven 3.9+ | Dependency management |
| Validation | Jakarta Validation | Input validation |
| Logging | SLF4J + Logback | Application logging |

### Frontend Technologies

| Component | Technology | Purpose |
|-----------|-----------|---------|
| Framework | React 18 | UI framework |
| Build Tool | Vite | Fast build tool |
| Styling | Tailwind CSS 3.x | Utility-first CSS |
| Routing | React Router 6.x | Client-side routing |
| HTTP Client | Axios | API communication |
| Code Editor | Monaco Editor | Code editing |
| State | Context API | State management |
| Forms | React Hook Form | Form handling |

### DevOps & Infrastructure

| Component | Technology | Purpose |
|-----------|-----------|---------|
| Containerization | Docker 20+ | Application containers |
| Database | PostgreSQL 15+ | Data storage |
| Version Control | Git | Source control |
| CI/CD | GitHub Actions (planned) | Automation |
| Monitoring | Actuator | Health checks |

---

## Design Patterns Used

1. **Clean Architecture** - Separation of concerns across layers
2. **Repository Pattern** - Data access abstraction
3. **DTO Pattern** - Data transfer between layers
4. **Factory Pattern** - Docker container creation
5. **Strategy Pattern** - Multi-language execution
6. **Singleton Pattern** - Docker client instance
7. **Builder Pattern** - Complex object construction
8. **Interceptor Pattern** - Request/response logging
9. **Filter Pattern** - JWT authentication
10. **Observer Pattern** - React state management

---

## Performance Considerations

### Backend Optimizations

- Connection pooling with HikariCP
- Database query optimization with indexes
- Lazy loading for entity relationships
- Pagination for large result sets
- Async processing for code execution
- Caching for frequently accessed data

### Frontend Optimizations

- Code splitting with React.lazy()
- Memoization with useMemo/useCallback
- Virtual scrolling for large lists
- Debouncing for search inputs
- Image optimization
- Bundle size optimization

### Docker Optimizations

- Container reuse (planned)
- Image caching
- Multi-stage builds
- Minimal base images
- Resource limit tuning

---

## Scalability Considerations

### Horizontal Scaling

- Stateless backend (JWT tokens)
- Load balancer distribution
- Database read replicas
- Container orchestration (K8s)

### Vertical Scaling

- Increase container resources
- Database performance tuning
- Connection pool sizing
- JVM heap optimization

### Future Enhancements

- Redis caching layer
- Message queue for submissions
- Container pooling
- CDN for static assets
- Database sharding

---

**Last Updated:** March 1, 2026  
**Author:** Ashhar Ahmad Khan  
**Version:** 1.0.0
