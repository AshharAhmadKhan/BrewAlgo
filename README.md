# BrewAlgo

> Coding judge platform I built to understand how LeetCode and Codeforces prevent malicious code from breaking their servers


This started as a "how hard can it be to build a coding judge?" project and turned into a deep dive into Docker isolation, resource limits, and secure code execution. It's basically my playground for learning system design.

## What it does

- Runs user code in isolated Docker containers (because running untrusted code directly would be... bad)
- Supports Java and Python (adding more languages is just adding Dockerfiles)
- Tests submissions against multiple test cases
- Returns detailed feedback: ACCEPTED, WRONG_ANSWER, COMPILATION_ERROR, TIME_LIMIT_EXCEEDED, etc.
- Has a leaderboard because competitive programming without rankings isn't fun

## Why I built this

Honestly, I was curious how platforms like LeetCode prevent someone from submitting `while(true) {fork();}` and crashing their servers. Turns out Docker resource limits and cgroups are pretty neat.

Also wanted to:
- Learn Clean Architecture (the 4-layer thing everyone talks about)
- Actually use Docker for something beyond `docker run hello-world`
- Understand how code execution services work
- Build something I could show to companies without saying "it's just a CRUD app"

## Tech Stack

**Backend:**
- Spring Boot 3.2 (with Spring Security for JWT auth)
- PostgreSQL (because relationships matter)
- Docker Java SDK (for container management)

**Frontend:**
- React + Vite (CRA felt slow)
- Tailwind CSS (I'm not a designer)

**Execution:**
- Docker containers with CPU/memory limits
- Custom images for each language (openjdk:17-slim, python:3.11-slim)

## Architecture
```
React Frontend → Spring Boot API → Docker Engine → Execute Code → Return Results
                      ↓
                 PostgreSQL
            (problems, users, submissions)
```

The interesting part is the code execution:
1. User submits code
2. Backend writes code to temp file
3. Spins up Docker container with resource limits (256MB RAM, 50% CPU, 5s timeout)
4. Runs code against test cases
5. Captures output via Docker logs
6. Compares with expected output
7. Returns verdict

## Quick Start

**Prerequisites:**
- Java 17+
- Node.js 18+
- PostgreSQL
- Docker Desktop
- Maven

**Setup:**
```bash
# Clone
git clone https://github.com/AshharAhmadKhan/BrewAlgo.git
cd BrewAlgo

# Database
psql -U postgres
CREATE DATABASE brewalgo;
\q

# Backend
cd backend
mvn spring-boot:run  # Runs on :8081

# Docker images (separate terminals)
cd docker/java-executor
docker build -t brewalgo-java-executor:latest .

cd ../python-executor
docker build -t brewalgo-python-executor:latest .

# Frontend
cd ../../frontend
npm install
npm run dev  # Runs on :5173
```

Don't forget to update `backend/src/main/resources/application.properties` with your database credentials.

## Project Structure
```
BrewAlgo/
├── backend/                 # Spring Boot
│   ├── domain/             # Entities, repositories
│   ├── application/        # Services (execution logic here)
│   ├── infrastructure/     # Security, persistence
│   └── presentation/       # Controllers
│
├── frontend/               # React
│   ├── components/
│   ├── pages/
│   └── services/          # API calls
│
└── docker/                # Execution environments
    ├── java-executor/
    └── python-executor/
```

## Challenges I faced

**1. Docker dependency hell**
Spring Boot 3.2.1 uses `httpcore5:5.2.4`, but `docker-java:3.3.4` needs `5.3+`. Took me hours to figure out I needed to explicitly force the version in Maven.

**2. Capturing Docker output**
Docker's log API returns a callback, not a string. Had to implement a custom `ResultCallback.Adapter<Frame>` to accumulate stdout. The docs weren't great here.

**3. Preventing shell injection**
Initially used `echo "input" | java Solution`, which is vulnerable. Switched to writing input to a file and redirecting it.

**4. Execution timing**
Docker overhead adds ~3-4s to execution time. Haven't optimized this yet — probably need container pooling.

## What's working

- ✅ User authentication (JWT)
- ✅ Problem CRUD
- ✅ Code submission & evaluation
- ✅ Multi-language support
- ✅ Resource limits (prevents infinite loops)
- ✅ Test case validation
- ✅ Leaderboard

## What's not (yet)

- Input format documentation for users
- More than 1 practice problem (need to add more)
- Contest system (backend is ready, UI isn't)
- Container pooling (creates new container per test case)
- WebSocket for real-time updates

## API Endpoints
```
POST /api/auth/register
POST /api/auth/login
GET  /api/problems
GET  /api/problems/slug/{slug}
POST /api/submissions
GET  /api/submissions/user/{userId}
GET  /api/leaderboard
```

See `docs/API.md` for full documentation.

## Security

- JWT authentication with BCrypt password hashing
- Docker isolation (no code runs on host)
- Resource limits (CPU, memory, timeout)
- Parameterized SQL queries (bye bye SQL injection)

## Stats

- ~10,000 lines of code
- 40+ Java files
- 25+ React components
- 15+ API endpoints
- 6 database entities

## Contributing

Feel free to fork and submit PRs. This is a learning project, so I'm open to feedback and suggestions.

## What I learned

- How Docker SDK actually works in Java
- Why async callback patterns exist (looking at you, Docker logs)
- Clean Architecture isn't just buzzwords — it actually helps organize code
- Resource limits are non-negotiable for code execution
- PostgreSQL's JSONB type is surprisingly useful for storing test cases

## License

MIT — do whatever you want with this.

## Contact

Ashhar Ahmad Khan  
📧 itzashhar@gmail.com  
💼 [LinkedIn](https://linkedin.com/in/ashhar-ahmad-khan)  
🐙 [GitHub](https://github.com/AshharAhmadKhan)

---

Built this to learn, sharing it to help others learn too.

If this helped you understand code execution systems better, give it a ⭐️
 
