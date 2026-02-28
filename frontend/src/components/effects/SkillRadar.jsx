import { useEffect, useRef } from 'react';

const SkillRadar = ({ easy, medium, hard }) => {
  const canvasRef = useRef(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const maxRadius = 80;

    // Clear canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Draw background circles
    for (let i = 1; i <= 5; i++) {
      ctx.beginPath();
      ctx.arc(centerX, centerY, (maxRadius / 5) * i, 0, Math.PI * 2);
      ctx.strokeStyle = i === 5 ? '#e5e7eb' : '#f3f4f6';
      ctx.lineWidth = 1;
      ctx.stroke();
    }

    // Draw axes
    const angles = [0, (2 * Math.PI) / 3, (4 * Math.PI) / 3];
    const labels = ['Easy', 'Medium', 'Hard'];
    const colors = ['#10b981', '#f59e0b', '#ef4444'];

    angles.forEach((angle, index) => {
      const x = centerX + maxRadius * Math.cos(angle - Math.PI / 2);
      const y = centerY + maxRadius * Math.sin(angle - Math.PI / 2);

      // Draw axis line
      ctx.beginPath();
      ctx.moveTo(centerX, centerY);
      ctx.lineTo(x, y);
      ctx.strokeStyle = '#e5e7eb';
      ctx.lineWidth = 1;
      ctx.stroke();

      // Draw label
      const labelX = centerX + (maxRadius + 25) * Math.cos(angle - Math.PI / 2);
      const labelY = centerY + (maxRadius + 25) * Math.sin(angle - Math.PI / 2);
      ctx.fillStyle = colors[index];
      ctx.font = 'bold 12px sans-serif';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText(labels[index], labelX, labelY);
    });

    // Calculate skill values (normalize to 0-1)
    const maxProblems = Math.max(easy, medium, hard, 10);
    const values = [
      easy / maxProblems,
      medium / maxProblems,
      hard / maxProblems
    ];

    // Draw skill polygon
    ctx.beginPath();
    angles.forEach((angle, index) => {
      const radius = maxRadius * values[index];
      const x = centerX + radius * Math.cos(angle - Math.PI / 2);
      const y = centerY + radius * Math.sin(angle - Math.PI / 2);

      if (index === 0) {
        ctx.moveTo(x, y);
      } else {
        ctx.lineTo(x, y);
      }
    });
    ctx.closePath();

    // Fill with gradient
    const gradient = ctx.createRadialGradient(centerX, centerY, 0, centerX, centerY, maxRadius);
    gradient.addColorStop(0, 'rgba(59, 130, 246, 0.4)');
    gradient.addColorStop(1, 'rgba(139, 92, 246, 0.2)');
    ctx.fillStyle = gradient;
    ctx.fill();

    // Stroke
    ctx.strokeStyle = '#3b82f6';
    ctx.lineWidth = 2;
    ctx.stroke();

    // Draw points
    angles.forEach((angle, index) => {
      const radius = maxRadius * values[index];
      const x = centerX + radius * Math.cos(angle - Math.PI / 2);
      const y = centerY + radius * Math.sin(angle - Math.PI / 2);

      ctx.beginPath();
      ctx.arc(x, y, 4, 0, Math.PI * 2);
      ctx.fillStyle = colors[index];
      ctx.fill();
      ctx.strokeStyle = '#fff';
      ctx.lineWidth = 2;
      ctx.stroke();
    });

  }, [easy, medium, hard]);

  return (
    <div className="flex flex-col items-center">
      <canvas
        ref={canvasRef}
        width={240}
        height={240}
        className="drop-shadow-lg"
      />
      <div className="mt-4 flex items-center space-x-4 text-sm">
        <div className="flex items-center space-x-1">
          <div className="w-3 h-3 rounded-full bg-green-500"></div>
          <span className="text-gray-600">{easy} Easy</span>
        </div>
        <div className="flex items-center space-x-1">
          <div className="w-3 h-3 rounded-full bg-orange-500"></div>
          <span className="text-gray-600">{medium} Medium</span>
        </div>
        <div className="flex items-center space-x-1">
          <div className="w-3 h-3 rounded-full bg-red-500"></div>
          <span className="text-gray-600">{hard} Hard</span>
        </div>
      </div>
    </div>
  );
};

export default SkillRadar;
