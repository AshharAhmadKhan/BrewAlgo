# BrewAlgo - API Reference

**Version:** 1.0.0  
**Base URL:** `http://localhost:8081/api`  
**Authentication:** JWT Bearer Token  
**Last Updated:** January 15, 2026

---

## 📋 **Table of Contents**

1. [Authentication](#authentication)
2. [Problems](#problems)
3. [Submissions](#submissions)
4. [Users](#users)
5. [Leaderboard](#leaderboard)
6. [Contests](#contests)
7. [Response Formats](#response-formats)
8. [Error Codes](#error-codes)

---

## 🔐 **Authentication**

### **Register User**

**Endpoint:** `POST /auth/register`  
**Authentication:** None (Public)

**Request Body:**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123!"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "rating": 1500,
  "problemsSolved": 0,
  "role": "USER",
  "createdAt": "2026-01-15T10:00:00"
}
```

**Validation Rules:**
- Username: 3-50 characters, alphanumeric + underscore
- Email: Valid email format
- Password: Minimum 8 characters

**Possible Errors:**
- `400 Bad Request` - Invalid input
- `409 Conflict` - Username or email already exists

---

### **Login**

**Endpoint:** `POST /auth/login`  
**Authentication:** None (Public)

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "SecurePass123!"
}
```

**Response:** `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 86400000,
  "user": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "rating": 1500,
    "problemsSolved": 0
  }
}
```

**Token Usage:**
```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Possible Errors:**
- `401 Unauthorized` - Invalid credentials
- `400 Bad Request` - Missing username or password

---

## 📝 **Problems**

### **Get All Problems**

**Endpoint:** `GET /problems`  
**Authentication:** Optional (Public endpoint)

**Query Parameters:**
- `difficulty` (optional): `EASY`, `MEDIUM`, `HARD`
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 20)

**Request:**
```http
GET /api/problems?difficulty=EASY&page=0&size=10
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "title": "Two Sum",
    "slug": "two-sum",
    "difficulty": "EASY",
    "baseScore": 100,
    "acceptanceRate": 85.5,
    "totalSubmissions": 15420,
    "successfulSubmissions": 13184,
    "tags": ["Array", "Hash Table"],
    "createdAt": "2026-01-01T00:00:00"
  },
  {
    "id": 2,
    "title": "Add Two Numbers",
    "slug": "add-two-numbers",
    "difficulty": "MEDIUM",
    "baseScore": 200,
    "acceptanceRate": 42.3,
    "totalSubmissions": 8932,
    "successfulSubmissions": 3778,
    "tags": ["Linked List", "Math"],
    "createdAt": "2026-01-02T00:00:00"
  }
]
```

---

### **Get Problem by Slug**

**Endpoint:** `GET /problems/slug/{slug}`  
**Authentication:** Optional

**Request:**
```http
GET /api/problems/slug/two-sum
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "title": "Two Sum",
  "slug": "two-sum",
  "description": "Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.\n\nYou may assume that each input would have exactly one solution, and you may not use the same element twice.\n\n**Example 1:**\n```\nInput: nums = [2,7,11,15], target = 9\nOutput: [0,1]\nExplanation: Because nums[0] + nums[1] == 9, we return [0, 1].\n```\n\n**Constraints:**\n- 2 <= nums.length <= 10^4\n- -10^9 <= nums[i] <= 10^9\n- -10^9 <= target <= 10^9\n- Only one valid answer exists.",
  "difficulty": "EASY",
  "baseScore": 100,
  "acceptanceRate": 85.5,
  "totalSubmissions": 15420,
  "successfulSubmissions": 13184,
  "tags": ["Array", "Hash Table"],
  "hints": "Use a hash map to store numbers you've seen | Look for target - current number",
  "createdAt": "2026-01-01T00:00:00"
}
```

**Possible Errors:**
- `404 Not Found` - Problem with given slug doesn't exist

---

### **Get Problem by ID**

**Endpoint:** `GET /problems/{id}`  
**Authentication:** Optional

**Request:**
```http
GET /api/problems/1
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "title": "Two Sum",
  "slug": "two-sum",
  "description": "...",
  "difficulty": "EASY",
  "baseScore": 100,
  "acceptanceRate": 85.5,
  "totalSubmissions": 15420,
  "successfulSubmissions": 13184,
  "tags": ["Array", "Hash Table"],
  "hints": "Use a hash map to store numbers you've seen",
  "createdAt": "2026-01-01T00:00:00"
}
```

**Possible Errors:**
- `404 Not Found` - Problem doesn't exist

---

## 🚀 **Submissions**

### **Submit Solution**

**Endpoint:** `POST /submissions`  
**Authentication:** **Required** (JWT)

**Request Body:**
```json
{
  "problemId": 1,
  "code": "import java.util.*;\n\npublic class Solution {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        String[] numsStr = sc.nextLine().split(\",\");\n        int target = sc.nextInt();\n        \n        int[] nums = new int[numsStr.length];\n        for (int i = 0; i < numsStr.length; i++) {\n            nums[i] = Integer.parseInt(numsStr[i]);\n        }\n        \n        Map<Integer, Integer> map = new HashMap<>();\n        for (int i = 0; i < nums.length; i++) {\n            int complement = target - nums[i];\n            if (map.containsKey(complement)) {\n                System.out.println(map.get(complement) + \",\" + i);\n                return;\n            }\n            map.put(nums[i], i);\n        }\n    }\n}",
  "language": "JAVA"
}
```

**Supported Languages:**
- `JAVA`
- `PYTHON`

**Response:** `200 OK`
```json
{
  "submission": {
    "id": 42,
    "userId": 1,
    "username": "john_doe",
    "problemId": 1,
    "problemTitle": "Two Sum",
    "contestId": null,
    "code": "...",
    "language": "JAVA",
    "status": "ACCEPTED",
    "executionTimeMs": 3262,
    "memoryUsedKb": 0,
    "scoreAwarded": 100,
    "submittedAt": "2026-01-15T12:30:00",
    "errorMessage": null
  },
  "executionResult": {
    "status": "ACCEPTED",
    "output": "All test cases passed",
    "errorMessage": null,
    "executionTimeMs": 3262,
    "memoryUsedKb": 0,
    "passedTestCases": 3,
    "totalTestCases": 3
  }
}
```

**Possible Status Values:**
- `ACCEPTED` - All test cases passed ✅
- `WRONG_ANSWER` - Output doesn't match expected ❌
- `COMPILATION_ERROR` - Code failed to compile 🔨
- `RUNTIME_ERROR` - Code crashed during execution 💥
- `TIME_LIMIT_EXCEEDED` - Execution took > 5 seconds ⏱️

**Possible Errors:**
- `400 Bad Request` - Invalid input (empty code, invalid language)
- `401 Unauthorized` - Missing or invalid JWT token
- `404 Not Found` - Problem doesn't exist

---

### **Get User's Submissions**

**Endpoint:** `GET /submissions/user/{userId}`  
**Authentication:** **Required** (JWT)

**Request:**
```http
GET /api/submissions/user/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:** `200 OK`
```json
[
  {
    "id": 42,
    "userId": 1,
    "username": "john_doe",
    "problemId": 1,
    "problemTitle": "Two Sum",
    "contestId": null,
    "language": "JAVA",
    "status": "ACCEPTED",
    "executionTimeMs": 3262,
    "memoryUsedKb": 0,
    "scoreAwarded": 100,
    "submittedAt": "2026-01-15T12:30:00"
  },
  {
    "id": 41,
    "userId": 1,
    "username": "john_doe",
    "problemId": 1,
    "problemTitle": "Two Sum",
    "contestId": null,
    "language": "JAVA",
    "status": "WRONG_ANSWER",
    "executionTimeMs": 2981,
    "memoryUsedKb": 0,
    "scoreAwarded": 0,
    "submittedAt": "2026-01-15T12:25:00"
  }
]
```

**Note:** Ordered by submission time (newest first)

---

### **Get Submissions for Problem**

**Endpoint:** `GET /submissions/problem/{problemId}`  
**Authentication:** **Required** (JWT, ADMIN only)

**Request:**
```http
GET /api/submissions/problem/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:** `200 OK`
```json
[
  {
    "id": 42,
    "userId": 1,
    "username": "john_doe",
    "problemId": 1,
    "problemTitle": "Two Sum",
    "language": "JAVA",
    "status": "ACCEPTED",
    "executionTimeMs": 3262,
    "submittedAt": "2026-01-15T12:30:00"
  },
  {
    "id": 40,
    "userId": 2,
    "username": "alice",
    "problemId": 1,
    "problemTitle": "Two Sum",
    "language": "PYTHON",
    "status": "ACCEPTED",
    "executionTimeMs": 2145,
    "submittedAt": "2026-01-15T11:00:00"
  }
]
```

**Possible Errors:**
- `403 Forbidden` - User is not admin

---

### **Get User's Problem Submissions**

**Endpoint:** `GET /submissions/user/{userId}/problem/{problemId}`  
**Authentication:** **Required** (JWT)

**Request:**
```http
GET /api/submissions/user/1/problem/1
```

**Response:** `200 OK`
```json
[
  {
    "id": 42,
    "userId": 1,
    "username": "john_doe",
    "problemId": 1,
    "problemTitle": "Two Sum",
    "language": "JAVA",
    "status": "ACCEPTED",
    "executionTimeMs": 3262,
    "submittedAt": "2026-01-15T12:30:00"
  },
  {
    "id": 41,
    "userId": 1,
    "username": "john_doe",
    "problemId": 1,
    "problemTitle": "Two Sum",
    "language": "JAVA",
    "status": "WRONG_ANSWER",
    "executionTimeMs": 2981,
    "submittedAt": "2026-01-15T12:25:00"
  }
]
```

---

## 👤 **Users**

### **Get User Profile**

**Endpoint:** `GET /users/profile`  
**Authentication:** **Required** (JWT)

**Request:**
```http
GET /api/users/profile
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "rating": 1650,
  "problemsSolved": 15,
  "role": "USER",
  "createdAt": "2026-01-01T00:00:00",
  "lastLoginAt": "2026-01-15T10:00:00",
  "recentSubmissions": [
    {
      "problemTitle": "Two Sum",
      "status": "ACCEPTED",
      "submittedAt": "2026-01-15T12:30:00"
    },
    {
      "problemTitle": "Reverse String",
      "status": "ACCEPTED",
      "submittedAt": "2026-01-14T15:20:00"
    }
  ]
}
```

**Possible Errors:**
- `401 Unauthorized` - Invalid token

---

### **Get User by ID**

**Endpoint:** `GET /users/{id}`  
**Authentication:** Optional

**Request:**
```http
GET /api/users/1
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "username": "john_doe",
  "rating": 1650,
  "problemsSolved": 15,
  "createdAt": "2026-01-01T00:00:00"
}
```

**Note:** Email is not exposed in public profile

**Possible Errors:**
- `404 Not Found` - User doesn't exist

---

## 🏆 **Leaderboard**

### **Get Global Leaderboard**

**Endpoint:** `GET /leaderboard`  
**Authentication:** Optional

**Query Parameters:**
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 50)

**Request:**
```http
GET /api/leaderboard?page=0&size=10
```

**Response:** `200 OK`
```json
[
  {
    "rank": 1,
    "userId": 5,
    "username": "coding_master",
    "rating": 2450,
    "problemsSolved": 87
  },
  {
    "rank": 2,
    "userId": 12,
    "username": "algo_guru",
    "rating": 2301,
    "problemsSolved": 72
  },
  {
    "rank": 3,
    "userId": 1,
    "username": "john_doe",
    "rating": 1650,
    "problemsSolved": 15
  }
]
```

**Note:** Ranked by rating (descending), then by problems solved

---

## 🏅 **Contests**

### **Get Live Contests**

**Endpoint:** `GET /contests/live`  
**Authentication:** Optional

**Request:**
```http
GET /api/contests/live
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "title": "Weekly Contest 123",
    "description": "Test your skills in this week's challenge!",
    "startTime": "2026-01-15T10:00:00",
    "endTime": "2026-01-15T12:00:00",
    "isActive": true,
    "participantCount": 245
  }
]
```

---

### **Get Contest Leaderboard**

**Endpoint:** `GET /contests/{id}/leaderboard`  
**Authentication:** Optional

**Request:**
```http
GET /api/contests/1/leaderboard
```

**Response:** `200 OK`
```json
[
  {
    "rank": 1,
    "userId": 5,
    "username": "coding_master",
    "score": 300,
    "problemsSolved": 3,
    "penalty": 0
  },
  {
    "rank": 2,
    "userId": 12,
    "username": "algo_guru",
    "score": 200,
    "problemsSolved": 2,
    "penalty": 120
  }
]
```

---

## 📊 **Response Formats**

### **Success Response**

All successful responses follow this structure:
```json
{
  "data": { /* Response data */ },
  "timestamp": "2026-01-15T12:30:00"
}
```

For list endpoints, data is an array:
```json
{
  "data": [ /* Array of items */ ],
  "timestamp": "2026-01-15T12:30:00",
  "page": 0,
  "size": 20,
  "totalElements": 150,
  "totalPages": 8
}
```

---

### **Error Response**

All error responses follow this structure:
```json
{
  "error": {
    "code": "RESOURCE_NOT_FOUND",
    "message": "Problem with slug 'invalid-slug' not found",
    "timestamp": "2026-01-15T12:30:00",
    "path": "/api/problems/slug/invalid-slug"
  }
}
```

---

## ❌ **Error Codes**

| HTTP Status | Error Code | Description |
|-------------|-----------|-------------|
| 400 | `BAD_REQUEST` | Invalid input or malformed request |
| 401 | `UNAUTHORIZED` | Missing or invalid authentication token |
| 403 | `FORBIDDEN` | User lacks permission for this resource |
| 404 | `NOT_FOUND` | Requested resource doesn't exist |
| 409 | `CONFLICT` | Resource already exists (e.g., duplicate username) |
| 500 | `INTERNAL_SERVER_ERROR` | Server encountered an error |

---

## 🔄 **Rate Limiting**

**Current Status:** Not implemented

**Planned:**
- 100 requests per minute per user
- 10 submissions per minute per user
- Headers in response:
  - `X-RateLimit-Limit`: 100
  - `X-RateLimit-Remaining`: 95
  - `X-RateLimit-Reset`: 1642248000

---

## 📝 **Testing with cURL**

### **Register User**
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"Test123!"}'
```

### **Login**
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"Test123!"}'
```

### **Get Problems (with auth)**
```bash
curl -X GET http://localhost:8081/api/problems \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### **Submit Solution**
```bash
curl -X POST http://localhost:8081/api/submissions \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "problemId": 1,
    "code": "public class Solution { public static void main(String[] args) { System.out.println(\"0,1\"); } }",
    "language": "JAVA"
  }'
```

---

## 🧪 **Testing with Postman**

**Collection:** [Download BrewAlgo.postman_collection.json](#)

**Environment Variables:**
- `baseUrl`: `http://localhost:8081/api`
- `token`: (set after login)

**Pre-request Script (for authenticated endpoints):**
```javascript
pm.request.headers.add({
    key: 'Authorization',
    value: 'Bearer ' + pm.environment.get('token')
});
```

---

## 📚 **API Versioning**

**Current Version:** v1 (implied, no version in URL)

**Future:** When breaking changes are introduced, new endpoints will be:
- `/api/v2/problems`
- `/api/v2/submissions`

v1 endpoints will remain supported for 6 months after v2 release.

---

## 🔒 **Security Considerations**

1. **JWT Token Expiration:** 24 hours (refresh not yet implemented)
2. **Password Requirements:** Minimum 8 characters
3. **HTTPS:** Recommended for production (not enforced in development)
4. **CORS:** Configured for `http://localhost:5173` only

---

**API Reference Version:** 1.0.0  
**Last Updated:** January 15, 2026  
**Author:** Ashhar Ahmad Khan

*For implementation details, see [ARCHITECTURE.md](ARCHITECTURE.md)*