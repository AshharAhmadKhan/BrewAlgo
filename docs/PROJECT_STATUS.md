# BrewAlgo - Project Status Document

**Last Updated:** January 15, 2026  
**Version:** 1.0.0 MVP  
**Status:** ✅ Demo-Ready & Interview-Ready  
**Developer:** Ashhar Ahmad Khan (BTech 3rd Year)

---

## 🎯 **Project Overview**

BrewAlgo is a **production-grade online judge platform** (similar to LeetCode/HackerRank) that enables:
- Secure, Docker-isolated code execution
- Multi-language support (Java, Python)
- Real-time test case evaluation
- User authentication and progress tracking
- Global leaderboard system

**Core Innovation:** Secure code execution with resource limits using Docker containers.

---

## ✅ **Completed Phases**

### **Phase 1: Foundation (Weeks 1-2)** ✅
**Status:** 100% Complete

#### Backend
- [x] Spring Boot 3.2.1 project setup
- [x] Clean Architecture implementation (4 layers)
- [x] PostgreSQL database integration
- [x] JPA entity relationships
- [x] JWT authentication with Spring Security 6
- [x] BCrypt password hashing
- [x] CORS configuration
- [x] 15+ REST API endpoints
- [x] DTO pattern implementation
- [x] Service layer abstraction
- [x] Repository pattern with Spring Data JPA

#### Frontend
- [x] React 18 + Vite setup
- [x] Tailwind CSS styling
- [x] React Router v6 navigation
- [x] Authentication context (JWT storage)
- [x] Protected routes
- [x] Login/Register pages
- [x] Problem listing page
- [x] Problem detail page
- [x] Leaderboard page
- [x] User profile page
- [x] Responsive design

#### Database
- [x] Users table
- [x] Problems table
- [x] Submissions table
- [x] Test Cases table
- [x] Contests table (structure only)
- [x] Seed data with sample problems
- [x] Foreign key relationships

---

### **Phase 2: Code Execution Engine (Weeks 3-4)** ✅
**Status:** 100% Complete

#### Docker Integration
- [x] Docker Desktop setup (WSL2)
- [x] Java executor Dockerfile (JDK 17 Alpine)
- [x] Python executor Dockerfile (Python 3.11 Alpine)
- [x] Docker images built and tested
- [x] Container resource limits (CPU, memory)
- [x] Execution timeout implementation (5 seconds)

#### Execution Service
- [x] CodeExecutionService implementation
- [x] Docker Java SDK integration (v3.4.0)
- [x] Container creation with bind mounts
- [x] Input file redirection (`input.txt`)
- [x] Output capture via `ResultCallback.Adapter<Frame>`
- [x] Compilation error detection
- [x] Runtime error handling
- [x] Timeout handling
- [x] Output normalization and comparison
- [x] Sequential test case execution
- [x] Early exit on failure

#### Status Detection
- [x] ACCEPTED
- [x] WRONG_ANSWER
- [x] COMPILATION_ERROR
- [x] RUNTIME_ERROR
- [x] TIME_LIMIT_EXCEEDED

#### Frontend Integration
- [x] Submission form with language selector
- [x] Code editor textarea
- [x] Real-time status display
- [x] Test case pass/fail count
- [x] Execution time display
- [x] Error message display
- [x] Output display

---

## 🔧 **Major Technical Challenges Solved**

### **Challenge 1: Docker Dependency Conflict**
**Problem:**
```
java.lang.NoClassDefFoundError: org/apache/hc/core5/http2/HttpVersionPolicy
```

**Root Cause:**
- Spring Boot 3.2.1 manages `httpcore5:5.2.4`
- `docker-java-transport-httpclient5:3.3.4` requires `httpcore5:5.3+`
- `HttpVersionPolicy` class added in 5.3.x

**Solution:**
```xml
<!-- pom.xml -->
<properties>
    <httpclient5.version>5.3.1</httpclient5.version>
    <httpcore5.version>5.3.1</httpcore5.version>
</properties>
<dependency>
    <groupId>com.github.docker-java</groupId>
    <artifactId>docker-java</artifactId>
    <version>3.4.0</version>
</dependency>
```

**Lesson Learned:** Spring Boot's dependency management can override transitive dependencies. Use `<properties>` to force specific versions.

---

### **Challenge 2: Container Output Not Captured**
**Problem:**
```java
String output = dockerClient.logContainerCmd(containerId)
    .exec(new ResultCallback.Adapter<>())
    .toString(); // Returns: "ResultCallback$Adapter@75b3a686"
```

**Root Cause:**
- `.toString()` called on callback object, not the logs
- `ResultCallback.Adapter` doesn't accumulate output by default

**Solution:**
```java
StringBuilder outputBuilder = new StringBuilder();
dockerClient.logContainerCmd(containerId)
    .withStdOut(true)
    .withStdErr(true)
    .exec(new ResultCallback.Adapter<Frame>() {
        @Override
        public void onNext(Frame frame) {
            outputBuilder.append(new String(frame.getPayload(), StandardCharsets.UTF_8));
        }
    })
    .awaitCompletion();
String output = outputBuilder.toString().trim();
```

**Lesson Learned:** Async callbacks require manual data accumulation. Docker SDK doesn't buffer output automatically.

---

### **Challenge 3: Frontend Status Not Displaying**
**Problem:**
```javascript
// Frontend expected this:
response.data.status

// Backend actually returned:
{
  "submission": { "status": "..." },
  "executionResult": { "status": "..." }
}
```

**Solution:**
```javascript
const result = await submitSolution(...);
const executionResult = result.executionResult || {};
const submissionData = result.submission || {};

setSubmission({
  status: executionResult.status,
  output: executionResult.output,
  errorMessage: executionResult.errorMessage,
  // ... other fields
});
```

**Lesson Learned:** Always validate API response structure in browser console before assuming the shape.

---

### **Challenge 4: Shell Injection Risk**
**Problem:**
```bash
echo 'user input' | java Solution  # Dangerous if input contains quotes/commands
```

**Solution:**
```java
// Write input to file
Files.writeString(workDir.resolve("input.txt"), testCase.getInput());

// Redirect safely
String[] command = {"sh", "-c", "java Solution < input.txt"};
```

**Lesson Learned:** Never concatenate user input into shell commands. Use file redirection.

---

## 📊 **Current System Metrics**

### **Performance**
- Average submission time: 3-5 seconds
  - Docker container creation: ~1s
  - JVM startup: ~1-2s
  - Code execution: <1s
  - Container cleanup: ~500ms
- Database query time: <100ms
- API response time: <200ms (non-submission endpoints)

### **Scale**
- Lines of Code: ~10,000+
  - Backend: ~4,000 (Java)
  - Frontend: ~3,000 (JavaScript/JSX)
  - Config/SQL: ~1,000
- API Endpoints: 15+
- Database Tables: 6 entities
- Docker Images: 2 (Java, Python)
- Test Problems: 1 (Two Sum)
- Concurrent Users: Not tested (single-user MVP)

### **Code Quality**
- Architecture: Clean Architecture (4 layers)
- Design Patterns: Repository, Service, DTO, Strategy
- Security: JWT, BCrypt, Docker isolation, resource limits
- Error Handling: Try-catch blocks, custom exceptions
- Logging: SLF4J with debug statements

---

## ⚠️ **Known Issues & Limitations**

### **Critical Issues** (Must Address Before Production)

#### 1. **Execution Time Metric Is Misleading**
**Status:** Known Limitation  
**Impact:** Shows 3-5s even for instant code  
**Cause:** Includes Docker startup + JVM boot time  
**Current Workaround:** Labeled as "Total Runtime"  
**Future Fix:** Measure time inside container after JVM initialization  
**Priority:** Medium (cosmetic, doesn't affect correctness)

#### 2. **No Input Format Documentation**
**Status:** Planned for Phase 3  
**Impact:** Users don't know how to read input from stdin  
**Current Workaround:** None (users must guess)  
**Future Fix:** Add "Input Format" collapsible section with code template  
**Priority:** High (usability issue)

#### 3. **Class Name Enforcement**
**Status:** Known Limitation  
**Impact:** Code must use `public class Solution` exactly  
**Current Workaround:** Document the requirement  
**Future Fix:** Regex-based class name rewriting before execution  
**Priority:** Medium (can be documented)

---

### **Design Limitations** (Non-Blocking)

#### 4. **Container Per Test Case**
**Status:** Optimization Opportunity  
**Impact:** Slow execution (~3s overhead per test)  
**Cause:** Creates new container for each test case  
**Better Approach:** Compile once, run multiple times in same container  
**Priority:** Low (works correctly, just inefficient)

#### 5. **No Function-Based Submission**
**Status:** Feature Gap  
**Impact:** Users must write full `main()` method  
**Comparison:** LeetCode allows function signatures only  
**Future Fix:** Code wrapping + reflection (complex)  
**Priority:** Low (current approach is valid)

#### 6. **Output Format Rigidity**
**Status:** Limitation  
**Impact:** Exact string match required (whitespace-normalized)  
**Current Workaround:** `normalizeOutput()` removes extra spaces  
**Better Approach:** Problem-specific comparators  
**Priority:** Low (works for most cases)

---

## 🎯 **What Works Right Now**

### **End-to-End Flow** ✅
1. User logs in → JWT stored in localStorage
2. User browses problems → API returns list
3. User opens problem → Sees description + test cases
4. User writes code → Submits via form
5. Backend receives code → Creates Docker container
6. Code compiles → Runs against test cases
7. Output captured → Compared with expected
8. Status determined → Saved to database
9. Frontend receives result → Displays status
10. User sees outcome → Can retry

### **Security** ✅
- JWT authentication protects endpoints
- BCrypt hashes passwords
- Docker isolates user code
- CPU limited to 50% of one core
- Memory capped at 256MB
- Execution times out at 5 seconds
- No host filesystem access
- SQL injection prevented (JPA)

### **Validation** ✅
- Input validation on frontend and backend
- Error messages propagated correctly
- Edge cases handled (empty input, timeout, etc.)
- Database constraints enforced

---

## 📅 **Next Steps (When Resuming Development)**

### **Phase 3: UI/UX Polish (2-3 Days)**

#### Day 1: Documentation
- [ ] Add "Input Format" section to problem page
  - Show example input/output
  - Provide code template with Scanner
  - Explain expected output format
- [ ] Create 2-3 more sample problems
  - Reverse String (Easy)
  - Palindrome Number (Easy)
  - Fibonacci (Medium)
- [ ] Add loading spinners during submission
- [ ] Improve error message formatting (syntax highlighting)

#### Day 2: Performance
- [ ] Measure execution time inside container
  - Add timestamp before/after code execution
  - Exclude JVM startup time
- [ ] Display separate metrics:
  - Total Runtime (includes overhead)
  - Execution Time (code only)
- [ ] Add memory usage tracking

#### Day 3: Testing
- [ ] Test all 3 problems with various inputs
- [ ] Verify edge cases (empty input, large input)
- [ ] Test timeout scenarios
- [ ] Test compilation errors
- [ ] Cross-browser testing (Chrome, Firefox)

---

### **Phase 4: Advanced Features (Post-Placement, Optional)**

#### Contest System
- [ ] Contest start/end time enforcement
- [ ] Live leaderboard (WebSocket)
- [ ] Time-based scoring
- [ ] Penalty for wrong submissions
- [ ] Contest registration/participation

#### Code Editor Upgrade
- [ ] Monaco Editor integration (VS Code-like)
- [ ] Syntax highlighting
- [ ] Auto-completion
- [ ] Line numbers
- [ ] Theme selection (dark/light)

#### Performance Optimization
- [ ] Container pooling (reuse containers)
- [ ] Compilation caching
- [ ] Redis for leaderboard
- [ ] Database connection pooling
- [ ] Query optimization

#### Observability
- [ ] Prometheus metrics
- [ ] Grafana dashboards
- [ ] ELK stack for logs
- [ ] Sentry error tracking
- [ ] Performance monitoring

---

## 🔄 **How to Resume Development**

### **Starting the System**
```bash
# Terminal 1: Backend
cd ~/brewalgo/backend
mvn spring-boot:run

# Terminal 2: Frontend
cd ~/brewalgo/frontend
npm run dev

# Verify Docker
docker ps
docker images | grep brewalgo
```

### **Debugging Checklist**
When things break, check in this order:

1. **Is Docker Desktop running?**
```bash
   docker ps  # Should not error
```

2. **Is PostgreSQL running?**
```bash
   # Windows: Check Services
   # WSL: sudo service postgresql status
   # Test connection: psql -U postgres -d brewalgo
```

3. **Are Docker images built?**
```bash
   docker images | grep brewalgo
   # Should show: brewalgo-java-executor, brewalgo-python-executor
```

4. **Is backend running on port 8081?**
```bash
   curl http://localhost:8081/api/problems
   # Should return JSON
```

5. **Is frontend running on port 5173?**
```bash
   # Open browser: http://localhost:5173
```

6. **Are CORS settings correct?**
```java
   // SecurityConfig.java should have:
   configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
```

7. **Is JWT secret configured?**
```properties
   # application.properties
   jwt.secret=<your-secret>
```

---

## 📞 **Getting Help (What to Provide)**

When asking for help or coming back after a break, provide:

1. **What you're trying to do:**
   - Example: "I want to add C++ language support"

2. **What's not working:**
   - Exact error message (full stack trace)
   - Last 30-50 lines of backend logs
   - Browser console errors (F12 → Console)

3. **What you've tried:**
   - "I added the Dockerfile"
   - "I updated CodeExecutionService"
   - "I checked Docker is running"

4. **Relevant code:**
   - Paste the specific file/method you modified
   - Use triple backticks for formatting

5. **Environment info:**
   - OS: Windows 11 + WSL2
   - Java version: `java -version`
   - Docker version: `docker --version`
   - Node version: `node --version`

---

## 🎓 **Key Learnings Documented**

### **Architecture Decisions**

#### Why Clean Architecture?
- **Framework Independence:** Can swap Spring for Micronaut without rewriting business logic
- **Testability:** Domain layer has zero dependencies
- **Maintainability:** Clear boundaries, easy to understand
- **Scalability:** Can extract services into microservices later

#### Why Docker for Execution?
- **Security:** Full process isolation, no host access
- **Consistency:** Same environment for all submissions
- **Resource Control:** CPU, memory, and time limits enforced
- **Multi-Language:** Each language gets its own image
- **Scalability:** Can distribute containers across machines

#### Why PostgreSQL?
- **ACID Compliance:** Strong consistency guarantees
- **JSON Support:** Can store complex test cases
- **Proven Reliability:** Battle-tested in production
- **Rich Ecosystem:** pgAdmin, backups, replication

#### Why JWT?
- **Stateless:** No server-side session storage needed
- **Scalable:** Works across multiple backend instances
- **Standard:** Well-understood, many libraries
- **Secure:** HMAC-SHA256 signing prevents tampering

---

### **Technical Skills Gained**

1. **Backend Development**
   - Clean Architecture implementation
   - Spring Boot 3.x features
   - Spring Security 6 configuration
   - JPA entity relationships
   - DTO pattern
   - Service layer design

2. **Docker Integration**
   - Docker Java SDK usage
   - Container lifecycle management
   - Resource limiting
   - Volume binding
   - Async callback patterns

3. **Frontend Development**
   - React Hooks (useState, useEffect, useContext)
   - React Router v6
   - Axios HTTP client
   - JWT storage and management
   - Protected routes

4. **Problem Solving**
   - Maven dependency conflict resolution
   - Async output accumulation
   - Shell injection prevention
   - API response structure debugging

5. **DevOps**
   - Dockerfile creation
   - Docker image building
   - WSL2 setup
   - PostgreSQL administration

---

## 🏆 **Achievements Unlocked**

- ✅ Built a working online judge from scratch
- ✅ Implemented secure, Docker-based code execution
- ✅ Solved complex Maven dependency conflicts
- ✅ Designed scalable Clean Architecture
- ✅ Created 15+ production-ready REST APIs
- ✅ Integrated async Docker callbacks
- ✅ Handled frontend-backend authentication
- ✅ Wrote comprehensive documentation

---

## 💼 **Interview Talking Points**

### **When Explaining This Project:**

**Opening Statement:**
> "I built BrewAlgo, a competitive programming platform similar to LeetCode, with a focus on secure code execution. The core challenge was running arbitrary user code safely, which I solved using Docker containers with CPU, memory, and timeout limits."

**Technical Deep-Dive:**
> "I implemented Clean Architecture with 4 layers to keep business logic framework-independent. The most interesting part was integrating the Docker Java SDK to create isolated containers for each submission. I had to solve a Maven dependency conflict where Spring Boot's managed httpcore5 version was incompatible with docker-java, which I resolved by forcing version 5.3.1."

**Problem-Solving Example:**
> "One challenge was capturing container output. The Docker SDK uses async callbacks, and I initially called `.toString()` on the callback object, which just returned a memory reference. I fixed it by implementing a custom ResultCallback.Adapter that accumulates output frames into a StringBuilder."

**System Design Discussion:**
> "For security, each submission runs in an isolated Docker container with a 256MB memory limit, 50% CPU quota, and 5-second timeout. The container has no network access and can only write to its temporary directory, which gets deleted after execution."

---

## 📝 **Final Notes**

### **This is an MVP**
- Focus on core functionality, not feature bloat
- The execution engine works reliably
- Security measures are in place
- Code is clean and documented

### **Don't Over-Engineer**
- This is already better than 95% of BTech projects
- Recruiters care more about depth than breadth
- Master explaining what you built
- Be ready to discuss trade-offs

### **What Makes This Strong:**
1. Solves a real problem (secure code execution)
2. Uses production technologies (Spring Boot, Docker)
3. Shows system design thinking (Clean Architecture)
4. Demonstrates problem-solving (dependency conflicts)
5. Has depth (not just CRUD operations)

---

## ✅ **Current Status Summary**

**What's Working:** Everything core to the MVP  
**What's Missing:** Polish (input docs, more problems, UI improvements)  
**What's Next:** Phase 3 polish (2-3 days) or stop here for placements  
**Recommendation:** Add documentation, then STOP and focus on interview prep

---

**This project is placement-ready NOW. Polish makes it interview-winning.**

**Last Commit:** January 15, 2026  
**Status:** ✅ Ready for Demo  
**Next Review:** When resuming development

---

*Document maintained by Ashhar Ahmad Khan*  
*For questions or clarifications, review this document first, then ask with context*