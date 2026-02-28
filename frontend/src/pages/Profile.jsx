import { useAuth } from '../context/AuthContext';
import { useState, useEffect } from 'react';
import { API_BASE_URL } from '../utils/constants';
import LoadingSkeleton from '../components/common/LoadingSkeleton';
import SkillRadar from '../components/effects/SkillRadar';

const Profile = () => {
  const { user } = useAuth();
  const [submissions, setSubmissions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('overview');

  useEffect(() => {
    if (user) {
      fetchSubmissions();
    }
  }, [user]);

  const fetchSubmissions = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/submissions/user/${user.id}?page=0&size=10`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      const data = await response.json();
      setSubmissions(data.submissions || []);
    } catch (err) {
      console.error('Failed to fetch submissions:', err);
    } finally {
      setLoading(false);
    }
  };

  if (!user) return null;

  const acceptedSubmissions = submissions.filter(s => s.status === 'ACCEPTED');
  const totalScore = acceptedSubmissions.reduce((sum, s) => sum + (s.scoreAwarded || 0), 0);
  const avgExecutionTime = acceptedSubmissions.length > 0
    ? Math.round(acceptedSubmissions.reduce((sum, s) => sum + (s.executionTimeMs || 0), 0) / acceptedSubmissions.length)
    : 0;

  return (
    <div className="max-w-6xl mx-auto animate-fade-in">
      <div className="bg-white rounded-lg shadow-lg p-8 mb-6 animate-scale-in">
        <div className="flex items-center space-x-6 mb-8">
          <div className="w-24 h-24 bg-blue-600 rounded-full flex items-center justify-center text-white text-4xl font-bold">
            {user.username[0].toUpperCase()}
          </div>
          <div>
            <h1 className="text-3xl font-bold">{user.username}</h1>
            <p className="text-gray-600">{user.email}</p>
            <div className="mt-2 inline-flex items-center px-3 py-1 rounded-full text-sm font-semibold bg-purple-100 text-purple-800">
              {user.role}
            </div>
          </div>
        </div>

        <div className="grid md:grid-cols-4 gap-4">
          <div className="bg-blue-50 p-4 rounded-lg">
            <div className="text-2xl font-bold text-blue-600 mb-1">{user.rating}</div>
            <div className="text-sm text-gray-600">Rating</div>
          </div>
          <div className="bg-green-50 p-4 rounded-lg">
            <div className="text-2xl font-bold text-green-600 mb-1">{user.problemsSolved}</div>
            <div className="text-sm text-gray-600">Problems Solved</div>
          </div>
          <div className="bg-orange-50 p-4 rounded-lg">
            <div className="text-2xl font-bold text-orange-600 mb-1">{totalScore}</div>
            <div className="text-sm text-gray-600">Total Score</div>
          </div>
          <div className="bg-purple-50 p-4 rounded-lg">
            <div className="text-2xl font-bold text-purple-600 mb-1">{submissions.length}</div>
            <div className="text-sm text-gray-600">Total Submissions</div>
          </div>
        </div>
      </div>

      {/* Tabs */}
      <div className="bg-white rounded-lg shadow-lg overflow-hidden animate-scale-in" style={{ animationDelay: '0.1s' }}>
        <div className="flex border-b">
          <button
            onClick={() => setActiveTab('overview')}
            className={`flex-1 px-6 py-4 font-semibold transition ${
              activeTab === 'overview'
                ? 'bg-blue-50 text-blue-600 border-b-2 border-blue-600'
                : 'text-gray-600 hover:bg-gray-50'
            }`}
          >
            Overview
          </button>
          <button
            onClick={() => setActiveTab('submissions')}
            className={`flex-1 px-6 py-4 font-semibold transition ${
              activeTab === 'submissions'
                ? 'bg-blue-50 text-blue-600 border-b-2 border-blue-600'
                : 'text-gray-600 hover:bg-gray-50'
            }`}
          >
            Recent Submissions
          </button>
        </div>

        <div className="p-6">
          {activeTab === 'overview' && (
            <div className="space-y-6">
              <div>
                <h2 className="text-xl font-bold mb-4">Skill Distribution</h2>
                <div className="flex justify-center">
                  <SkillRadar 
                    easy={Math.floor(user.problemsSolved * 0.4)} 
                    medium={Math.floor(user.problemsSolved * 0.35)} 
                    hard={Math.floor(user.problemsSolved * 0.25)} 
                  />
                </div>
              </div>

              <div>
                <h2 className="text-xl font-bold mb-4">Submission Statistics</h2>
                <div className="grid md:grid-cols-3 gap-4">
                  <div className="border rounded-lg p-4">
                    <div className="text-lg font-bold text-green-600">{acceptedSubmissions.length}</div>
                    <div className="text-sm text-gray-600">Accepted</div>
                  </div>
                  <div className="border rounded-lg p-4">
                    <div className="text-lg font-bold text-red-600">{submissions.length - acceptedSubmissions.length}</div>
                    <div className="text-sm text-gray-600">Failed</div>
                  </div>
                  <div className="border rounded-lg p-4">
                    <div className="text-lg font-bold text-blue-600">{avgExecutionTime}ms</div>
                    <div className="text-sm text-gray-600">Avg Runtime</div>
                  </div>
                </div>
              </div>

              <div>
                <h2 className="text-xl font-bold mb-4">Account Information</h2>
                <div className="space-y-3">
                  <div className="flex justify-between py-2 border-b">
                    <span className="text-gray-600">Member since:</span>
                    <span className="font-semibold">{new Date(user.createdAt).toLocaleDateString()}</span>
                  </div>
                  <div className="flex justify-between py-2 border-b">
                    <span className="text-gray-600">Last login:</span>
                    <span className="font-semibold">{new Date(user.lastLoginAt).toLocaleDateString()}</span>
                  </div>
                  <div className="flex justify-between py-2">
                    <span className="text-gray-600">Acceptance Rate:</span>
                    <span className="font-semibold text-green-600">
                      {submissions.length > 0 ? Math.round((acceptedSubmissions.length / submissions.length) * 100) : 0}%
                    </span>
                  </div>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'submissions' && (
            <div>
              <h2 className="text-xl font-bold mb-4">Recent Submissions</h2>
              {loading ? (
                <LoadingSkeleton count={5} type="table" />
              ) : submissions.length === 0 ? (
                <div className="text-center py-8 text-gray-500">
                  No submissions yet. Start solving problems!
                </div>
              ) : (
                <div className="overflow-x-auto">
                  <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                      <tr>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Problem</th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Language</th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Runtime</th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Score</th>
                        <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Date</th>
                      </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                      {submissions.map((sub) => (
                        <tr key={sub.id} className="hover:bg-gray-50">
                          <td className="px-4 py-3 text-sm font-medium text-gray-900">
                            Problem #{sub.problemId}
                          </td>
                          <td className="px-4 py-3">
                            <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                              sub.status === 'ACCEPTED' 
                                ? 'bg-green-100 text-green-800' 
                                : 'bg-red-100 text-red-800'
                            }`}>
                              {sub.status}
                            </span>
                          </td>
                          <td className="px-4 py-3 text-sm text-gray-600">{sub.language}</td>
                          <td className="px-4 py-3 text-sm text-gray-600">
                            {sub.executionTimeMs ? `${sub.executionTimeMs}ms` : '-'}
                          </td>
                          <td className="px-4 py-3 text-sm font-semibold text-green-600">
                            {sub.scoreAwarded > 0 ? `+${sub.scoreAwarded}` : '-'}
                          </td>
                          <td className="px-4 py-3 text-sm text-gray-600">
                            {new Date(sub.submittedAt).toLocaleDateString()}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Profile;