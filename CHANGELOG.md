# Changelog

All notable changes to BrewAlgo will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-03-01

### Added
- **Core Platform Features**
  - User authentication and authorization with JWT
  - Problem browsing and filtering by difficulty
  - Code submission system with multi-language support (Java, Python)
  - Real-time code execution in isolated Docker containers
  - Comprehensive test case validation
  - Global leaderboard with user rankings
  - User profile with statistics tracking

- **Backend Architecture**
  - Clean Architecture implementation (4-layer separation)
  - Spring Boot 3.2.1 with Java 17
  - PostgreSQL database with JPA/Hibernate
  - Docker Java SDK integration for code execution
  - JWT-based authentication with Spring Security
  - RESTful API with 15+ endpoints
  - Global exception handling
  - Request/Response logging interceptors
  - Rate limiting middleware
  - Audit logging system

- **Frontend Features**
  - Modern React 18 application with Vite
  - Responsive UI with Tailwind CSS
  - Monaco-based code editor
  - Real-time submission feedback
  - Confetti animation for successful submissions
  - Problem list with search and filters
  - User authentication flow
  - Protected routes

- **Security Features**
  - Docker container isolation for code execution
  - Resource limits (CPU: 50%, Memory: 256MB, Timeout: 5s)
  - BCrypt password hashing
  - JWT token-based authentication
  - CORS configuration
  - SQL injection prevention with parameterized queries

- **Database Schema**
  - Users table with authentication
  - Problems table with 100 curated DSA problems
  - Submissions table with execution results
  - Test cases table with expected outputs
  - Audit logs table for tracking
  - Contests table (framework ready)

- **DevOps & Infrastructure**
  - Docker images for Java and Python execution
  - Docker Compose configuration
  - Maven build configuration
  - Environment-specific configurations
  - Database seed scripts

### Fixed
- JWT token storage key mismatch between frontend and backend
- Docker Java SDK dependency conflicts with Spring Boot
- Async log capture from Docker containers
- Submission request DTO validation
- Markdown rendering in problem descriptions
- Code editor syntax highlighting

### Security
- Implemented secure code execution in isolated containers
- Added resource limits to prevent DoS attacks
- Configured CORS for frontend-backend communication
- Implemented JWT token expiration (24 hours)
- Added BCrypt password hashing with strength 10

### Performance
- Optimized database queries with proper indexing
- Implemented connection pooling with HikariCP
- Added caching for frequently accessed data
- Async code execution processing

### Documentation
- Comprehensive README with quick start guide
- API documentation with all endpoints
- Architecture documentation with diagrams
- Setup guide for local development
- Troubleshooting guide for common issues
- Project status tracking

---

## [Unreleased]

### Planned Features
- Contest system implementation
- WebSocket for real-time updates
- Code templates for each language
- Input/output format documentation per problem
- Admin dashboard for problem management
- Analytics and monitoring
- Container pooling for better performance
- Redis caching layer
- More programming languages (C++, JavaScript)
- Problem difficulty rating system
- User submission history
- Code sharing and discussion forums

---

## Version History

- **v1.0.0** (2026-03-01) - Initial production release
  - Full-featured competitive programming platform
  - Secure Docker-based code execution
  - 100 curated DSA problems
  - Complete authentication system
  - Global leaderboard

---

**Last Updated:** March 1, 2026  
**Maintained by:** Ashhar Ahmad Khan (itzashhar@gmail.com)
