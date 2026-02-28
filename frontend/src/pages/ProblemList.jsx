import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { problemService } from '../services/problemService';
import { DIFFICULTY, DIFFICULTY_COLORS, API_BASE_URL } from '../utils/constants';
import LoadingSkeleton from '../components/common/LoadingSkeleton';
import ErrorMessage from '../components/common/ErrorMessage';
import { useToast } from '../context/ToastContext';

const ProblemList = () => {
  const [problems, setProblems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [filter, setFilter] = useState('ALL');
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalItems, setTotalItems] = useState(0);
  const [searchQuery, setSearchQuery] = useState('');
  const [sortBy, setSortBy] = useState('title');
  const [difficultyCounts, setDifficultyCounts] = useState({ EASY: 0, MEDIUM: 0, HARD: 0 });
  const { showToast } = useToast();

  useEffect(() => {
    fetchProblems();
  }, [filter, currentPage, sortBy]);

  useEffect(() => {
    // Reset to page 0 when search query changes
    if (currentPage !== 0) {
      setCurrentPage(0);
    } else {
      fetchProblems();
    }
  }, [searchQuery]);

  const fetchProblems = async () => {
    setLoading(true);
    setError('');
    try {
      let data;
      if (filter === 'ALL') {
        // Use pagination API
        const response = await fetch(`${API_BASE_URL}/problems?page=${currentPage}&size=20`);
        const result = await response.json();
        let problemsList = result.problems || [];
        
        // Calculate difficulty counts
        const counts = { EASY: 0, MEDIUM: 0, HARD: 0 };
        problemsList.forEach(p => {
          if (counts[p.difficulty] !== undefined) counts[p.difficulty]++;
        });
        setDifficultyCounts(counts);
        
        // Apply client-side search filter
        if (searchQuery.trim()) {
          problemsList = problemsList.filter(p => 
            p.title.toLowerCase().includes(searchQuery.toLowerCase())
          );
        }
        
        // Apply sorting
        problemsList = sortProblems(problemsList, sortBy);
        
        setProblems(problemsList);
        setTotalPages(result.totalPages || 0);
        setTotalItems(result.totalItems || 0);
      } else {
        data = await problemService.getProblemsByDifficulty(filter);
        
        // Apply client-side search filter
        if (searchQuery.trim()) {
          data = data.filter(p => 
            p.title.toLowerCase().includes(searchQuery.toLowerCase())
          );
        }
        
        // Apply sorting
        data = sortProblems(data, sortBy);
        
        setProblems(data);
        setTotalPages(1);
        setTotalItems(data.length);
      }
    } catch (err) {
      const errorMsg = 'Failed to load problems. Please try again.';
      setError(errorMsg);
      showToast(errorMsg, 'error');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const sortProblems = (problemsList, sortOption) => {
    const sorted = [...problemsList];
    switch (sortOption) {
      case 'title':
        return sorted.sort((a, b) => a.title.localeCompare(b.title));
      case 'difficulty':
        const diffOrder = { EASY: 1, MEDIUM: 2, HARD: 3 };
        return sorted.sort((a, b) => diffOrder[a.difficulty] - diffOrder[b.difficulty]);
      case 'acceptance':
        return sorted.sort((a, b) => b.acceptanceRate - a.acceptanceRate);
      case 'score':
        return sorted.sort((a, b) => b.baseScore - a.baseScore);
      default:
        return sorted;
    }
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  if (loading) return <LoadingSkeleton count={5} type="table" />;

  return (
    <div className="max-w-6xl mx-auto animate-fade-in">
      <div className="mb-8">
        <h1 className="text-4xl font-bold mb-4">Problems</h1>
        
        {/* Search Bar */}
        <div className="mb-4">
          <div className="relative">
            <input
              type="text"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              placeholder="Search problems by title..."
              className="w-full px-4 py-3 pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none"
            />
            <svg 
              className="absolute left-3 top-3.5 h-5 w-5 text-gray-400" 
              fill="none" 
              stroke="currentColor" 
              viewBox="0 0 24 24"
            >
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
            {searchQuery && (
              <button
                onClick={() => setSearchQuery('')}
                className="absolute right-3 top-3 text-gray-400 hover:text-gray-600"
              >
                <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            )}
          </div>
        </div>
        
        {/* Filters */}
        <div className="flex flex-wrap items-center justify-between gap-4 mb-6">
          <div className="flex space-x-2">
            <button
              onClick={() => setFilter('ALL')}
              className={`px-4 py-2 rounded-lg font-semibold transition ${
                filter === 'ALL'
                  ? 'bg-blue-600 text-white'
                  : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
              }`}
            >
              All
            </button>
            {[DIFFICULTY.EASY, DIFFICULTY.MEDIUM, DIFFICULTY.HARD].map((diff) => (
              <button
                key={diff}
                onClick={() => setFilter(diff)}
                className={`px-4 py-2 rounded-lg font-semibold transition relative ${
                  filter === diff
                    ? 'bg-blue-600 text-white'
                    : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
                }`}
              >
                {diff}
                {filter === 'ALL' && difficultyCounts[diff] > 0 && (
                  <span className="ml-2 px-2 py-0.5 text-xs rounded-full bg-blue-100 text-blue-800">
                    {difficultyCounts[diff]}
                  </span>
                )}
              </button>
            ))}
          </div>
          
          <div className="flex items-center space-x-2">
            <label className="text-sm font-medium text-gray-700">Sort by:</label>
            <select
              value={sortBy}
              onChange={(e) => setSortBy(e.target.value)}
              className="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
            >
              <option value="title">Title</option>
              <option value="difficulty">Difficulty</option>
              <option value="acceptance">Acceptance Rate</option>
              <option value="score">Score</option>
            </select>
          </div>
        </div>

        <ErrorMessage message={error} onClose={() => setError('')} />
      </div>

      {/* Problems Table */}
      <div className="bg-white rounded-lg shadow overflow-hidden animate-scale-in">
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

      {/* Pagination */}
      {totalPages > 1 && (
        <div className="mt-6 flex items-center justify-between">
          <div className="text-sm text-gray-600">
            Showing {problems.length} of {totalItems} problems
          </div>
          <div className="flex items-center space-x-2">
            <button
              onClick={() => handlePageChange(currentPage - 1)}
              disabled={currentPage === 0}
              className="px-4 py-2 border rounded-lg disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
            >
              Previous
            </button>
            
            {[...Array(Math.min(5, totalPages))].map((_, idx) => {
              const pageNum = currentPage < 3 ? idx : currentPage - 2 + idx;
              if (pageNum >= totalPages) return null;
              return (
                <button
                  key={pageNum}
                  onClick={() => handlePageChange(pageNum)}
                  className={`px-4 py-2 border rounded-lg ${
                    currentPage === pageNum
                      ? 'bg-blue-600 text-white'
                      : 'hover:bg-gray-50'
                  }`}
                >
                  {pageNum + 1}
                </button>
              );
            })}
            
            <button
              onClick={() => handlePageChange(currentPage + 1)}
              disabled={currentPage >= totalPages - 1}
              className="px-4 py-2 border rounded-lg disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
            >
              Next
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProblemList;