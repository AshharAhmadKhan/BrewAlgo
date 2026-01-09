import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useAuth } from '../context/AuthContext';
import Button from '../components/common/Button';

const Home = () => {
  const { isAuthenticated } = useAuth();

  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.15
      }
    }
  };

  const itemVariants = {
    hidden: { y: 30, opacity: 0 },
    visible: {
      y: 0,
      opacity: 1,
      transition: { duration: 0.6, ease: "easeOut" }
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4">
      {/* Hero Section with Rocket */}
      <motion.div
        initial={{ opacity: 0, y: -30 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 1, ease: "easeOut" }}
        className="text-center py-24 relative"
      >
        {/* Animated Rocket */}
        <motion.div
          animate={{ 
            y: [0, -15, 0],
            rotate: [0, 5, -5, 0]
          }}
          transition={{ 
            duration: 4, 
            repeat: Infinity,
            ease: "easeInOut"
          }}
          className="absolute top-0 right-1/4 text-6xl"
        >
          
        </motion.div>

        <motion.div
          initial={{ scale: 0.8, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          transition={{ duration: 0.8, delay: 0.2 }}
          className="mb-8"
        >
          <h1 className="text-7xl md:text-8xl font-black mb-6 leading-tight">
            Welcome to{' '}
            <span className="gradient-text bg-gradient-to-r from-blue-600 via-purple-600 to-indigo-700">
              BrewAlgo
            </span>
          </h1>
        </motion.div>

        <motion.p
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.5, duration: 0.8 }}
          className="text-2xl text-gray-700 mb-14 max-w-4xl mx-auto leading-relaxed font-light"
        >
          Master algorithmic problem-solving with our{' '}
          <span className="font-semibold text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600">
            interactive platform
          </span>
          . Compete in contests, track your progress, and climb the leaderboard.
        </motion.p>

        {!isAuthenticated && (
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.8 }}
            className="flex justify-center space-x-6"
          >
            <Link to="/register">
              <motion.div whileHover={{ scale: 1.05 }} whileTap={{ scale: 0.95 }}>
                <Button size="lg" className="btn-glow px-12 py-4 text-lg shadow-xl">
                  Get Started
                </Button>
              </motion.div>
            </Link>
            <Link to="/login">
              <motion.div whileHover={{ scale: 1.05 }} whileTap={{ scale: 0.95 }}>
                <Button 
                  variant="outline" 
                  size="lg" 
                  className="px-12 py-4 text-lg border-2 border-purple-600 text-purple-600 hover:bg-purple-50"
                >
                  Sign In
                </Button>
              </motion.div>
            </Link>
          </motion.div>
        )}
      </motion.div>

      {/* Features Section */}
      <motion.div
        variants={containerVariants}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: true, amount: 0.2 }}
        className="grid md:grid-cols-3 gap-10 py-20"
      >
        {[
          {
            icon: (
              <svg className="w-16 h-16 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4" />
              </svg>
            ),
            title: 'Practice Problems',
            description: 'Solve algorithmic challenges across multiple difficulty levels with detailed explanations and hints.',
            gradient: 'from-blue-500 to-cyan-500'
          },
          {
            icon: (
              <svg className="w-16 h-16 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            ),
            title: 'Live Contests',
            description: 'Compete against others in real-time contests and improve your competitive programming skills.',
            gradient: 'from-purple-500 to-pink-500'
          },
          {
            icon: (
              <svg className="w-16 h-16 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
              </svg>
            ),
            title: 'Track Progress',
            description: 'Monitor your performance with detailed analytics, ratings, and leaderboard rankings.',
            gradient: 'from-indigo-500 to-blue-500'
          }
        ].map((feature, index) => (
          <motion.div
            key={index}
            variants={itemVariants}
            whileHover={{ y: -8, scale: 1.02 }}
            className="glass-card p-10 rounded-3xl group cursor-pointer border border-gray-200/50"
          >
            <div className="mb-6 transform group-hover:scale-110 transition-transform duration-300">
              {feature.icon}
            </div>
            <h3 className="text-2xl font-bold mb-4 text-gray-900 group-hover:gradient-text transition-all duration-300">
              {feature.title}
            </h3>
            <p className="text-gray-600 leading-relaxed text-lg">
              {feature.description}
            </p>
            <div className={`h-1 w-0 group-hover:w-full bg-gradient-to-r ${feature.gradient} mt-6 transition-all duration-500 rounded-full`}></div>
          </motion.div>
        ))}
      </motion.div>

      {/* Stats Section */}
      <motion.div
        initial={{ opacity: 0, scale: 0.95 }}
        whileInView={{ opacity: 1, scale: 1 }}
        viewport={{ once: true }}
        transition={{ duration: 0.8 }}
        className="relative overflow-hidden glass-card rounded-3xl p-14 my-20 bg-gradient-to-br from-blue-600 via-purple-600 to-indigo-700 text-white shadow-2xl"
      >
        {/* Background Pattern */}
        <div className="absolute inset-0 opacity-10">
          <div className="absolute top-0 left-0 w-full h-full" 
               style={{backgroundImage: 'radial-gradient(circle, white 1px, transparent 1px)', backgroundSize: '50px 50px'}}></div>
        </div>

        <div className="relative grid md:grid-cols-4 gap-10 text-center">
          {[
            { value: '500+', label: 'Problems' },
            { value: '50K+', label: 'Users' },
            { value: '100+', label: 'Contests' },
            { value: '24/7', label: 'Support' }
          ].map((stat, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              transition={{ delay: index * 0.1 }}
              whileHover={{ scale: 1.08 }}
              className="transform transition-all"
            >
              <div className="text-6xl font-black mb-3">{stat.value}</div>
              <div className="text-blue-100 text-xl font-medium tracking-wide">{stat.label}</div>
            </motion.div>
          ))}
        </div>
      </motion.div>

      {/* CTA Section */}
      {isAuthenticated && (
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          className="text-center py-20"
        >
          <h2 className="text-5xl font-bold mb-8 gradient-text">Ready to Code?</h2>
          <p className="text-xl text-gray-600 mb-12 max-w-2xl mx-auto">
            Start solving problems or join a contest now and boost your skills!
          </p>
          <div className="flex justify-center space-x-6">
            <Link to="/problems">
              <motion.div whileHover={{ scale: 1.05 }} whileTap={{ scale: 0.95 }}>
                <Button size="lg" className="btn-glow px-12 py-4 text-lg shadow-xl">
                  Browse Problems
                </Button>
              </motion.div>
            </Link>
            <Link to="/contests">
              <motion.div whileHover={{ scale: 1.05 }} whileTap={{ scale: 0.95 }}>
                <Button 
                  variant="outline" 
                  size="lg" 
                  className="px-12 py-4 text-lg border-2 border-indigo-600 text-indigo-600 hover:bg-indigo-50"
                >
                  View Contests
                </Button>
              </motion.div>
            </Link>
          </div>
        </motion.div>
      )}

      {/* Professional Creator Credit */}
      <motion.div
        initial={{ opacity: 0 }}
        whileInView={{ opacity: 1 }}
        viewport={{ once: true }}
        className="text-center py-16 mt-20 border-t border-gray-200"
      >
        <div className="mb-6">
          <p className="text-sm text-gray-500 uppercase tracking-wider mb-2">Developed By</p>
          <h3 className="text-3xl font-bold gradient-text mb-2">
            Ashhar Ahmad Khan
          </h3>
          <p className="text-gray-600 text-lg">Lead Software Architect & Full-Stack Developer</p>
        </div>

        <div className="flex items-center justify-center space-x-6 mb-6">
          <div className="flex items-center space-x-2 text-gray-600">
            <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
              <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z" />
              <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z" />
            </svg>
            <a href="mailto:itzashhar@gmail.com" className="text-blue-600 hover:text-blue-700 font-semibold transition-colors">
              itzashhar@gmail.com
            </a>
          </div>
        </div>

        <div className="inline-flex items-center space-x-2 px-6 py-2 bg-orange-50 text-orange-700 rounded-full border border-orange-200">
          <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <span className="text-sm font-semibold">Under Active Development</span>
        </div>

        <p className="text-gray-500 mt-4 text-sm">
          Feedback and suggestions are always welcome!
        </p>
      </motion.div>
    </div>
  );
};

export default Home;