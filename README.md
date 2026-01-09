# 🚀 BrewAlgo

> **A production-grade full-stack algorithmic problem-solving platform**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19.2.0-blue.svg)](https://reactjs.org/)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Master algorithmic problem-solving with our interactive platform. Compete in live contests, track your progress, and climb the global leaderboard! 🏆

![BrewAlgo Banner](https://via.placeholder.com/1200x400/667eea/ffffff?text=BrewAlgo+-+Code+•+Compete+•+Conquer)

---

## ✨ Features

### 🎯 Core Functionality
- **Problem Bank**: 500+ algorithmic challenges across Easy, Medium, and Hard difficulties
- **Live Contests**: Real-time competitive programming with live leaderboards
- **Code Submission**: Multi-language support (Java, Python, JavaScript, C++)
- **Smart Evaluation**: Automatic code execution with performance metrics
- **Global Rankings**: Dynamic leaderboard with rating system
- **User Profiles**: Track problems solved, rating, and submission history

### 🎨 Premium UI/UX
- Glassmorphism design with smooth animations
- Custom cursor effects and hover interactions
- Responsive layout for all device sizes
- Dark gradient themes with professional branding
- Framer Motion powered transitions

### 🏗️ Technical Excellence
- **Clean Architecture**: Domain-driven design with clear separation of concerns
- **RESTful APIs**: 50+ endpoints following REST best practices
- **Real-time Updates**: WebSocket integration for live features
- **State Management**: React Context API for global state
- **Type Safety**: Comprehensive validation with Jakarta Validation
- **Transaction Management**: ACID compliance with Spring transactions

---

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.2.1
- **Language**: Java 17
- **Database**: PostgreSQL (Transactional) + Neo4j (Knowledge Graph - Planned)
- **Real-time**: WebSocket (STOMP + SockJS)
- **Build Tool**: Maven
- **Architecture**: Clean Architecture (4 layers)

### Frontend
- **Framework**: React 19.2.0
- **Build Tool**: Vite 7.3.1
- **Styling**: Tailwind CSS 3.4.1
- **Animations**: Framer Motion
- **HTTP Client**: Axios
- **Routing**: React Router DOM 6

### DevOps (Planned)
- **Containerization**: Docker + Docker Compose
- **CI/CD**: GitHub Actions
- **Monitoring**: Prometheus + Grafana
- **Logging**: ELK Stack

---

## 📊 Project Statistics
```
Total Lines of Code:  8,700+
Backend Files:        34 Java files (2,364 lines)
Frontend Files:       33 JS/JSX files (6,364 lines)
API Endpoints:        50+
Database Tables:      4 main entities
Components:           20+ React components
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Node.js 18+ and npm
- PostgreSQL 14+
- Git

### Backend Setup
```bash
# Clone the repository
git clone https://github.com/AshharAhmadKhan/BrewAlgo.git
cd BrewAlgo/backend

# Install dependencies and run
mvn spring-boot:run
```

Backend will start at: `http://localhost:8080`

### Frontend Setup
```bash
# Navigate to frontend directory
cd ../frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

Frontend will start at: `http://localhost:5173`

### Database Setup
```sql
-- Create PostgreSQL database
CREATE DATABASE brewalgo;

-- Tables will be auto-created by Hibernate on first run
```

---

## 📁 Project Structure
```
brewalgo/
├── backend/                          # Spring Boot Application
│   ├── src/main/java/com/brewalgo/
│   │   ├── domain/                   # Domain Layer (Entities, Repositories)
│   │   ├── application/              # Application Layer (Services, DTOs)
│   │   ├── infrastructure/           # Infrastructure Layer (Implementations)
│   │   └── presentation/             # Presentation Layer (Controllers)
│   └── pom.xml
│
├── frontend/                         # React Application
│   ├── src/
│   │   ├── components/               # Reusable UI Components
│   │   ├── pages/                    # Route-level Pages
│   │   ├── services/                 # API Integration
│   │   ├── context/                  # React Context (State)
│   │   └── utils/                    # Helper Functions
│   └── package.json
│
├── docs/                             # Documentation
│   └── BACKEND_SUMMARY.md
│
└── README.md                         # You are here!
```

---

## 🎯 API Documentation

### Authentication
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User authentication

### Problems
- `GET /api/problems` - List all problems
- `GET /api/problems/{slug}` - Get problem details
- `GET /api/problems/difficulty/{level}` - Filter by difficulty
- `POST /api/submissions` - Submit solution

### Contests
- `GET /api/contests/live` - Get active contests
- `GET /api/contests/{id}/leaderboard` - Contest rankings
- `POST /api/contests/{id}/join` - Join contest

### Leaderboard
- `GET /api/leaderboard/global` - Global rankings

**Full API documentation**: See [docs/BACKEND_SUMMARY.md](docs/BACKEND_SUMMARY.md)

---

## 🏛️ Architecture

### Clean Architecture Layers
```
┌─────────────────────────────────────────┐
│        Presentation Layer               │  ← Controllers, DTOs
├─────────────────────────────────────────┤
│        Application Layer                │  ← Use Cases, Services
├─────────────────────────────────────────┤
│        Domain Layer                     │  ← Entities, Business Logic
├─────────────────────────────────────────┤
│        Infrastructure Layer             │  ← Database, External APIs
└─────────────────────────────────────────┘
```

**Benefits:**
- ✅ Testable business logic
- ✅ Framework independence
- ✅ Easy to maintain and scale
- ✅ Clear separation of concerns

---

## 🎨 Screenshots

### Home Page
![Home Page](https://via.placeholder.com/800x500/667eea/ffffff?text=Home+Page)

### Problem List
![Problem List](https://via.placeholder.com/800x500/764ba2/ffffff?text=Problem+List)

### Code Editor
![Code Editor](https://via.placeholder.com/800x500/f093fb/ffffff?text=Code+Editor)

### Leaderboard
![Leaderboard](https://via.placeholder.com/800x500/4facfe/ffffff?text=Leaderboard)

---

## 🗺️ Roadmap

### Phase 1 - MVP ✅ (Completed)
- [x] Backend API with Clean Architecture
- [x] Frontend UI with React
- [x] Authentication system
- [x] Problem browsing and submission
- [x] Basic leaderboard

### Phase 2 - Enhancement 🚧 (In Progress)
- [ ] Live contest system with real-time updates
- [ ] Code execution engine (Docker-based sandbox)
- [ ] Advanced analytics dashboard
- [ ] Neo4j knowledge graph for problem recommendations

### Phase 3 - Scale 📅 (Planned)
- [ ] Microservices architecture
- [ ] Redis caching layer
- [ ] Kubernetes deployment
- [ ] CI/CD pipeline
- [ ] Performance monitoring

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Developer

**Ashhar Ahmad Khan**  
Lead Software Architect & Full-Stack Developer

- 📧 Email: [itzashhar@gmail.com](mailto:itzashhar@gmail.com)
- 💼 GitHub: [@AshharAhmadKhan](https://github.com/AshharAhmadKhan)
- 🌐 Portfolio: [Coming Soon]

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- React team for the amazing UI library
- Tailwind CSS for utility-first styling
- All open-source contributors

---

## 📊 GitHub Stats

![GitHub stars](https://img.shields.io/github/stars/AshharAhmadKhan/BrewAlgo?style=social)
![GitHub forks](https://img.shields.io/github/forks/AshharAhmadKhan/BrewAlgo?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/AshharAhmadKhan/BrewAlgo?style=social)

---

<div align="center">

### ⭐ Star this repository if you find it helpful!

**Made with 💙 by Ashhar Ahmad Khan**

*© 2026 BrewAlgo. All rights reserved.*

</div>