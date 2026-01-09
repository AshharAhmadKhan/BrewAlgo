import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

export const problemService = {
  // Get all problems
  getAllProblems: async () => {
    const response = await api.get(API_ENDPOINTS.PROBLEMS);
    return response.data;
  },

  // Get problem by ID
  getProblemById: async (id) => {
    const response = await api.get(API_ENDPOINTS.PROBLEM_BY_ID(id));
    return response.data;
  },

  // Get problem by slug
  getProblemBySlug: async (slug) => {
    const response = await api.get(API_ENDPOINTS.PROBLEM_BY_SLUG(slug));
    return response.data;
  },

  // Get problems by difficulty
  getProblemsByDifficulty: async (difficulty) => {
    const response = await api.get(API_ENDPOINTS.PROBLEMS_BY_DIFFICULTY(difficulty));
    return response.data;
  },

  // Get recommended problems
  getRecommendedProblems: async (userId, limit = 10) => {
    const response = await api.get(API_ENDPOINTS.RECOMMENDED_PROBLEMS, {
      params: { userId, limit },
    });
    return response.data;
  },

  // Submit solution
  submitSolution: async (userId, problemId, code, language) => {
    const response = await api.post(API_ENDPOINTS.SUBMISSIONS, {
      userId,
      problemId,
      code,
      language,
    });
    return response.data;
  },

  // Check if user solved problem
  hasUserSolved: async (userId, problemId) => {
    const response = await api.get(API_ENDPOINTS.CHECK_SOLVED(userId, problemId));
    return response.data.solved;
  },

  // Get user's submissions for a problem
  getUserProblemSubmissions: async (userId, problemId) => {
    const response = await api.get(`${API_ENDPOINTS.SUBMISSIONS}/user/${userId}/problem/${problemId}`);
    return response.data;
  },
};