# Spring Boot CRUD Application

A RESTful API built with Spring Boot for managing Software Engineers. This project demonstrates a complete CRUD (Create, Read, Update, Delete) application with comprehensive test coverage using Mockito.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [License](#license)

## Features

- RESTful API with full CRUD operations
- PostgreSQL database integration with JPA/Hibernate
- Data Transfer Objects (DTOs) for clean separation of concerns
- Global exception handling with custom error responses
- Comprehensive unit tests with Mockito (100% coverage)
- Health check endpoint for monitoring

## Technologies

- **Java 21**
- **Spring Boot 3.5.9**
  - Spring Web
  - Spring Data JPA
  - Spring Boot Test
- **PostgreSQL** - Database (via Docker Compose)
- **Docker & Docker Compose** - Container orchestration
- **Maven** - Build tool
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **dotenv-java** - Environment variable management

## Prerequisites

Before running this application, ensure you have:

- Java 21 or higher installed
- Maven 3.6+ installed
- Docker and Docker Compose installed (for database)
- Git (optional, for cloning the repository)

## Installation

1. Clone the repository (or download the source code):
```bash
git clone <repository-url>
cd spring-boot
```

2. Install dependencies:
```bash
mvn clean install
```

## Configuration

### Option 1: Using Docker Compose (Recommended)

1. The project includes a `docker-compose.yml` file that sets up PostgreSQL automatically.

2. Create a `.env` file in the project root directory:

```properties
DB_URL=jdbc:postgresql://localhost:5432/postgres
DB_USERNAME=ricardo
DB_PASSWORD=password
```

3. Start the PostgreSQL database using Docker Compose:

```bash
docker-compose up -d
```

This will:
- Create a PostgreSQL container named `postgres`
- Use PostgreSQL latest version
- Expose port 5432
- Create a persistent volume for data storage
- Auto-restart the container unless stopped

4. To stop the database:

```bash
docker-compose down
```

5. To stop and remove volumes (deletes all data):

```bash
docker-compose down -v
```

### Option 2: Using Local PostgreSQL

1. Install and run PostgreSQL locally

2. Create a `.env` file with your custom credentials:

```properties
DB_URL=jdbc:postgresql://localhost:5432/your_database_name
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

3. Ensure PostgreSQL is running and the database exists

## Running the Application

### Quick Start with Docker Compose

```bash
# 1. Start the database
docker-compose up -d

# 2. Run the application
mvn spring-boot:run
```

### Using Maven

```bash
mvn spring-boot:run
```

### Using Java

```bash
mvn clean package
java -jar target/spring-boot-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080` by default.

### Docker Compose Commands

```bash
# Start PostgreSQL in background
docker-compose up -d

# View logs
docker-compose logs -f

# Stop PostgreSQL
docker-compose down

# Restart PostgreSQL
docker-compose restart

# Check status
docker-compose ps

# Access PostgreSQL CLI
docker exec -it postgres psql -U ricardo -d postgres
```

## API Endpoints

### Base URL: `/api/v1/software-engineers`

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/v1/software-engineers` | Get all software engineers | - | `200 OK` with array of engineers |
| GET | `/api/v1/software-engineers/{id}` | Get engineer by ID | - | `200 OK` with engineer or `404 Not Found` |
| POST | `/api/v1/software-engineers` | Create new engineer | SoftwareEngineerDTO | `201 Created` with created engineer |
| PUT | `/api/v1/software-engineers/{id}` | Update existing engineer | SoftwareEngineerDTO | `200 OK` with updated engineer or `404 Not Found` |
| DELETE | `/api/v1/software-engineers/{id}` | Delete engineer | - | `204 No Content` or `404 Not Found` |

### Health Check

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| GET | `/health` | Application health status | `200 OK` with status message |

### Request/Response Examples

**Create Engineer (POST)**
```json
{
  "name": "John Doe",
  "techStack": ["Java", "Spring Boot", "PostgreSQL"]
}
```

**Response (201 Created)**
```json
{
  "id": 1,
  "name": "John Doe",
  "techStack": ["Java", "Spring Boot", "PostgreSQL"]
}
```

**Get All Engineers (GET)**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "techStack": ["Java", "Spring Boot", "PostgreSQL"]
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "techStack": ["Python", "Django", "MongoDB"]
  }
]
```

**Update Engineer (PUT)**
```json
{
  "name": "John Doe Updated",
  "techStack": ["Java", "Spring Boot", "PostgreSQL", "Docker"]
}
```

**Error Response (404 Not Found)**
```json
Engineer not found with id: 999
```

## Testing

This project includes comprehensive unit tests for both the service and controller layers.

### Run All Tests

```bash
mvn test
```

### Run Specific Test Classes

```bash
# Service layer tests
mvn test -Dtest=SoftwareEngineerServiceTest

# Controller layer tests
mvn test -Dtest=SoftwareEngineerControllerTest
```

### Test Coverage

- **SoftwareEngineerServiceTest** (9 tests)
  - Tests all CRUD operations with mocked repository
  - Exception handling scenarios
  - Edge cases (empty lists, non-existent IDs)

- **SoftwareEngineerControllerTest** (10 tests)
  - Tests all REST endpoints with MockMvc
  - HTTP status code validation
  - JSON response validation
  - Error handling and exception scenarios

**Total: 19 tests covering all functionality**

## Project Structure

```
spring-boot/
├── src/
│   ├── main/
│   │   ├── java/com/rvg/
│   │   │   ├── controller/
│   │   │   │   ├── HealthController.java
│   │   │   │   └── SoftwareEngineerController.java
│   │   │   ├── service/
│   │   │   │   └── SoftwareEngineerService.java
│   │   │   ├── repository/
│   │   │   │   └── SoftwareEngineerRepository.java
│   │   │   ├── dto/
│   │   │   │   ├── SoftwareEngineerDTO.java
│   │   │   │   └── SoftwareEngineerMapper.java
│   │   │   ├── errors/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── NotFoundException.java
│   │   │   ├── data/
│   │   │   │   └── DataLoader.java
│   │   │   ├── SoftwareEngineer.java
│   │   │   └── Application.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/rvg/
│           ├── controller/
│           │   └── SoftwareEngineerControllerTest.java
│           ├── service/
│           │   └── SoftwareEngineerServiceTest.java
│           └── ApplicationTests.java
├── docker-compose.yml
├── pom.xml
├── .env (create this file)
└── README.md
```

## Key Components

### Entity Layer
- **SoftwareEngineer** - JPA entity representing a software engineer with ID, name, and tech stack

### Repository Layer
- **SoftwareEngineerRepository** - Spring Data JPA repository for database operations

### Service Layer
- **SoftwareEngineerService** - Business logic layer handling CRUD operations with validation

### Controller Layer
- **SoftwareEngineerController** - REST controller exposing API endpoints
- **HealthController** - Health check endpoint for monitoring

### DTO Layer
- **SoftwareEngineerDTO** - Data Transfer Object for API communication
- **SoftwareEngineerMapper** - Maps between entity and DTO objects

### Error Handling
- **GlobalExceptionHandler** - Global exception handler with `@ControllerAdvice`
- **NotFoundException** - Custom exception for resource not found scenarios

## Development

### Adding New Features

1. Create/update entity in the model package
2. Create/update repository interface
3. Implement business logic in service layer
4. Create DTO and mapper if needed
5. Implement controller endpoint
6. Write unit tests for service and controller
7. Run tests to ensure everything works

### Code Quality

- Follow Java naming conventions
- Write JavaDoc comments for public methods
- Maintain test coverage above 80%
- Use DTOs to separate internal models from API contracts
- Handle exceptions gracefully with proper HTTP status codes

## Troubleshooting

### Database Connection Issues

If you encounter database connection errors:

**Using Docker Compose:**
1. Check if the container is running: `docker-compose ps`
2. View container logs: `docker-compose logs db`
3. Restart the container: `docker-compose restart`
4. Verify port 5432 is not used by another service: `netstat -ano | findstr :5432` (Windows) or `lsof -i :5432` (Mac/Linux)
5. Ensure `.env` credentials match those in `docker-compose.yml`:
   - Username: `your_username`
   - Password: `your_password`
   - Database: `postgres`

**Using Local PostgreSQL:**
1. Verify PostgreSQL service is running
2. Check database name, username, and password in `.env`
3. Ensure the database exists
4. Check PostgreSQL is listening on the correct port (default: 5432)

### Port Already in Use

**Application Port (8080):**
If port 8080 is already in use, add to `application.properties`:
```properties
server.port=8081
```

**Database Port (5432):**
If port 5432 is already in use by another PostgreSQL instance:
1. Stop the other PostgreSQL service
2. Or modify `docker-compose.yml` to use a different port:
```yaml
ports:
  - "5433:5432"  # Use port 5433 on host
```
Then update `.env`:
```properties
DB_URL=jdbc:postgresql://localhost:5433/postgres
```

### Docker Issues

**Container won't start:**
```bash
# Remove existing container and volumes
docker-compose down -v

# Pull latest PostgreSQL image
docker pull postgres:latest

# Start fresh
docker-compose up -d
```

**Permission denied errors:**
- On Linux/Mac: Ensure Docker daemon is running
- On Windows: Ensure Docker Desktop is running

### Test Failures

If tests fail:
1. Ensure all dependencies are installed: `mvn clean install`
2. Check that Mockito and JUnit versions are compatible
3. Review test output for specific error messages

## License

**MIT License - Free for Students and Educational Use**

Copyright (c) 2025 Ricardo Vega

---

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Support

For questions or issues:
- Open an issue in the repository
- Contact the maintainers
- Check existing documentation and tests for examples

## Acknowledgments

- Spring Boot team for the excellent framework
- PostgreSQL community
- All contributors and students using this project for learning

---

**Happy Coding!** 🚀
