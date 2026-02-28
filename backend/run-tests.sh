#!/bin/bash

echo "=========================================="
echo "BrewAlgo - Comprehensive Test Suite"
echo "=========================================="
echo ""

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}Running all tests...${NC}"
echo ""

# Run Maven tests
cd "$(dirname "$0")"
./mvnw clean test

# Check exit code
if [ $? -eq 0 ]; then
    echo ""
    echo -e "${GREEN}=========================================="
    echo "✓ All tests passed successfully!"
    echo -e "==========================================${NC}"
    echo ""
    echo "Test Summary:"
    echo "  - Unit Tests: UserServiceTest, ProblemServiceTest, SubmissionServiceTest"
    echo "  - Integration Tests: UserController, ProblemController, SubmissionController"
    echo ""
else
    echo ""
    echo -e "${RED}=========================================="
    echo "✗ Some tests failed!"
    echo -e "==========================================${NC}"
    echo ""
    echo "Please check the output above for details."
    exit 1
fi

echo "Next steps:"
echo "  1. Review test coverage report"
echo "  2. Run manual testing checklist"
echo "  3. Fix any issues found"
echo ""
