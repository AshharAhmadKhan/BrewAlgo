import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import Navbar from './components/common/Navbar';
import Footer from './components/common/Footer';
import Loading from './components/common/Loading';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import ProblemList from './pages/ProblemList';
import ProblemDetail from './pages/ProblemDetail';
import ContestList from './pages/ContestList';
import Leaderboard from './pages/Leaderboard';
import Profile from './pages/Profile';

// Protected Route Component
const ProtectedRoute = ({ children }) => {
  const { isAuthenticated, loading } = useAuth();
  
  if (loading) {
    return <Loading />;
  }
  
  return isAuthenticated ? children : <Navigate to="/login" />;
};

function App() {
  return (
    <div className="min-h-screen flex flex-col bg-gray-50">
      <Navbar />
      <main className="flex-grow container mx-auto px-4 py-8">
        <Routes>
          {/* Public Routes */}
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          
          {/* Protected Routes */}
          <Route
            path="/problems"
            element={
              <ProtectedRoute>
                <ProblemList />
              </ProtectedRoute>
            }
          />
          <Route
            path="/problems/:slug"
            element={
              <ProtectedRoute>
                <ProblemDetail />
              </ProtectedRoute>
            }
          />
          <Route
            path="/contests"
            element={
              <ProtectedRoute>
                <ContestList />
              </ProtectedRoute>
            }
          />
          <Route path="/leaderboard" element={<Leaderboard />} />
          <Route
            path="/profile"
            element={
              <ProtectedRoute>
                <Profile />
              </ProtectedRoute>
            }
          />
          
          {/* Catch all - redirect to home */}
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </main>
      <Footer />
    </div>
  );
}

export default App;