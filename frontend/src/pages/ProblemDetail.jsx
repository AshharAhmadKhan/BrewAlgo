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
  const [showInputGuide, setShowInputGuide] = useState(false);

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
    setSubmission(null); // Clear previous submission
    
    try {
      const result = await problemService.submitSolution(user.id, problem.id, code, language);
      
      const executionResult = result.executionResult || {};
      const submissionData = result.submission || {};
      
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

  const getCodeTemplate = () => {
    if (language === 'JAVA') {
      return `import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Example: Reading array input
        String[] input = sc.nextLine().split(",");
        int target = sc.nextInt();
        
        // Your solution here
        
        // Example: Print output
        System.out.println("result");
    }
}`;
    } else if (language === 'PYTHON') {
      return `# Read input
input_line = input().split(',')
target = int(input())

# Your solution here

# Print output
print("result")`;
    }
    return '';
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
          <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4 mb-6">
            <h3 className="font-semibold mb-2">💡 Hints</h3>
            <p className="text-sm text-gray-700">{problem.hints}</p>
          </div>
        )}
      </div>

      {/* Code Editor */}
      <div className="bg-white rounded-lg shadow-lg p-6">
        <h2 className="text-2xl font-bold mb-4">Submit Solution</h2>
        
        {/* INPUT FORMAT GUIDE - NEW */}
        <div className="mb-4 bg-blue-50 border border-blue-200 rounded-lg overflow-hidden">
          <button
            onClick={() => setShowInputGuide(!showInputGuide)}
            className="w-full px-4 py-3 text-left font-semibold text-blue-800 hover:bg-blue-100 transition-colors flex items-center justify-between"
          >
            <span className="flex items-center">
              <span className="mr-2">📥</span>
              Input/Output Format Guide
            </span>
            <span className="text-sm">{showInputGuide ? '▲ Hide' : '▼ Show'}</span>
          </button>
          
          {showInputGuide && (
            <div className="px-4 py-3 bg-white border-t border-blue-200">
              <div className="space-y-3 text-sm">
                <div>
                  <p className="font-semibold text-gray-700 mb-1">📥 How to Read Input:</p>
                  <ul className="list-disc list-inside text-gray-600 space-y-1">
                    <li>Use <code className="bg-gray-100 px-1 rounded">Scanner</code> (Java) or <code className="bg-gray-100 px-1 rounded">input()</code> (Python)</li>
                    <li>For Two Sum: Line 1 is comma-separated array, Line 2 is target</li>
                    <li>Example: <code className="bg-gray-100 px-1 rounded">2,7,11,15</code> then <code className="bg-gray-100 px-1 rounded">9</code></li>
                  </ul>
                </div>
                
                <div>
                  <p className="font-semibold text-gray-700 mb-1">📤 How to Print Output:</p>
                  <ul className="list-disc list-inside text-gray-600 space-y-1">
                    <li>Print result exactly as expected (no extra text)</li>
                    <li>For Two Sum: Print indices as <code className="bg-gray-100 px-1 rounded">0,1</code></li>
                    <li>Use <code className="bg-gray-100 px-1 rounded">System.out.println()</code> (Java) or <code className="bg-gray-100 px-1 rounded">print()</code> (Python)</li>
                  </ul>
                </div>

                <div>
                  <p className="font-semibold text-gray-700 mb-2">💻 Code Template ({language}):</p>
                  <pre className="bg-gray-900 text-gray-100 p-3 rounded text-xs overflow-x-auto">
{getCodeTemplate()}
                  </pre>
                </div>

                <div className="bg-yellow-50 border border-yellow-200 rounded p-2">
                  <p className="text-xs text-yellow-800">
                    ⚠️ <strong>Important:</strong> Your class must be named <code className="bg-yellow-100 px-1 rounded">Solution</code> for Java submissions.
                  </p>
                </div>
              </div>
            </div>
          )}
        </div>
        
        <ErrorMessage message={error} onClose={() => setError('')} />

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Language
          </label>
          <select
            value={language}
            onChange={(e) => setLanguage(e.target.value)}
            className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none"
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
          className="w-full h-64 p-4 font-mono text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none"
          placeholder="Write your solution here..."
        />

        <div className="mt-4 flex items-center space-x-4">
          <Button onClick={handleSubmit} disabled={submitting || !code.trim()}>
            {submitting ? (
              <span className="flex items-center">
                <svg className="animate-spin -ml-1 mr-2 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Executing Code...
              </span>
            ) : (
              'Submit Solution'
            )}
          </Button>
          
          {submitting && (
            <span className="text-sm text-gray-600">
              ⏱️ This may take 3-5 seconds...
            </span>
          )}
        </div>

        {/* Submission Result - IMPROVED */}
        {submission && (
          <div className="mt-6 rounded-lg border-2 overflow-hidden" style={{
            borderColor: submission.status === 'ACCEPTED' ? '#10b981' : 
                        submission.status === 'WRONG_ANSWER' ? '#f59e0b' : 
                        submission.status === 'COMPILATION_ERROR' ? '#ef4444' : '#6b7280'
          }}>
            <div className={`px-4 py-3 font-semibold text-white ${
              submission.status === 'ACCEPTED' ? 'bg-green-500' : 
              submission.status === 'WRONG_ANSWER' ? 'bg-orange-500' : 
              submission.status === 'COMPILATION_ERROR' ? 'bg-red-500' : 'bg-gray-500'
            }`}>
              <span className="flex items-center">
                {submission.status === 'ACCEPTED' && '✅'}
                {submission.status === 'WRONG_ANSWER' && '❌'}
                {submission.status === 'COMPILATION_ERROR' && '🔨'}
                {submission.status === 'RUNTIME_ERROR' && '💥'}
                {submission.status === 'TIME_LIMIT_EXCEEDED' && '⏱️'}
                <span className="ml-2">Submission Result: {submission.status.replace(/_/g, ' ')}</span>
              </span>
            </div>
            
            <div className="bg-gray-50 p-4 space-y-3">
              {submission.passedTestCases !== undefined && submission.totalTestCases !== undefined && (
                <div className="flex items-center justify-between bg-white p-3 rounded border">
                  <span className="font-medium text-gray-700">Test Cases Passed</span>
                  <span className={`font-bold ${submission.passedTestCases === submission.totalTestCases ? 'text-green-600' : 'text-orange-600'}`}>
                    {submission.passedTestCases} / {submission.totalTestCases}
                  </span>
                </div>
              )}
              
              {submission.executionTimeMs && (
                <div className="flex items-center justify-between bg-white p-3 rounded border">
                  <span className="font-medium text-gray-700">Total Runtime</span>
                  <span className="text-gray-900 font-mono">{submission.executionTimeMs}ms</span>
                </div>
              )}
              
              {submission.memoryUsedKb > 0 && (
                <div className="flex items-center justify-between bg-white p-3 rounded border">
                  <span className="font-medium text-gray-700">Memory Used</span>
                  <span className="text-gray-900 font-mono">{submission.memoryUsedKb}KB</span>
                </div>
              )}
              
              {submission.scoreAwarded > 0 && (
                <div className="flex items-center justify-between bg-green-50 p-3 rounded border border-green-200">
                  <span className="font-medium text-green-700">Score Awarded</span>
                  <span className="text-green-600 font-bold text-lg">+{submission.scoreAwarded} pts</span>
                </div>
              )}
              
              {submission.output && submission.status === 'ACCEPTED' && (
                <div className="bg-white p-3 rounded border">
                  <p className="text-sm font-semibold text-gray-700 mb-2">✅ Output (Sample):</p>
                  <pre className="bg-green-50 p-2 rounded text-sm font-mono text-green-800 border border-green-200">
{submission.output}
                  </pre>
                </div>
              )}
              
              {submission.errorMessage && submission.status === 'WRONG_ANSWER' && (
                <div className="bg-white p-3 rounded border border-orange-200">
                  <p className="text-sm font-semibold text-orange-700 mb-2">❌ Expected Output:</p>
                  <pre className="bg-orange-50 p-2 rounded text-sm font-mono text-orange-800">
{submission.errorMessage.replace('Expected: ', '')}
                  </pre>
                  <p className="text-xs text-gray-600 mt-2">💡 Your output doesn't match the expected result for one or more test cases.</p>
                </div>
              )}
              
              {submission.errorMessage && submission.status === 'COMPILATION_ERROR' && (
                <div className="bg-white p-3 rounded border border-red-200">
                  <p className="text-sm font-semibold text-red-700 mb-2">🔨 Compilation Error:</p>
                  <pre className="bg-red-50 p-3 rounded text-xs font-mono text-red-800 overflow-x-auto max-h-40 overflow-y-auto">
{submission.errorMessage}
                  </pre>
                  <div className="mt-3 bg-yellow-50 border border-yellow-200 rounded p-2">
                    <p className="text-xs font-semibold text-yellow-800 mb-1">💡 Common Fixes:</p>
                    <ul className="text-xs text-yellow-700 space-y-1 ml-4 list-disc">
                      <li>Ensure class name is <code className="bg-yellow-100 px-1 rounded">Solution</code></li>
                      <li>Check for missing semicolons or brackets</li>
                      <li>Verify all imports are correct</li>
                      <li>Make sure method signatures match</li>
                    </ul>
                  </div>
                </div>
              )}
              
              {submission.errorMessage && submission.status === 'RUNTIME_ERROR' && (
                <div className="bg-white p-3 rounded border border-red-200">
                  <p className="text-sm font-semibold text-red-700 mb-2">💥 Runtime Error:</p>
                  <pre className="bg-red-50 p-3 rounded text-xs font-mono text-red-800 overflow-x-auto max-h-40 overflow-y-auto">
{submission.errorMessage}
                  </pre>
                  <div className="mt-3 bg-yellow-50 border border-yellow-200 rounded p-2">
                    <p className="text-xs font-semibold text-yellow-800 mb-1">💡 Common Causes:</p>
                    <ul className="text-xs text-yellow-700 space-y-1 ml-4 list-disc">
                      <li>Array index out of bounds</li>
                      <li>Null pointer exception</li>
                      <li>Division by zero</li>
                      <li>Incorrect input parsing</li>
                    </ul>
                  </div>
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