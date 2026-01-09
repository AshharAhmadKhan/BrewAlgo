const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-gradient-to-r from-gray-900 via-gray-800 to-gray-900 text-white mt-auto">
      <div className="container mx-auto px-4 py-10">
        <div className="grid md:grid-cols-3 gap-8 mb-8">
          {/* Brand */}
          <div>
            <h3 className="text-2xl font-bold mb-4 gradient-text bg-gradient-to-r from-blue-400 to-purple-400 bg-clip-text">
              BrewAlgo
            </h3>
            <p className="text-gray-400 text-sm leading-relaxed">
              A production-grade algorithmic problem-solving platform built with Spring Boot & React.
            </p>
          </div>

          {/* Links */}
          <div>
            <h4 className="text-lg font-semibold mb-4">Quick Links</h4>
            <ul className="space-y-2 text-sm">
              <li><a href="#" className="text-gray-400 hover:text-blue-400 transition">About</a></li>
              <li><a href="#" className="text-gray-400 hover:text-blue-400 transition">Documentation</a></li>
              <li><a href="https://github.com" className="text-gray-400 hover:text-blue-400 transition" target="_blank" rel="noopener noreferrer">GitHub</a></li>
              <li><a href="mailto:itzashhar@gmail.com" className="text-gray-400 hover:text-blue-400 transition">Contact</a></li>
            </ul>
          </div>

          {/* Developer Info */}
          <div>
            <h4 className="text-lg font-semibold mb-4">Developer</h4>
            <p className="text-gray-400 text-sm mb-2">
              <span className="font-semibold text-white">Ashhar Ahmad Khan</span>
            </p>
            <p className="text-gray-500 text-xs mb-3">Lead Software Architect</p>
            <a 
              href="mailto:itzashhar@gmail.com" 
              className="inline-flex items-center space-x-2 text-blue-400 hover:text-blue-300 transition text-sm"
            >
              <svg className="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z" />
                <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z" />
              </svg>
              <span>itzashhar@gmail.com</span>
            </a>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="border-t border-gray-700 pt-6 flex flex-col md:flex-row justify-between items-center text-sm">
          <p className="text-gray-400 mb-4 md:mb-0">
            © {currentYear} BrewAlgo. Crafted with passion by <span className="text-blue-400 font-semibold">Ashhar Ahmad Khan</span>.
          </p>
          <div className="flex items-center space-x-2 text-gray-500">
            <span className="inline-flex items-center">
              <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
              Built with Spring Boot & React
            </span>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;