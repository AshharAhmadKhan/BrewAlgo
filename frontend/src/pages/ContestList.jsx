import { useState, useEffect } from 'react';
import Loading from '../components/common/Loading';

const ContestList = () => {
  const [loading] = useState(false);

  if (loading) return <Loading />;

  return (
    <div className="max-w-6xl mx-auto">
      <h1 className="text-4xl font-bold mb-8">Contests</h1>
      
      <div className="bg-blue-50 border border-blue-200 rounded-lg p-6 text-center">
        <p className="text-lg text-gray-700">
          Contest feature coming soon! 🏆
        </p>
        <p className="text-sm text-gray-600 mt-2">
          Real-time contests with live leaderboards will be available shortly.
        </p>
      </div>
    </div>
  );
};

export default ContestList;