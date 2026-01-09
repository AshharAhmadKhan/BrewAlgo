const Footer = () => {
  return (
    <footer className="bg-gray-800 text-white mt-auto">
      <div className="container mx-auto px-4 py-6">
        <div className="flex flex-col md:flex-row justify-between items-center">
          <div className="mb-4 md:mb-0">
            <p className="text-sm">
              © 2026 BrewAlgo. Built with Spring Boot & React.
            </p>
          </div>
          <div className="flex space-x-6">
            <a href="#" className="text-sm hover:text-blue-400 transition">
              About
            </a>
            <a href="#" className="text-sm hover:text-blue-400 transition">
              Documentation
            </a>
            <a href="#" className="text-sm hover:text-blue-400 transition">
              GitHub
            </a>
            <a href="#" className="text-sm hover:text-blue-400 transition">
              Contact
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;