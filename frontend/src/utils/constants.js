// API Configuration
export const API_BASE_URL = 'http://localhost:8080/api';
export const WS_BASE_URL = 'ws://localhost:8080/ws';

// API Endpoints
export const API_ENDPOINTS = {
  // Auth
  REGISTER: '/users/register',
  LOGIN: '/users/login',
  
  // Users
  USERS: '/users',
  USER_BY_ID: (id) => `/users/${id}`,
  USER_BY_USERNAME: (username) => `/users/username/${username}`,
  TOP_USERS: '/users/top',
  
  // Problems
  PROBLEMS: '/problems',
  PROBLEM_BY_ID: (id) => `/problems/${id}`,
  PROBLEM_BY_SLUG: (slug) => `/problems/slug/${slug}`,
  PROBLEMS_BY_DIFFICULTY: (difficulty) => `/problems/difficulty/${difficulty}`,
  RECOMMENDED_PROBLEMS: '/problems/recommended',
  
  // Submissions
  SUBMISSIONS: '/submissions',
  SUBMISSION_BY_ID: (id) => `/submissions/${id}`,
  USER_SUBMISSIONS: (userId) => `/submissions/user/${userId}`,
  PROBLEM_SUBMISSIONS: (problemId) => `/submissions/problem/${problemId}`,
  USER_ACCEPTED: (userId) => `/submissions/user/${userId}/accepted`,
  CHECK_SOLVED: (userId, problemId) => `/submissions/user/${userId}/problem/${problemId}/solved`,
  
  // Contests
  CONTESTS: '/contests',
  CONTEST_BY_ID: (id) => `/contests/${id}`,
  UPCOMING_CONTESTS: '/contests/upcoming',
  LIVE_CONTESTS: '/contests/live',
  COMPLETED_CONTESTS: '/contests/completed',
  CONTEST_LEADERBOARD: (id) => `/contests/${id}/leaderboard`,
  JOIN_CONTEST: (id) => `/contests/${id}/join`,
  
  // Leaderboard
  GLOBAL_LEADERBOARD: '/leaderboard/global',
  CONTEST_LEADERBOARD_ROUTE: (id) => `/leaderboard/contest/${id}`,
};

// Difficulty Levels
export const DIFFICULTY = {
  EASY: 'EASY',
  MEDIUM: 'MEDIUM',
  HARD: 'HARD',
};

export const DIFFICULTY_COLORS = {
  EASY: 'text-green-600 bg-green-50',
  MEDIUM: 'text-yellow-600 bg-yellow-50',
  HARD: 'text-red-600 bg-red-50',
};

// Submission Status
export const SUBMISSION_STATUS = {
  PENDING: 'PENDING',
  ACCEPTED: 'ACCEPTED',
  WRONG_ANSWER: 'WRONG_ANSWER',
  TIME_LIMIT_EXCEEDED: 'TIME_LIMIT_EXCEEDED',
  MEMORY_LIMIT_EXCEEDED: 'MEMORY_LIMIT_EXCEEDED',
  RUNTIME_ERROR: 'RUNTIME_ERROR',
  COMPILATION_ERROR: 'COMPILATION_ERROR',
};

export const STATUS_COLORS = {
  PENDING: 'text-blue-600 bg-blue-50',
  ACCEPTED: 'text-green-600 bg-green-50',
  WRONG_ANSWER: 'text-red-600 bg-red-50',
  TIME_LIMIT_EXCEEDED: 'text-orange-600 bg-orange-50',
  MEMORY_LIMIT_EXCEEDED: 'text-purple-600 bg-purple-50',
  RUNTIME_ERROR: 'text-red-700 bg-red-100',
  COMPILATION_ERROR: 'text-gray-600 bg-gray-50',
};

// Programming Languages
export const LANGUAGES = {
  JAVA: 'JAVA',
  PYTHON: 'PYTHON',
  JAVASCRIPT: 'JAVASCRIPT',
  CPP: 'CPP',
};

// Contest Status
export const CONTEST_STATUS = {
  UPCOMING: 'UPCOMING',
  LIVE: 'LIVE',
  COMPLETED: 'COMPLETED',
  CANCELLED: 'CANCELLED',
};

// Local Storage Keys
export const STORAGE_KEYS = {
  TOKEN: 'brewalgo_token',
  USER: 'brewalgo_user',
};