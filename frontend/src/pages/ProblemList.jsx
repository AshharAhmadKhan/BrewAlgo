import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { problemService } from '../services/problemService';
import { DIFFICULTY, DIFFICULTY_COLORS } from '../utils/constants';
import Loading from '../components/common/Loading';
import ErrorMessage from '../components/common/ErrorMessage';

const ProblemList = () => {
  const [problems, setProblems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [filter, setFilter] = useState('ALL');

  useEffect(() => {
    fetchProblems();
  }, [filter]);

  const fetchProblems = async () => {
    setLoading(true);
    setError('');
    try {
      let data;
      if (filter === 'ALL') {
        data = await problemService.getAllProblems();
      } else {
        data = await problemService.getProblemsByDifficulty(filter);
      }
      setProblems(data);
    } catch (err) {
      setError('Failed to load problems. Please try again.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loading />;

  return (
    <div className="max-w-6xl mx-auto">
      <div className="mb-8">
        <h1 className="text-4xl font-bold mb-4">Problems</h1>
        
        {/* Filters */}
        <div className="flex space-x-2 mb-6">
          {['ALL', DIFFICULTY.EASY, DIFFICULTY.MEDIUM, DIFFICULTY.HARD].map((diff) => (
            <button
              key={diff}
              onClick={() => setFilter(diff)}
              className={`px-4 py-2 rounded-lg font-semibold transition ${
                filter === diff
                  ? 'bg-blue-600 text-white'
                  : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
              }`}
            >
              {diff}
            </button>
          ))}
        </div>

        <ErrorMessage message={error} onClose={() => setError('')} />
      </div>

      {/* Problems Table */}
      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Title
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Difficulty
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Acceptance
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Score
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {problems.length === 0 ? (
              <tr>
                <td colSpan="4" className="px-6 py-4 text-center text-gray-500">
                  No problems found
                </td>
              </tr>
            ) : (
              problems.map((problem) => (
                <tr key={problem.id} className="hover:bg-gray-50 transition">
                  <td className="px-6 py-4">
                    <Link
                      to={`/problems/${problem.slug}`}
                      className="text-blue-600 hover:text-blue-800 font-medium"
                    >
                      {problem.title}
                    </Link>
                  </td>
                  <td className="px-6 py-4">
                    <span className={`px-3 py-1 rounded-full text-sm font-semibold ${DIFFICULTY_COLORS[problem.difficulty]}`}>
                      {problem.difficulty}
                    </span>
                  </td>
                  <td className="px-6 py-4 text-gray-600">
                    {problem.acceptanceRate}%
                  </td>
                  <td className="px-6 py-4 text-gray-600">
                    {problem.baseScore} pts
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ProblemList;