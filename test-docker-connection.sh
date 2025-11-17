#!/bin/bash

# Test script to verify Docker Compose PostgreSQL setup
# This script tests the Docker Compose setup and application connection
#
# IMPORTANT: This script uses development defaults. These credentials are
# safe for local development only and are already visible in docker-compose.yml
# and README.md. For production, use environment variables.

set -e

# Configuration - Uses development defaults (matches docker-compose.yml)
# These are NOT production secrets - they're safe defaults for local development
POSTGRES_DB="${POSTGRES_DB:-sales_metrics_dev}"
POSTGRES_USER="${POSTGRES_USER:-sales_user}"
# Note: Password is not needed for psql commands with Docker exec
# Database password is only used by docker-compose.yml

echo "🧪 Testing Docker Compose PostgreSQL Setup"
echo "=========================================="
echo ""

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print success
success() {
    echo -e "${GREEN}✅ $1${NC}"
}

# Function to print error
error() {
    echo -e "${RED}❌ $1${NC}"
}

# Function to print warning
warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

# Check if Docker is installed
echo "1. Checking Docker installation..."
if command -v docker &> /dev/null; then
    success "Docker is installed: $(docker --version)"
else
    error "Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is available
echo ""
echo "2. Checking Docker Compose..."
if docker compose version &> /dev/null 2>&1; then
    COMPOSE_CMD="docker compose"
    success "Docker Compose v2 is available"
elif command -v docker-compose &> /dev/null; then
    COMPOSE_CMD="docker-compose"
    success "Docker Compose v1 is available"
else
    error "Docker Compose is not available. Please install Docker Compose."
    exit 1
fi

# Start Docker Compose
echo ""
echo "3. Starting Docker Compose services..."
$COMPOSE_CMD up -d

# Wait for PostgreSQL to be ready
echo ""
echo "4. Waiting for PostgreSQL to be ready..."
sleep 5

# Check if PostgreSQL container is running
echo ""
echo "5. Verifying PostgreSQL container is running..."
if $COMPOSE_CMD ps | grep -q "sales-metrics-postgres.*Up"; then
    success "PostgreSQL container is running"
else
    error "PostgreSQL container is not running"
    echo "Checking logs:"
    $COMPOSE_CMD logs postgres | tail -20
    exit 1
fi

# Check PostgreSQL health
echo ""
echo "6. Checking PostgreSQL health..."
if $COMPOSE_CMD exec -T postgres pg_isready -U "$POSTGRES_USER" -d "$POSTGRES_DB" &> /dev/null; then
    success "PostgreSQL is ready to accept connections"
else
    error "PostgreSQL is not ready"
    exit 1
fi

# Test database connection
echo ""
echo "7. Testing database connection..."
if $COMPOSE_CMD exec -T postgres psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "SELECT version();" &> /dev/null; then
    success "Database connection successful"
    echo "PostgreSQL version:"
    $COMPOSE_CMD exec -T postgres psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "SELECT version();" | head -3
else
    error "Database connection failed"
    exit 1
fi

# Check if database exists
echo ""
echo "8. Verifying database exists..."
if $COMPOSE_CMD exec -T postgres psql -U "$POSTGRES_USER" -lqt | cut -d \| -f 1 | grep -qw "$POSTGRES_DB"; then
    success "Database '$POSTGRES_DB' exists"
else
    error "Database '$POSTGRES_DB' does not exist"
    exit 1
fi

# Check if user exists and has permissions
echo ""
echo "9. Verifying user permissions..."
if $COMPOSE_CMD exec -T postgres psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "SELECT current_user, current_database();" &> /dev/null; then
    success "User '$POSTGRES_USER' has access to database"
else
    error "User '$POSTGRES_USER' does not have access"
    exit 1
fi

# Test data persistence (create a test table)
echo ""
echo "10. Testing data persistence..."
$COMPOSE_CMD exec -T postgres psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "CREATE TABLE IF NOT EXISTS test_persistence (id SERIAL PRIMARY KEY, created_at TIMESTAMP DEFAULT NOW());" &> /dev/null
$COMPOSE_CMD exec -T postgres psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "INSERT INTO test_persistence DEFAULT VALUES;" &> /dev/null
RECORD_COUNT=$($COMPOSE_CMD exec -T postgres psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -t -c "SELECT COUNT(*) FROM test_persistence;" | tr -d ' ')

if [ "$RECORD_COUNT" -gt "0" ]; then
    success "Data can be written (created test record)"
    
    # Restart container to test persistence
    echo ""
    echo "11. Testing data persistence across restarts..."
    $COMPOSE_CMD restart postgres
    sleep 5
    
    NEW_RECORD_COUNT=$($COMPOSE_CMD exec -T postgres psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -t -c "SELECT COUNT(*) FROM test_persistence;" | tr -d ' ')
    if [ "$NEW_RECORD_COUNT" -eq "$RECORD_COUNT" ]; then
        success "Data persists across container restarts"
    else
        error "Data was lost after restart"
        exit 1
    fi
    
    # Cleanup test table
    $COMPOSE_CMD exec -T postgres psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "DROP TABLE test_persistence;" &> /dev/null
else
    error "Failed to write data"
    exit 1
fi

# Check pgAdmin
echo ""
echo "12. Verifying pgAdmin service..."
if $COMPOSE_CMD ps | grep -q "sales-metrics-pgadmin.*Up"; then
    success "pgAdmin container is running"
    warning "Access pgAdmin at http://localhost:5050"
    warning "Login credentials are in docker-compose.yml (dev defaults only)"
else
    warning "pgAdmin container is not running (optional service)"
fi

# Final summary
echo ""
echo "=========================================="
success "All tests passed! Docker Compose setup is working correctly."
echo ""
echo "Next steps:"
echo "1. Start the Spring Boot application:"
echo "   mvn spring-boot:run -Dspring-boot.run.profiles=dev"
echo ""
echo "2. The application should connect to PostgreSQL automatically"
echo ""
echo "3. To stop Docker Compose:"
echo "   $COMPOSE_CMD down"
echo ""
echo "4. To stop and remove volumes (clean slate):"
echo "   $COMPOSE_CMD down -v"

