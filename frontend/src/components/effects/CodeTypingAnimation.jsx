import { useState, useEffect } from 'react';

const CodeTypingAnimation = () => {
  const [displayedCode, setDisplayedCode] = useState('');
  const [currentIndex, setCurrentIndex] = useState(0);
  const [currentSnippet, setCurrentSnippet] = useState(0);

  const codeSnippets = [
    {
      code: `function twoSum(nums, target) {
  const map = new Map();
  for (let i = 0; i < nums.length; i++) {
    const complement = target - nums[i];
    if (map.has(complement)) {
      return [map.get(complement), i];
    }
    map.set(nums[i], i);
  }
}`,
      language: 'JavaScript'
    },
    {
      code: `def binary_search(arr, target):
    left, right = 0, len(arr) - 1
    while left <= right:
        mid = (left + right) // 2
        if arr[mid] == target:
            return mid
        elif arr[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1`,
      language: 'Python'
    },
    {
      code: `public int maxSubArray(int[] nums) {
    int maxSum = nums[0];
    int currentSum = nums[0];
    for (int i = 1; i < nums.length; i++) {
        currentSum = Math.max(nums[i], 
                     currentSum + nums[i]);
        maxSum = Math.max(maxSum, currentSum);
    }
    return maxSum;
}`,
      language: 'Java'
    }
  ];

  useEffect(() => {
    const snippet = codeSnippets[currentSnippet];
    
    if (currentIndex < snippet.code.length) {
      const timeout = setTimeout(() => {
        setDisplayedCode(snippet.code.substring(0, currentIndex + 1));
        setCurrentIndex(currentIndex + 1);
      }, 30); // Typing speed

      return () => clearTimeout(timeout);
    } else {
      // Wait 2 seconds then move to next snippet
      const timeout = setTimeout(() => {
        setCurrentIndex(0);
        setDisplayedCode('');
        setCurrentSnippet((currentSnippet + 1) % codeSnippets.length);
      }, 2000);

      return () => clearTimeout(timeout);
    }
  }, [currentIndex, currentSnippet]);

  return (
    <div className="relative max-w-3xl mx-auto mt-12 mb-8">
      <div className="bg-gray-900 rounded-xl shadow-2xl overflow-hidden border border-gray-700">
        {/* Terminal Header */}
        <div className="bg-gray-800 px-4 py-3 flex items-center justify-between border-b border-gray-700">
          <div className="flex space-x-2">
            <div className="w-3 h-3 rounded-full bg-red-500"></div>
            <div className="w-3 h-3 rounded-full bg-yellow-500"></div>
            <div className="w-3 h-3 rounded-full bg-green-500"></div>
          </div>
          <div className="text-gray-400 text-sm font-mono">
            {codeSnippets[currentSnippet].language}
          </div>
          <div className="w-16"></div>
        </div>

        {/* Code Area */}
        <div className="p-6 font-mono text-sm leading-relaxed">
          <pre className="text-gray-100">
            <code className="language-javascript">
              {displayedCode}
              <span className="animate-pulse text-blue-400">|</span>
            </code>
          </pre>
        </div>

        {/* Bottom Bar */}
        <div className="bg-gray-800 px-4 py-2 flex items-center justify-between border-t border-gray-700">
          <div className="text-xs text-gray-500">
            Line {displayedCode.split('\n').length} • Col {displayedCode.split('\n').pop()?.length || 0}
          </div>
          <div className="flex items-center space-x-2">
            <div className="w-2 h-2 rounded-full bg-green-500 animate-pulse"></div>
            <span className="text-xs text-gray-500">Live Coding</span>
          </div>
        </div>
      </div>

      {/* Floating Labels */}
      <div className="absolute -top-4 -left-4 bg-blue-500 text-white px-3 py-1 rounded-full text-xs font-semibold shadow-lg animate-bounce">
        ⚡ Fast
      </div>
      <div className="absolute -top-4 -right-4 bg-purple-500 text-white px-3 py-1 rounded-full text-xs font-semibold shadow-lg animate-bounce" style={{ animationDelay: '0.2s' }}>
        🎯 Accurate
      </div>
      <div className="absolute -bottom-4 left-1/2 transform -translate-x-1/2 bg-green-500 text-white px-3 py-1 rounded-full text-xs font-semibold shadow-lg animate-bounce" style={{ animationDelay: '0.4s' }}>
        ✨ Beautiful
      </div>
    </div>
  );
};

export default CodeTypingAnimation;
