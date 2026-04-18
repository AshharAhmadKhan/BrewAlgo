# BrewAlgo

Competitive programming platforms like LeetCode exist, but building one from scratch teaches you things using them never will. That's why I built BrewAlgo — a full-stack online judge where you can solve DSA problems, submit code, and see it run in real time.

This is my personal project, built during my BTech CSE at Jamia Hamdard University.

---

## What it does

BrewAlgo is an online coding judge. You browse problems, write code in the browser, submit it, and the platform runs it against test cases and tells you if you passed.

The two things I'm most proud of:

**Secure code execution** — your code runs inside an isolated Docker container with CPU, memory, and time limits. It never touches the host machine.

**Clean Architecture** — the backend is structured in four layers (Domain, Application, Infrastructure, Presentation) so the business logic stays completely independent of frameworks and databases.

---

## Features

- User registration and login with JWT authentication
- 100+ curated DSA problems across Easy, Medium, and Hard
- Code submission in Java and Python
- Real-time execution with detailed feedback (Accepted, Wrong Answer, TLE, Runtime Error, Compilation Error)
- Global leaderboard ranked by rating
- User profile with submission history and skill breakdown
- Rate limiting, audit logging, and request tracing

---

## How to set it up

You'll need Java 17, Node.js 18, PostgreSQL 15, Docker Desktop, and Maven 3.9.

**1. Clone the repo**
```bash
git clone https://github.com/AshharAhmadKhan/BrewAlgo.git
cd BrewAlgo
```

**2. Create the database**
```bash
psql -U postgres
CREATE DATABASE brewalgo;
\q
```

**3. Configure the backend**

Open `backend/src/main/resources/application.properties` and update:
```properties
spring.datasource.password=your_postgres_password
jwt.secret=your_secret_key_here
```

**4. Build the Docker executor images**
```bash
cd docker/java-executor
docker build -t brewalgo-java-executor:latest .

cd ../python-executor
docker build -t brewalgo-python-executor:latest .
```

**5. Start the backend**
```bash
cd backend
mvn spring-boot:run
```

**6. Start the frontend**
```bash
cd frontend
npm install
npm run dev
```

Frontend runs at `http://localhost:5173`, backend at `http://localhost:8081`.

---

## How the code execution works

When you submit code, here's what actually happens:

1. The backend writes your code to a temporary file
2. It creates a Docker container with strict limits — 256MB memory, 50% of one CPU core, 5 second timeout
3. The container compiles and runs your code against each test case
4. Output is captured and compared to the expected answer
5. The container is destroyed and the temp files are deleted
6. You get a verdict

The container has no network access and no access to the host filesystem. If your code tries to do something malicious, it just fails inside the container.

---

## Why Docker for execution

The obvious answer is security. But there's more to it.

Every submission gets a consistent environment regardless of what's installed on the server. The resource limits are enforced at the OS level, not by the application. And the architecture makes it easy to add new languages — you just write a new Dockerfile.

The tradeoff is speed. Creating a new container for each test case adds about 1-2 seconds of overhead. That's a known limitation and something I'd fix with container pooling if I continued developing this.

---

## Tech stack

**Backend** — Spring Boot 3.2.1, Java 17, Spring Security with JWT, PostgreSQL, Spring Data JPA, Docker Java SDK

**Frontend** — React 18, Vite, Tailwind CSS, Monaco Editor, Axios

**Infrastructure** — Docker, Docker Compose, HikariCP connection pooling

---

## Project structure

```
BrewAlgo/
├── backend/
│   └── src/main/java/com/brewalgo/
│       ├── domain/          # Entities, repositories, exceptions
│       ├── application/     # Services, DTOs, business logic
│       ├── infrastructure/  # Security, persistence, config
│       └── presentation/    # Controllers
├── frontend/
│   └── src/
│       ├── components/      # Reusable UI components
│       ├── pages/           # Page components
│       ├── services/        # API layer
│       └── context/         # Auth and toast state
├── docker/
│   ├── java-executor/       # Java execution environment
│   └── python-executor/     # Python execution environment
└── docs/                    # Architecture, API, setup guides
```

---

## What's not built yet

- Contest system (the backend is ready, the UI just shows "coming soon")
- WebSocket for real-time updates
- More languages (C++, JavaScript)
- Admin dashboard for managing problems
- Redis caching layer
- Container pooling for faster execution

---

## Docs

- [Architecture](docs/ARCHITECTURE.md) — system design, data flow, security layers
- [API Reference](docs/API.md) — all endpoints with request/response examples
- [Setup Guide](docs/SETUP.md) — detailed setup with troubleshooting

---

## Author

Ashhar Ahmad Khan
Enrollment No. 2023-310-059
BTech CSE, Jamia Hamdard University

itzashhar@gmail.com
