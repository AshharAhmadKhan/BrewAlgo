# Contributing to BrewAlgo

First off, thank you for considering contributing to BrewAlgo! It's people like you that make BrewAlgo such a great platform.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
- [Development Setup](#development-setup)
- [Coding Standards](#coding-standards)
- [Commit Guidelines](#commit-guidelines)
- [Pull Request Process](#pull-request-process)
- [Project Structure](#project-structure)

---

## Code of Conduct

This project and everyone participating in it is governed by our commitment to providing a welcoming and inspiring community for all. Please be respectful and constructive in your interactions.

### Our Standards

- Using welcoming and inclusive language
- Being respectful of differing viewpoints and experiences
- Gracefully accepting constructive criticism
- Focusing on what is best for the community
- Showing empathy towards other community members

---

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues to avoid duplicates. When you create a bug report, include as many details as possible:

- **Use a clear and descriptive title**
- **Describe the exact steps to reproduce the problem**
- **Provide specific examples**
- **Describe the behavior you observed and what you expected**
- **Include screenshots if applicable**
- **Include your environment details** (OS, Java version, Node version, Docker version)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, include:

- **Use a clear and descriptive title**
- **Provide a detailed description of the suggested enhancement**
- **Explain why this enhancement would be useful**
- **List any alternative solutions you've considered**

### Your First Code Contribution

Unsure where to begin? You can start by looking through `good-first-issue` and `help-wanted` issues:

- **Good first issues** - issues which should only require a few lines of code
- **Help wanted issues** - issues which are a bit more involved

### Pull Requests

- Fill in the required template
- Follow the coding standards
- Include appropriate test cases
- Update documentation as needed
- End all files with a newline

---

## Development Setup

### Prerequisites

```bash
- Java 17 or higher
- Node.js 18+
- PostgreSQL 15+
- Docker Desktop
- Maven 3.9+
- Git
```

### Local Setup

1. **Fork and Clone**
   ```bash
   git clone https://github.com/YOUR_USERNAME/BrewAlgo.git
   cd BrewAlgo
   ```

2. **Create a Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Backend Setup**
   ```bash
   cd backend
   # Update application.properties with your DB credentials
   mvn clean install
   mvn spring-boot:run
   ```

4. **Frontend Setup**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

5. **Build Docker Images**
   ```bash
   cd docker/java-executor
   docker build -t brewalgo-java-executor:latest .
   
   cd ../python-executor
   docker build -t brewalgo-python-executor:latest .
   ```

---

## Coding Standards

### Java (Backend)

- **Follow Clean Architecture principles**
  - Keep layers separated (Presentation → Application → Domain → Infrastructure)
  - Dependencies point inward
  - Domain layer has no external dependencies

- **Code Style**
  - Use Java 17 features where appropriate
  - Follow standard Java naming conventions
  - Use Lombok annotations to reduce boilerplate
  - Maximum line length: 120 characters
  - Use meaningful variable and method names

- **Documentation**
  - Add Javadoc for public methods and classes
  - Include @param and @return tags
  - Explain complex logic with inline comments

- **Testing**
  - Write unit tests for services
  - Write integration tests for controllers
  - Aim for >80% code coverage
  - Use meaningful test names: `shouldReturnUserWhenValidIdProvided()`

### JavaScript/React (Frontend)

- **Code Style**
  - Use functional components with hooks
  - Follow React best practices
  - Use ES6+ features
  - Maximum line length: 100 characters
  - Use meaningful component and variable names

- **Component Structure**
  ```javascript
  // 1. Imports
  // 2. Component definition
  // 3. State and hooks
  // 4. Event handlers
  // 5. Effects
  // 6. Render
  // 7. Export
  ```

- **Documentation**
  - Add JSDoc for complex functions
  - Comment non-obvious logic
  - Keep comments up-to-date

### Database

- **Migrations**
  - Never modify existing migrations
  - Create new migrations for schema changes
  - Test migrations on a copy of production data

- **Queries**
  - Use JPA repositories
  - Avoid N+1 queries
  - Add indexes for frequently queried columns
  - Use pagination for large result sets

---

## Commit Guidelines

### Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- **feat**: A new feature
- **fix**: A bug fix
- **docs**: Documentation only changes
- **style**: Code style changes (formatting, missing semi-colons, etc)
- **refactor**: Code refactoring without changing functionality
- **perf**: Performance improvements
- **test**: Adding or updating tests
- **chore**: Changes to build process or auxiliary tools

### Examples

```
feat(submissions): add Python language support

- Add Python executor Docker image
- Update CodeExecutionService to handle Python
- Add Python syntax highlighting in editor

Closes #123
```

```
fix(auth): resolve JWT token storage key mismatch

The frontend was using 'token' while backend expected 'brewalgo_token'.
Updated api.js to use STORAGE_KEYS.TOKEN constant.

Fixes #456
```

---

## Pull Request Process

1. **Update Documentation**
   - Update README.md if needed
   - Update API.md for new endpoints
   - Update CHANGELOG.md with your changes

2. **Test Your Changes**
   - Run all tests: `mvn test` (backend)
   - Test manually in browser
   - Ensure Docker execution works

3. **Create Pull Request**
   - Use a clear title describing the change
   - Fill out the PR template completely
   - Link related issues
   - Add screenshots for UI changes

4. **Code Review**
   - Address review comments promptly
   - Keep discussions focused and professional
   - Be open to suggestions

5. **Merge**
   - Squash commits if requested
   - Ensure CI passes
   - Wait for maintainer approval

---

## Project Structure

### Backend (Spring Boot)

```
backend/src/main/java/com/brewalgo/
├── domain/              # Core business entities
│   ├── entity/         # JPA entities
│   ├── repository/     # Data access interfaces
│   └── exception/      # Domain exceptions
├── application/         # Business logic
│   ├── dto/            # Data transfer objects
│   └── service/        # Service layer
├── infrastructure/      # External concerns
│   ├── security/       # JWT, authentication
│   ├── persistence/    # JPA implementations
│   └── config/         # Spring configuration
└── presentation/        # API layer
    └── controller/     # REST controllers
```

### Frontend (React)

```
frontend/src/
├── components/          # Reusable components
│   ├── common/         # Shared UI components
│   └── effects/        # Visual effects
├── pages/              # Page components
├── services/           # API integration
├── context/            # React context
├── hooks/              # Custom hooks
└── utils/              # Utility functions
```

---

## Areas for Contribution

### High Priority

- [ ] Add more programming languages (C++, JavaScript)
- [ ] Implement contest system UI
- [ ] Add code templates for each language
- [ ] Create admin dashboard
- [ ] Add WebSocket for real-time updates

### Medium Priority

- [ ] Improve error messages
- [ ] Add more DSA problems
- [ ] Implement user submission history
- [ ] Add problem difficulty rating
- [ ] Create discussion forums

### Low Priority

- [ ] Dark mode theme
- [ ] Mobile app
- [ ] Social features
- [ ] Gamification elements
- [ ] Code sharing

---

## Questions?

Feel free to reach out:

- **Email:** itzashhar@gmail.com
- **GitHub Issues:** [Create an issue](https://github.com/AshharAhmadKhan/BrewAlgo/issues)

---

**Thank you for contributing to BrewAlgo!** 🎉

---

**Last Updated:** March 1, 2026  
**Maintained by:** Ashhar Ahmad Khan
