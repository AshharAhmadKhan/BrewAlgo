import { useState, useEffect } from 'react';
import { authService } from '../services/authService';
import LoadingSkeleton from '../components/common/LoadingSkeleton';
import ErrorMessage from '../components/common/ErrorMessage';
import { useToast } from '../context/ToastContext';

const Leaderboard = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const { showToast } = useToast();

  useEffect(() => {
    fetchLeaderboard();
  }, []);

  const fetchLeaderboard = async () => {
    setLoading(true);
    try {
      // This would call leaderboard API, using user service as placeholder
      const response = await fetch('http://localhost:8081/api/v1/users/top?limit=50');
      const data = await response.json();
      setUsers(data);
    } catch (err) {
      const errorMsg = 'Failed to load leaderboard.';
      setError(errorMsg);
      showToast(errorMsg, 'error');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSkeleton count={10} type="table" />;

  return (
    <div className="max-w-4xl mx-auto animate-fade-in">
      <h1 className="text-4xl font-bold mb-8">Global Leaderboard</h1>

      <ErrorMessage message={error} onClose={() => setError('')} />

      <div className="bg-white rounded-lg shadow overflow-hidden animate-scale-in">
        <table className="min-w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Rank</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">User</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Rating</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Problems Solved</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-200">
            {users.map((user, index) => {
              const rank = index + 1;
              const getRankBadge = () => {
                if (rank === 1) return '🥇';
                if (rank === 2) return '🥈';
                if (rank === 3) return '🥉';
                return null;
              };
              
              return (
                <tr 
                  key={user.id} 
                  className={`hover:bg-gray-50 transition-colors animate-slide-in ${
                    rank <= 3 ? 'bg-yellow-50' : ''
                  }`}
                  style={{ animationDelay: `${index * 50}ms` }}
                >
                  <td className="px-6 py-4">
                    <div className="flex items-center space-x-2">
                      <span className="font-semibold text-lg">{rank}</span>
                      {getRankBadge() && <span className="text-2xl">{getRankBadge()}</span>}
                    </div>
                  </td>
                  <td className="px-6 py-4">
                    <div className="flex items-center space-x-3">
                      <div className="w-10 h-10 bg-blue-600 rounded-full flex items-center justify-center text-white font-bold">
                        {user.username[0].toUpperCase()}
                      </div>
                      <span className="font-medium">{user.username}</span>
                    </div>
                  </td>
                  <td className="px-6 py-4">
                    <span className={`font-bold text-lg ${
                      rank === 1 ? 'text-yellow-600' :
                      rank === 2 ? 'text-gray-500' :
                      rank === 3 ? 'text-orange-600' :
                      'text-blue-600'
                    }`}>
                      {user.rating}
                    </span>
                  </td>
                  <td className="px-6 py-4">
                    <span className="font-semibold">{user.problemsSolved}</span>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Leaderboard;