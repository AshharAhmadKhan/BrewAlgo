import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { problemService } from '../services/problemService';
import { useAuth } from '../context/AuthContext';
import { LANGUAGES, DIFFICULTY_COLORS, STATUS_COLORS } from '../utils/constants';
import Loading from '../components/common/Loading';
import ErrorMessage from '../components/common/ErrorMessage';
import Button from '../components/common/Button';

const ProblemDetail = () => {
  const { slug } = useParams();
  const { user } = useAuth();
  const [problem, setProblem] = useState(null);
  const [code, setCode] = useState('');
  const [language, setLanguage] = useState(LANGUAGES.JAVA);
  const [submission, setSubmission] = useState(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchProblem();
  }, [slug]);

  const fetchProblem = async () => {
    setLoading(true);
    try {
      const data = await problemService.getProblemBySlug(slug);
      setProblem(data);
    } catch (err) {
      setError('Failed to load problem.');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async () => {
    if (!code.trim()) {
      setError('Please enter your code');
      return;
    }

    setSubmitting(true);
    setError('');
    try {
      const result = await problemService.submitSolution(user.id, problem.id, code, language);
      
      // FIX: Extract executionResult from the response
      const executionResult = result.executionResult || {};
      const submissionData = result.submission || {};
      
      // Combine both for display
      setSubmission({
        status: executionResult.status || submissionData.status,
        executionTimeMs: executionResult.executionTimeMs,
        memoryUsedKb: executionResult.memoryUsedKb,
        scoreAwarded: submissionData.scoreAwarded,
        errorMessage: executionResult.errorMessage,
        output: executionResult.output,
        passedTestCases: executionResult.passedTestCases,
        totalTestCases: executionResult.totalTestCases
      });
    } catch (err) {
      setError('Failed to submit solution.');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <Loading />;
  if (!problem) return <ErrorMessage message="Problem not found" />;

  return (
    <div className="max-w-6xl mx-auto">
      <div className="bg-white rounded-lg shadow-lg p-8 mb-6">
        <div className="flex items-center justify-between mb-4">
          <h1 className="text-3xl font-bold">{problem.title}</h1>
          <span className={`px-4 py-2 rounded-full font-semibold ${DIFFICULTY_COLORS[problem.difficulty]}`}>
            {problem.difficulty}
          </span>
        </div>

        <div className="flex items-center space-x-6 text-sm text-gray-600 mb-6">
          <span>Score: {problem.baseScore} pts</span>
          <span>Acceptance: {problem.acceptanceRate}%</span>
          <span>Submissions: {problem.totalSubmissions}</span>
        </div>

        <div className="prose max-w-none mb-6">
          <h3 className="text-xl font-semibold mb-2">Description</h3>
          <p className="text-gray-700 whitespace-pre-wrap">{problem.description}</p>
        </div>

        {problem.hints && (
          <div className="bg-yellow-50 border border-yellow-200 rounded p-4 mb-6">
            <h3 className="font-semibold mb-2">💡 Hints</h3>
            <p className="text-sm text-gray-700">{problem.hints}</p>
          </div>
        )}
      </div>

      {/* Code Editor */}
      <div className="bg-white rounded-lg shadow-lg p-6">
        <h2 className="text-2xl font-bold mb-4">Submit Solution</h2>
        
        <ErrorMessage message={error} onClose={() => setError('')} />

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Language
          </label>
          <select
            value={language}
            onChange={(e) => setLanguage(e.target.value)}
            className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          >
            {Object.values(LANGUAGES).map((lang) => (
              <option key={lang} value={lang}>
                {lang}
              </option>
            ))}
          </select>
        </div>

        <textarea
          value={code}
          onChange={(e) => setCode(e.target.value)}
          className="w-full h-64 p-4 font-mono text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
          placeholder="Write your solution here..."
        />

        <div className="mt-4">
          <Button onClick={handleSubmit} disabled={submitting}>
            {submitting ? 'Submitting...' : 'Submit Solution'}
          </Button>
        </div>

        {/* Submission Result */}
        {submission && (
          <div className="mt-6 p-4 bg-gray-50 rounded-lg">
            <h3 className="font-semibold mb-2">Submission Result</h3>
            <div className="space-y-2">
              <div className="flex items-center space-x-2">
                <span className="text-gray-600">Status:</span>
                <span className={`px-3 py-1 rounded-full text-sm font-semibold ${STATUS_COLORS[submission.status] || 'bg-gray-200 text-gray-800'}`}>
                  {submission.status}
                </span>
              </div>
              
              {submission.passedTestCases !== undefined && submission.totalTestCases !== undefined && (
                <p className="text-gray-600">
                  Test Cases: {submission.passedTestCases}/{submission.totalTestCases} passed
                </p>
              )}
              
              {submission.executionTimeMs && (
                <p className="text-gray-600">Execution Time: {submission.executionTimeMs}ms</p>
              )}
              
              {submission.memoryUsedKb && (
                <p className="text-gray-600">Memory Used: {submission.memoryUsedKb}KB</p>
              )}
              
              {submission.scoreAwarded > 0 && (
                <p className="text-green-600 font-semibold">Score: {submission.scoreAwarded} pts</p>
              )}
              
              {submission.output && (
                <div className="mt-2">
                  <p className="text-gray-600 font-semibold">Output:</p>
                  <pre className="bg-white p-2 rounded border text-sm">{submission.output}</pre>
                </div>
              )}
              
              {submission.errorMessage && (
                <div className="mt-2">
                  <p className="text-red-600 font-semibold">Error:</p>
                  <pre className="bg-red-50 p-2 rounded border border-red-200 text-sm text-red-800">
                    {submission.errorMessage}
                  </pre>
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default ProblemDetail;