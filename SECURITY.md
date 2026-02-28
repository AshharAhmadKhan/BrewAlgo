# Security Policy

## Supported Versions

Currently supported versions of BrewAlgo with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |
| < 1.0   | :x:                |

---

## Security Features

BrewAlgo implements multiple layers of security to protect users and the platform:

### 1. Authentication & Authorization

- **JWT-based Authentication**
  - HMAC-SHA256 signing algorithm
  - 24-hour token expiration
  - Secure token storage in localStorage
  - Token validation on every request

- **Password Security**
  - BCrypt hashing with strength 10
  - Minimum password requirements enforced
  - No plain-text password storage
  - Secure password reset flow (planned)

### 2. Code Execution Security

- **Docker Container Isolation**
  - Each code submission runs in an isolated container
  - No access to host system
  - Containers destroyed after execution
  - No persistent storage in containers

- **Resource Limits**
  - CPU: 50% of single core
  - Memory: 256MB maximum
  - Execution timeout: 5 seconds
  - Prevents resource exhaustion attacks

- **Input Sanitization**
  - All user inputs validated
  - File-based input (no shell injection)
  - No direct shell command execution
  - Parameterized database queries

### 3. API Security

- **CORS Configuration**
  - Restricted to allowed origins
  - Credentials handling configured
  - Preflight request support

- **Rate Limiting**
  - Request throttling per user
  - Prevents brute force attacks
  - Configurable limits

- **Input Validation**
  - Jakarta Validation annotations
  - DTO-level validation
  - Size limits on code submissions (50KB)
  - SQL injection prevention

### 4. Database Security

- **Connection Security**
  - Encrypted connections (SSL/TLS)
  - Connection pooling with HikariCP
  - Prepared statements only
  - No dynamic SQL

- **Data Protection**
  - Sensitive data encrypted
  - Audit logging enabled
  - Regular backups recommended
  - Access control at database level

### 5. Infrastructure Security

- **Docker Security**
  - Non-root user in containers
  - Read-only file systems where possible
  - Network isolation
  - Image scanning recommended

- **Environment Variables**
  - Secrets not committed to repository
  - Environment-specific configurations
  - JWT secret rotation recommended

---

## Reporting a Vulnerability

We take security vulnerabilities seriously. If you discover a security issue, please follow these steps:

### 1. **DO NOT** Create a Public Issue

Please do not report security vulnerabilities through public GitHub issues.

### 2. Report Privately

Send an email to: **itzashhar@gmail.com**

Include:
- Description of the vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix (if any)

### 3. Response Timeline

- **Initial Response:** Within 48 hours
- **Status Update:** Within 7 days
- **Fix Timeline:** Depends on severity
  - Critical: 1-7 days
  - High: 7-14 days
  - Medium: 14-30 days
  - Low: 30-90 days

### 4. Disclosure Policy

- We will acknowledge your email within 48 hours
- We will provide a detailed response within 7 days
- We will work with you to understand and resolve the issue
- We will credit you in the security advisory (unless you prefer to remain anonymous)
- We will publicly disclose the vulnerability after a fix is released

---

## Security Best Practices for Deployment

### Production Deployment Checklist

- [ ] Change default JWT secret
- [ ] Use strong database passwords
- [ ] Enable HTTPS/TLS
- [ ] Configure firewall rules
- [ ] Enable Docker security features
- [ ] Set up monitoring and alerting
- [ ] Regular security updates
- [ ] Backup strategy in place
- [ ] Rate limiting configured
- [ ] CORS properly configured

### Environment Variables

Never commit these to version control:

```bash
# Database
DB_PASSWORD=<strong-password>

# JWT
JWT_SECRET=<256-bit-secret>
JWT_EXPIRATION=86400000

# Docker
DOCKER_HOST=<docker-host>
```

### Docker Security

```dockerfile
# Use specific versions
FROM eclipse-temurin:17-jdk-alpine

# Run as non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Read-only root filesystem
--read-only

# Drop capabilities
--cap-drop=ALL
```

### Database Security

```properties
# Use SSL
spring.datasource.url=jdbc:postgresql://localhost:5432/brewalgo?ssl=true&sslmode=require

# Connection limits
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

---

## Known Security Considerations

### Current Limitations

1. **Container Overhead**
   - Each submission creates a new container
   - Potential for resource exhaustion with many concurrent submissions
   - Mitigation: Rate limiting, queue system

2. **Code Execution Risks**
   - Malicious code could attempt to escape container
   - Mitigation: Docker isolation, resource limits, timeout

3. **JWT Token Storage**
   - Tokens stored in localStorage (XSS vulnerable)
   - Mitigation: Strict CSP, input sanitization
   - Future: Consider httpOnly cookies

4. **No 2FA**
   - Single-factor authentication only
   - Planned: Two-factor authentication

### Planned Security Enhancements

- [ ] Two-factor authentication (2FA)
- [ ] OAuth2 integration
- [ ] httpOnly cookie for JWT
- [ ] Content Security Policy (CSP)
- [ ] Security headers (HSTS, X-Frame-Options, etc.)
- [ ] Container image scanning
- [ ] Dependency vulnerability scanning
- [ ] Penetration testing
- [ ] Security audit logging
- [ ] Intrusion detection

---

## Security Updates

Security updates will be released as patch versions (e.g., 1.0.1, 1.0.2) and documented in:

- [CHANGELOG.md](CHANGELOG.md)
- GitHub Security Advisories
- Release notes

Subscribe to repository notifications to stay informed.

---

## Compliance

### Data Protection

- User passwords are hashed and never stored in plain text
- JWT tokens expire after 24 hours
- User data is not shared with third parties
- Audit logs track all submissions

### Code Execution

- All code runs in isolated containers
- No persistent storage of user code
- Execution logs are temporary
- Resource limits prevent abuse

---

## Security Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Docker Security Best Practices](https://docs.docker.com/engine/security/)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT Best Practices](https://tools.ietf.org/html/rfc8725)

---

## Contact

For security concerns:
- **Email:** itzashhar@gmail.com
- **GitHub:** [@AshharAhmadKhan](https://github.com/AshharAhmadKhan)

For general questions, please use GitHub Issues.

---

**Last Updated:** March 1, 2026  
**Security Officer:** Ashhar Ahmad Khan
