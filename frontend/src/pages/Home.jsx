import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import Button from '../components/common/Button';

const Home = () => {
  const { isAuthenticated } = useAuth();

  return (
    <div className="max-w-6xl mx-auto">
      {/* Hero Section */}
      <div className="text-center py-16">
        <h1 className="text-5xl font-bold text-gray-900 mb-4">
          Welcome to <span className="text-blue-600">BrewAlgo</span>
        </h1>
        <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
          Master algorithmic problem-solving with our interactive platform. 
          Compete in contests, track your progress, and climb the leaderboard!
        </p>
        {!isAuthenticated && (
          <div className="flex justify-center space-x-4">
            <Link to="/register">
              <Button size="lg">Get Started</Button>
            </Link>
            <Link to="/login">
              <Button variant="outline" size="lg">Sign In</Button>
            </Link>
          </div>
        )}
      </div>

      {/* Features Section */}
      <div className="grid md:grid-cols-3 gap-8 py-12">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="text-3xl mb-4">💻</div>
          <h3 className="text-xl font-semibold mb-2">Practice Problems</h3>
          <p className="text-gray-600">
            Solve algorithmic challenges across multiple difficulty levels 
            with detailed explanations and hints.
          </p>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="text-3xl mb-4">🏆</div>
          <h3 className="text-xl font-semibold mb-2">Live Contests</h3>
          <p className="text-gray-600">
            Compete against others in real-time contests and improve 
            your competitive programming skills.
          </p>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="text-3xl mb-4">📊</div>
          <h3 className="text-xl font-semibold mb-2">Track Progress</h3>
          <p className="text-gray-600">
            Monitor your performance with detailed analytics, 
            ratings, and leaderboard rankings.
          </p>
        </div>
      </div>

      {/* Stats Section */}
      <div className="bg-gradient-to-r from-blue-600 to-purple-600 text-white rounded-lg p-8 my-12">
        <div className="grid md:grid-cols-4 gap-8 text-center">
          <div>
            <div className="text-4xl font-bold mb-2">500+</div>
            <div className="text-blue-100">Problems</div>
          </div>
          <div>
            <div className="text-4xl font-bold mb-2">50K+</div>
            <div className="text-blue-100">Users</div>
          </div>
          <div>
            <div className="text-4xl font-bold mb-2">100+</div>
            <div className="text-blue-100">Contests</div>
          </div>
          <div>
            <div className="text-4xl font-bold mb-2">24/7</div>
            <div className="text-blue-100">Support</div>
          </div>
        </div>
      </div>

      {/* CTA Section */}
      {isAuthenticated && (
        <div className="text-center py-12">
          <h2 className="text-3xl font-bold mb-4">Ready to code?</h2>
          <p className="text-gray-600 mb-6">Start solving problems or join a contest now!</p>
          <div className="flex justify-center space-x-4">
            <Link to="/problems">
              <Button size="lg">Browse Problems</Button>
            </Link>
            <Link to="/contests">
              <Button variant="outline" size="lg">View Contests</Button>
            </Link>
          </div>
        </div>
      )}
    </div>
  );
};

export default Home;