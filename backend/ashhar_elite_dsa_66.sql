-- ============================================
-- ASHHAR'S ELITE DSA COLLECTION
-- 66 Handpicked Problems for Interview Mastery
-- ============================================

-- Sliding Window Problems
INSERT INTO problems (title, slug, description, difficulty, base_score, hints, acceptance_rate, total_submissions, successful_submissions, created_at) VALUES
('Maximum Average Subarray I', 'max-average-subarray',
'You are given an integer array `nums` consisting of `n` elements, and an integer `k`. Find a contiguous subarray whose length is equal to `k` that has the maximum average value.

**Example 1:**
```
Input: nums = [1,12,-5,-6,50,3], k = 4
Output: 12.75000
```

**Constraints:**
- 1 <= k <= n <= 10^5',
'EASY', 150, 'Use sliding window technique. Calculate sum of first k elements, then slide.', 0, 0, 0, NOW()),

('Maximum Number of Vowels in a Substring', 'max-vowels-substring',
'Given a string `s` and an integer `k`, return the maximum number of vowel letters in any substring of `s` with length `k`.

**Example 1:**
```
Input: s = "abciiidef", k = 3
Output: 3
```

**Constraints:**
- 1 <= s.length <= 10^5',
'MEDIUM', 200, 'Use sliding window. Count vowels in first k characters, then slide.', 0, 0, 0, NOW()),

('Max Consecutive Ones III', 'max-consecutive-ones-iii',
'Given a binary array `nums` and an integer `k`, return the maximum number of consecutive 1''s if you can flip at most `k` 0''s.

**Example 1:**
```
Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
Output: 6
```

**Constraints:**
- 1 <= nums.length <= 10^5',
'MEDIUM', 250, 'Use sliding window. Expand while zeros <= k, shrink when zeros > k.', 0, 0, 0, NOW()),

('Longest Subarray After Deleting One Element', 'longest-subarray-delete-one',
'Given a binary array `nums`, delete one element from it. Return the size of the longest non-empty subarray containing only 1''s.

**Example 1:**
```
Input: nums = [1,1,0,1]
Output: 3
```

**Constraints:**
- 1 <= nums.length <= 10^5',
'MEDIUM', 250, 'Use sliding window allowing at most one 0.', 0, 0, 0, NOW()),

-- Prefix Sum Problems
('Find the Highest Altitude', 'highest-altitude',
'There is a biker going on a road trip. You are given an integer array `gain` where `gain[i]` is the net gain in altitude. Return the highest altitude.

**Example 1:**
```
Input: gain = [-5,1,5,0,-7]
Output: 1
```

**Constraints:**
- 1 <= n <= 100',
'EASY', 100, 'Calculate cumulative sum and track maximum.', 0, 0, 0, NOW()),

('Find Pivot Index', 'pivot-index',
'Given an array of integers `nums`, calculate the pivot index where the sum of left equals sum of right.

**Example 1:**
```
Input: nums = [1,7,3,6,5,6]
Output: 3
```

**Constraints:**
- 1 <= nums.length <= 10^4',
'EASY', 150, 'Calculate total sum first, then check if leftSum == totalSum - leftSum - nums[i].', 0, 0, 0, NOW()),

-- Hash Map / Set Problems
('Find the Difference of Two Arrays', 'difference-two-arrays',
'Given two arrays `nums1` and `nums2`, return a list where answer[0] is distinct integers in nums1 not in nums2, and answer[1] is distinct integers in nums2 not in nums1.

**Example 1:**
```
Input: nums1 = [1,2,3], nums2 = [2,4,6]
Output: [[1,3],[4,6]]
```

**Constraints:**
- 1 <= nums1.length, nums2.length <= 1000',
'EASY', 150, 'Use HashSet to store elements, then find differences.', 0, 0, 0, NOW()),

('Unique Number of Occurrences', 'unique-occurrences',
'Given an array `arr`, return `true` if the number of occurrences of each value is unique.

**Example 1:**
```
Input: arr = [1,2,2,1,1,3]
Output: true
```

**Constraints:**
- 1 <= arr.length <= 1000',
'EASY', 150, 'Count occurrences with HashMap, check if counts are unique using HashSet.', 0, 0, 0, NOW()),

('Determine if Two Strings Are Close', 'close-strings',
'Two strings are close if you can transform one into the other using swap and transform operations.

**Example 1:**
```
Input: word1 = "abc", word2 = "bca"
Output: true
```

**Constraints:**
- 1 <= word1.length, word2.length <= 10^5',
'MEDIUM', 250, 'Check if both have same character set and same frequency distribution.', 0, 0, 0, NOW()),

('Equal Row and Column Pairs', 'equal-row-column-pairs',
'Given an `n x n` matrix `grid`, return the number of pairs where row equals column.

**Example 1:**
```
Input: grid = [[3,2,1],[1,7,6],[2,7,7]]
Output: 1
```

**Constraints:**
- 1 <= n <= 200',
'MEDIUM', 250, 'Convert rows to strings, store in HashMap with counts, compare with columns.', 0, 0, 0, NOW()),

-- Stack Problems
('Removing Stars From a String', 'remove-stars',
'Given a string `s` with stars, remove the closest non-star character to the left of each star.

**Example 1:**
```
Input: s = "leet**cod*e"
Output: "lecoe"
```

**Constraints:**
- 1 <= s.length <= 10^5',
'MEDIUM', 200, 'Use stack. Push characters, pop when encountering star.', 0, 0, 0, NOW()),

('Asteroid Collision', 'asteroid-collision',
'Given an array `asteroids` representing asteroids in a row, find the state after all collisions.

**Example 1:**
```
Input: asteroids = [5,10,-5]
Output: [5,10]
```

**Constraints:**
- 2 <= asteroids.length <= 10^4',
'MEDIUM', 250, 'Use stack. Only right-moving and left-moving asteroids collide.', 0, 0, 0, NOW()),

('Decode String', 'decode-string',
'Given an encoded string, return its decoded string. Encoding rule: `k[encoded_string]`.

**Example 1:**
```
Input: s = "3[a]2[bc]"
Output: "aaabcbc"
```

**Constraints:**
- 1 <= s.length <= 30',
'MEDIUM', 300, 'Use stack to handle nested brackets.', 0, 0, 0, NOW()),

('Daily Temperatures', 'daily-temperatures',
'Given `temperatures`, return array where answer[i] is days until warmer temperature.

**Example 1:**
```
Input: temperatures = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]
```

**Constraints:**
- 1 <= temperatures.length <= 10^5',
'MEDIUM', 250, 'Use monotonic decreasing stack storing indices.', 0, 0, 0, NOW()),

-- Queue Problems
('Number of Recent Calls', 'recent-calls',
'Implement RecentCounter class that counts recent requests within 3000ms time frame.

**Example 1:**
```
Input: ["RecentCounter", "ping", "ping", "ping"]
Output: [null, 1, 2, 3]
```

**Constraints:**
- At most 10^4 calls to ping',
'EASY', 150, 'Use queue. Add new request, remove requests older than t - 3000.', 0, 0, 0, NOW()),

-- Linked List Problems
('Delete the Middle Node of a Linked List', 'delete-middle-node',
'Delete the middle node of a linked list and return the head.

**Example 1:**
```
Input: head = [1,3,4,7,1,2,6]
Output: [1,3,4,1,2,6]
```

**Constraints:**
- 1 <= nodes <= 10^5',
'MEDIUM', 200, 'Use slow and fast pointers.', 0, 0, 0, NOW()),

('Odd Even Linked List', 'odd-even-linked-list',
'Group all odd indices together followed by even indices.

**Example 1:**
```
Input: head = [1,2,3,4,5]
Output: [1,3,5,2,4]
```

**Constraints:**
- 0 <= nodes <= 10^4',
'MEDIUM', 250, 'Use two pointers for odd and even lists.', 0, 0, 0, NOW()),

('Reverse Linked List', 'reverse-linked-list',
'Reverse a singly linked list.

**Example 1:**
```
Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]
```

**Constraints:**
- 0 <= nodes <= 5000',
'EASY', 150, 'Use three pointers: prev, current, next.', 0, 0, 0, NOW()),

('Maximum Twin Sum of a Linked List', 'max-twin-sum',
'Return the maximum twin sum where twin is node i and node (n-1-i).

**Example 1:**
```
Input: head = [5,4,2,1]
Output: 6
```

**Constraints:**
- 2 <= nodes <= 10^5',
'MEDIUM', 250, 'Find middle, reverse second half, compare twins.', 0, 0, 0, NOW()),

('Linked List Cycle II', 'linked-list-cycle-ii',
'Return the node where the cycle begins, or null if no cycle.

**Example 1:**
```
Input: head = [3,2,0,-4], pos = 1
Output: node at index 1
```

**Constraints:**
- 0 <= nodes <= 10^4',
'MEDIUM', 300, 'Use Floyd''s cycle detection.', 0, 0, 0, NOW());


-- Binary Tree - DFS Problems
INSERT INTO problems (title, slug, description, difficulty, base_score, hints, acceptance_rate, total_submissions, successful_submissions, created_at) VALUES
('Maximum Depth of Binary Tree', 'max-depth-binary-tree',
'Return the maximum depth of a binary tree.

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: 3
```

**Constraints:**
- 0 <= nodes <= 10^4',
'EASY', 100, 'Use recursion. Max depth = 1 + max(left, right).', 0, 0, 0, NOW()),

('Leaf-Similar Trees', 'leaf-similar-trees',
'Two trees are leaf-similar if their leaf value sequences are the same.

**Example 1:**
```
Input: root1 = [3,5,1,6,2,9,8], root2 = [3,5,1,6,7,4,2]
Output: true
```

**Constraints:**
- 1 <= nodes <= 200',
'EASY', 150, 'Use DFS to collect leaf nodes, then compare.', 0, 0, 0, NOW()),

('Count Good Nodes in Binary Tree', 'count-good-nodes',
'A node is good if no node in path from root has greater value.

**Example 1:**
```
Input: root = [3,1,4,3,null,1,5]
Output: 4
```

**Constraints:**
- 1 <= nodes <= 10^5',
'MEDIUM', 200, 'Use DFS with max value seen so far.', 0, 0, 0, NOW()),

('Path Sum III', 'path-sum-iii',
'Return number of paths that sum to targetSum (path doesn''t need to start/end at root/leaf).

**Example 1:**
```
Input: root = [10,5,-3,3,2,null,11], targetSum = 8
Output: 3
```

**Constraints:**
- 0 <= nodes <= 1000',
'MEDIUM', 300, 'Use DFS with prefix sum and HashMap.', 0, 0, 0, NOW()),

('Longest ZigZag Path in a Binary Tree', 'longest-zigzag-path',
'Return the longest ZigZag path (alternating left-right directions).

**Example 1:**
```
Input: root = [1,null,1,1,1,null,null,1,1,null,1]
Output: 3
```

**Constraints:**
- 1 <= nodes <= 5 * 10^4',
'MEDIUM', 300, 'Use DFS tracking left and right zigzag lengths.', 0, 0, 0, NOW()),

('Lowest Common Ancestor of a Binary Tree', 'lowest-common-ancestor',
'Find the lowest common ancestor of two nodes.

**Example 1:**
```
Input: root = [3,5,1,6,2,0,8], p = 5, q = 1
Output: 3
```

**Constraints:**
- 2 <= nodes <= 10^5',
'MEDIUM', 300, 'Use DFS. If both children return non-null, current is LCA.', 0, 0, 0, NOW()),

('Binary Tree Right Side View', 'binary-tree-right-side-view',
'Return values of nodes you can see from the right side.

**Example 1:**
```
Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]
```

**Constraints:**
- 0 <= nodes <= 100',
'MEDIUM', 250, 'Use BFS level-order, add last node of each level.', 0, 0, 0, NOW()),

('Maximum Level Sum of a Binary Tree', 'max-level-sum',
'Return the smallest level with maximum sum.

**Example 1:**
```
Input: root = [1,7,0,7,-8,null,null]
Output: 2
```

**Constraints:**
- 1 <= nodes <= 10^4',
'MEDIUM', 250, 'Use BFS, calculate sum for each level.', 0, 0, 0, NOW()),

-- Binary Search Tree Problems
('Search in a Binary Search Tree', 'search-bst',
'Find node with given value in BST and return subtree.

**Example 1:**
```
Input: root = [4,2,7,1,3], val = 2
Output: [2,1,3]
```

**Constraints:**
- 1 <= nodes <= 5000',
'EASY', 100, 'Use BST property: go left if val < node.val, right otherwise.', 0, 0, 0, NOW()),

('Delete Node in a BST', 'delete-node-bst',
'Delete node with given key in BST.

**Example 1:**
```
Input: root = [5,3,6,2,4,null,7], key = 3
Output: [5,4,6,2,null,null,7]
```

**Constraints:**
- 0 <= nodes <= 10^4',
'MEDIUM', 300, 'Handle 3 cases: leaf, one child, two children.', 0, 0, 0, NOW()),

-- Graph - DFS Problems
('Keys and Rooms', 'keys-and-rooms',
'Return true if you can visit all rooms starting from room 0.

**Example 1:**
```
Input: rooms = [[1],[2],[3],[]]
Output: true
```

**Constraints:**
- 2 <= n <= 1000',
'MEDIUM', 200, 'Use DFS/BFS from room 0, track visited rooms.', 0, 0, 0, NOW()),

('Number of Provinces', 'number-of-provinces',
'Return total number of provinces (connected components).

**Example 1:**
```
Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
Output: 2
```

**Constraints:**
- 1 <= n <= 200',
'MEDIUM', 250, 'Use DFS or Union-Find to count components.', 0, 0, 0, NOW()),

('Reorder Routes to Make All Paths Lead to City Zero', 'reorder-routes',
'Return minimum edges to change so all cities can reach city 0.

**Example 1:**
```
Input: n = 6, connections = [[0,1],[1,3],[2,3],[4,0],[4,5]]
Output: 3
```

**Constraints:**
- 2 <= n <= 5 * 10^4',
'MEDIUM', 300, 'Build bidirectional graph, DFS from 0, count edges going away.', 0, 0, 0, NOW()),

('Evaluate Division', 'evaluate-division',
'Given equations and values, evaluate queries.

**Example 1:**
```
Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0]
Output: [6.0, 0.5, -1.0, 1.0, -1.0]
```

**Constraints:**
- 1 <= equations.length <= 20',
'MEDIUM', 350, 'Build weighted graph, use DFS to find path and multiply weights.', 0, 0, 0, NOW()),

-- Graph - BFS Problems
('Nearest Exit from Entrance in Maze', 'nearest-exit-maze',
'Return shortest path from entrance to nearest exit in maze.

**Example 1:**
```
Input: maze = [["+","+",".","+"],[".",".",".","+"]], entrance = [1,2]
Output: 1
```

**Constraints:**
- 1 <= m, n <= 100',
'MEDIUM', 250, 'Use BFS from entrance, track distance.', 0, 0, 0, NOW()),

('Rotting Oranges', 'rotting-oranges',
'Return minimum minutes until no fresh orange remains.

**Example 1:**
```
Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
```

**Constraints:**
- 1 <= m, n <= 10',
'MEDIUM', 250, 'Use multi-source BFS from all rotten oranges.', 0, 0, 0, NOW());


-- Heap / Priority Queue Problems
INSERT INTO problems (title, slug, description, difficulty, base_score, hints, acceptance_rate, total_submissions, successful_submissions, created_at) VALUES
('Kth Largest Element in an Array', 'kth-largest-element',
'Return the kth largest element in the array.

**Example 1:**
```
Input: nums = [3,2,1,5,6,4], k = 2
Output: 5
```

**Constraints:**
- 1 <= k <= nums.length <= 10^5',
'MEDIUM', 250, 'Use min heap of size k.', 0, 0, 0, NOW()),

('Smallest Number in Infinite Set', 'smallest-infinite-set',
'Implement SmallestInfiniteSet with popSmallest() and addBack(num).

**Example 1:**
```
Input: ["SmallestInfiniteSet", "popSmallest", "popSmallest"]
Output: [null, 1, 2]
```

**Constraints:**
- At most 1000 calls total',
'MEDIUM', 250, 'Use min heap for added back numbers, track current smallest.', 0, 0, 0, NOW()),

('Maximum Subsequence Score', 'max-subsequence-score',
'Choose k indices to maximize sum(nums1) * min(nums2).

**Example 1:**
```
Input: nums1 = [1,3,3,2], nums2 = [2,1,3,4], k = 3
Output: 12
```

**Constraints:**
- 1 <= n <= 10^5',
'MEDIUM', 350, 'Sort by nums2 descending, use min heap for k largest nums1 values.', 0, 0, 0, NOW()),

-- Binary Search Problems
('Guess Number Higher or Lower', 'guess-number',
'Guess the picked number using binary search.

**Example 1:**
```
Input: n = 10, pick = 6
Output: 6
```

**Constraints:**
- 1 <= n <= 2^31 - 1',
'EASY', 100, 'Use binary search, adjust range based on guess() result.', 0, 0, 0, NOW()),

('Successful Pairs of Spells and Potions', 'spells-and-potions',
'Return number of potions that form successful pair with each spell.

**Example 1:**
```
Input: spells = [5,1,3], potions = [1,2,3,4,5], success = 7
Output: [4,0,3]
```

**Constraints:**
- 1 <= n, m <= 10^5',
'MEDIUM', 250, 'Sort potions, binary search for each spell.', 0, 0, 0, NOW()),

('Find Peak Element', 'find-peak-element',
'Find a peak element in O(log n) time.

**Example 1:**
```
Input: nums = [1,2,3,1]
Output: 2
```

**Constraints:**
- 1 <= nums.length <= 1000',
'MEDIUM', 250, 'Use binary search. If mid < mid+1, peak is on right.', 0, 0, 0, NOW()),

('Koko Eating Bananas', 'koko-eating-bananas',
'Return minimum eating speed k to finish all bananas in h hours.

**Example 1:**
```
Input: piles = [3,6,7,11], h = 8
Output: 4
```

**Constraints:**
- 1 <= piles.length <= 10^4',
'MEDIUM', 300, 'Binary search on speed k, calculate hours needed.', 0, 0, 0, NOW()),

-- Backtracking Problems
('Letter Combinations of a Phone Number', 'letter-combinations',
'Return all possible letter combinations from phone number digits.

**Example 1:**
```
Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

**Constraints:**
- 0 <= digits.length <= 4',
'MEDIUM', 250, 'Use backtracking, build combinations recursively.', 0, 0, 0, NOW()),

('Combination Sum III', 'combination-sum-iii',
'Find all combinations of k numbers that sum to n using only 1-9.

**Example 1:**
```
Input: k = 3, n = 7
Output: [[1,2,4]]
```

**Constraints:**
- 2 <= k <= 9',
'MEDIUM', 250, 'Use backtracking, track sum and count.', 0, 0, 0, NOW()),

-- Dynamic Programming - 1D
('N-th Tribonacci Number', 'tribonacci-number',
'Return the nth Tribonacci number.

**Example 1:**
```
Input: n = 4
Output: 4
```

**Constraints:**
- 0 <= n <= 37',
'EASY', 100, 'Use DP with three variables.', 0, 0, 0, NOW()),

('Min Cost Climbing Stairs', 'min-cost-climbing-stairs',
'Return minimum cost to reach the top.

**Example 1:**
```
Input: cost = [10,15,20]
Output: 15
```

**Constraints:**
- 2 <= cost.length <= 1000',
'EASY', 150, 'DP: dp[i] = cost[i] + min(dp[i-1], dp[i-2]).', 0, 0, 0, NOW()),

('House Robber', 'house-robber',
'Return maximum money you can rob without alerting police (no adjacent houses).

**Example 1:**
```
Input: nums = [1,2,3,1]
Output: 4
```

**Constraints:**
- 1 <= nums.length <= 100',
'MEDIUM', 200, 'DP: dp[i] = max(dp[i-1], dp[i-2] + nums[i]).', 0, 0, 0, NOW()),

('Domino and Tromino Tiling', 'domino-tromino-tiling',
'Return number of ways to tile 2 x n board.

**Example 1:**
```
Input: n = 3
Output: 5
```

**Constraints:**
- 1 <= n <= 1000',
'MEDIUM', 300, 'DP: dp[i] = 2*dp[i-1] + dp[i-3].', 0, 0, 0, NOW()),

('Longest Common Subsequence', 'longest-common-subsequence',
'Return length of longest common subsequence.

**Example 1:**
```
Input: text1 = "abcde", text2 = "ace"
Output: 3
```

**Constraints:**
- 1 <= text1.length, text2.length <= 1000',
'MEDIUM', 300, 'DP: dp[i][j] = dp[i-1][j-1] + 1 if match, else max(dp[i-1][j], dp[i][j-1]).', 0, 0, 0, NOW()),

-- Dynamic Programming - Multidimensional
('Best Time to Buy and Sell Stock with Transaction Fee', 'stock-with-fee',
'Return maximum profit with transaction fee.

**Example 1:**
```
Input: prices = [1,3,2,8,4,9], fee = 2
Output: 8
```

**Constraints:**
- 1 <= prices.length <= 5 * 10^4',
'MEDIUM', 300, 'DP with two states: hold and cash.', 0, 0, 0, NOW()),

('Edit Distance', 'edit-distance',
'Return minimum operations to convert word1 to word2.

**Example 1:**
```
Input: word1 = "horse", word2 = "ros"
Output: 3
```

**Constraints:**
- 0 <= word1.length, word2.length <= 500',
'MEDIUM', 350, 'DP: dp[i][j] = min(insert, delete, replace).', 0, 0, 0, NOW()),

('Unique Paths', 'unique-paths',
'Return number of unique paths from top-left to bottom-right.

**Example 1:**
```
Input: m = 3, n = 7
Output: 28
```

**Constraints:**
- 1 <= m, n <= 100',
'MEDIUM', 250, 'DP: dp[i][j] = dp[i-1][j] + dp[i][j-1].', 0, 0, 0, NOW()),

('Longest Increasing Path in a Matrix', 'longest-increasing-path',
'Return length of longest increasing path in matrix.

**Example 1:**
```
Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
Output: 4
```

**Constraints:**
- 1 <= m, n <= 200',
'HARD', 400, 'DFS with memoization.', 0, 0, 0, NOW());


-- Bit Manipulation Problems
INSERT INTO problems (title, slug, description, difficulty, base_score, hints, acceptance_rate, total_submissions, successful_submissions, created_at) VALUES
('Counting Bits', 'counting-bits',
'Return array where ans[i] is number of 1''s in binary representation of i.

**Example 1:**
```
Input: n = 2
Output: [0,1,1]
```

**Constraints:**
- 0 <= n <= 10^5',
'EASY', 150, 'DP: ans[i] = ans[i >> 1] + (i & 1).', 0, 0, 0, NOW()),

('Single Number', 'single-number',
'Find the element that appears once (all others appear twice).

**Example 1:**
```
Input: nums = [2,2,1]
Output: 1
```

**Constraints:**
- 1 <= nums.length <= 3 * 10^4',
'EASY', 150, 'Use XOR. a ^ a = 0, a ^ 0 = a.', 0, 0, 0, NOW()),

('Minimum Flips to Make a OR b Equal to c', 'min-flips-or',
'Return minimum flips to make (a OR b == c).

**Example 1:**
```
Input: a = 2, b = 6, c = 5
Output: 3
```

**Constraints:**
- 1 <= a, b, c <= 10^9',
'MEDIUM', 250, 'Check each bit position.', 0, 0, 0, NOW()),

-- Trie Problems
('Implement Trie (Prefix Tree)', 'implement-trie',
'Implement Trie with insert, search, and startsWith methods.

**Example 1:**
```
Input: ["Trie", "insert", "search"]
Output: [null, null, true]
```

**Constraints:**
- At most 3 * 10^4 calls total',
'MEDIUM', 300, 'Create TrieNode with children map and isEnd flag.', 0, 0, 0, NOW()),

('Search Suggestions System', 'search-suggestions',
'Suggest at most 3 products after each character typed.

**Example 1:**
```
Input: products = ["mobile","mouse","moneypot"], searchWord = "mouse"
Output: [["mobile","moneypot"],["mobile","moneypot"],["mouse"],["mouse"],["mouse"]]
```

**Constraints:**
- 1 <= products.length <= 1000',
'MEDIUM', 300, 'Sort products, use binary search or Trie.', 0, 0, 0, NOW()),

-- Intervals Problems
('Non-overlapping Intervals', 'non-overlapping-intervals',
'Return minimum intervals to remove to make rest non-overlapping.

**Example 1:**
```
Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
Output: 1
```

**Constraints:**
- 1 <= intervals.length <= 10^5',
'MEDIUM', 250, 'Sort by end time, greedily select intervals.', 0, 0, 0, NOW()),

('Minimum Number of Arrows to Burst Balloons', 'min-arrows-burst-balloons',
'Return minimum arrows to burst all balloons.

**Example 1:**
```
Input: points = [[10,16],[2,8],[1,6],[7,12]]
Output: 2
```

**Constraints:**
- 1 <= points.length <= 10^5',
'MEDIUM', 250, 'Sort by end position, shoot arrow at end of first balloon.', 0, 0, 0, NOW()),

-- Monotonic Stack
('Online Stock Span', 'online-stock-span',
'Return span of stock price (consecutive days with price <= today).

**Example 1:**
```
Input: ["StockSpanner", "next", "next"]
Output: [null, 1, 1]
```

**Constraints:**
- At most 10^4 calls to next',
'MEDIUM', 250, 'Use monotonic decreasing stack storing (price, span) pairs.', 0, 0, 0, NOW());

-- ============================================
-- END OF ASHHAR'S ELITE DSA COLLECTION
-- Total: 66 Premium Problems Added
-- ============================================
