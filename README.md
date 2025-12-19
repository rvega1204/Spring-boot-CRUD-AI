# Spring Boot CRUD Application with AI-Powered Learning Recommendations

A RESTful API built with Spring Boot for managing Software Engineers. This project demonstrates a complete CRUD (Create, Read, Update, Delete) application with **AI-powered personalized learning path generation** using Spring AI and Groq, plus comprehensive test coverage using Mockito.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Testing the API](#testing-the-api)
- [API Endpoints](#api-endpoints)
- [AI-Powered Learning Recommendations](#ai-powered-learning-recommendations)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [License](#license)

## Features

- RESTful API with full CRUD operations
- PostgreSQL database integration with JPA/Hibernate
- **AI-Powered Learning Recommendations** - Automatically generates personalized learning paths using Spring AI + Groq
- Intelligent tech stack analysis and career growth suggestions
- Data Transfer Objects (DTOs) for clean separation of concerns
- Global exception handling with custom error responses
- Comprehensive unit tests with Mockito (100% coverage)
- Health check endpoint for monitoring
- Virtual threads support for improved performance
- **Ready-to-use HTTP requests** for easy API testing

## Technologies

- **Java 21** with Virtual Threads
- **Spring Boot 3.5.9**
  - Spring Web
  - Spring Data JPA
  - Spring Boot Test
- **Spring AI 1.1.2** - AI integration framework
  - OpenAI-compatible API support (Groq)
  - LLaMA 3.3 70B model for intelligent recommendations
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
- **Groq API Key** (free at [https://console.groq.com](https://console.groq.com)) for AI features
- Git (optional, for cloning the repository)

## Installation

1. Clone the repository (or download the source code):
```bash
git clone https://github.com/rvega1204/Spring-boot-CRUD.git
cd Spring-boot-CRUD
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
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/postgres
DB_USERNAME=your_username
DB_PASSWORD=your_password

# AI Configuration (Groq API)
GROQ_API_KEY=your_groq_api_key_here
```

**How to get a Groq API Key:**
1. Visit [https://console.groq.com](https://console.groq.com)
2. Sign up for a free account
3. Navigate to API Keys section
4. Create a new API key
5. Copy the key to your `.env` file

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
GROQ_API_KEY=your_groq_api_key_here
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

## Testing the API

### Using the Included requests.http File

The project includes a [requests.http](requests.http) file with pre-configured HTTP requests for easy testing. This file works with popular HTTP client extensions in IDEs like:

- **VS Code**: [REST Client extension](https://marketplace.visualstudio.com/items?itemName=humao.rest-client)
- **IntelliJ IDEA**: Built-in HTTP Client
- **WebStorm**: Built-in HTTP Client

#### Available Requests in requests.http:

1. **GET All Engineers**
   ```http
   GET http://localhost:8080/api/v1/software-engineers
   ```

2. **GET Engineer by ID**
   ```http
   GET http://localhost:8080/api/v1/software-engineers/6
   ```

3. **POST Create New Engineer** (triggers AI recommendations)
   ```http
   POST http://localhost:8080/api/v1/software-engineers
   Content-Type: application/json

   {
     "name": "Alice Johnson",
     "techStack": ["Python", "Django", "PostgreSQL", "Docker", "AWS"]
   }
   ```

4. **PUT Update Engineer**
   ```http
   PUT http://localhost:8080/api/v1/software-engineers/2
   Content-Type: application/json

   {
     "name": "Bob Smith",
     "techStack": ["Java", "Spring Boot", "MySQL", "Kubernetes", "GCP"]
   }
   ```

5. **DELETE Engineer**
   ```http
   DELETE http://localhost:8080/api/v1/software-engineers/1
   ```

6. **Database Health Check**
   ```http
   GET http://localhost:8080/db-check
   ```

#### How to Use:

1. **Install REST Client** (if using VS Code):
   - Open VS Code Extensions (Ctrl+Shift+X)
   - Search for "REST Client"
   - Install the extension by Huachao Mao

2. **Open requests.http**:
   - Open the file in your IDE
   - You'll see "Send Request" links above each HTTP request

3. **Execute Requests**:
   - Click "Send Request" above any request
   - View the response in a new panel

4. **Modify as Needed**:
   - Change IDs, names, or tech stacks
   - All requests are ready to use!

### Alternative: Using cURL

```bash
# Get all engineers
curl http://localhost:8080/api/v1/software-engineers

# Get engineer by ID
curl http://localhost:8080/api/v1/software-engineers/1

# Create new engineer (with AI recommendations)
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Johnson",
    "techStack": ["Python", "Django", "PostgreSQL", "Docker", "AWS"]
  }'

# Update engineer
curl -X PUT http://localhost:8080/api/v1/software-engineers/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Johnson Updated",
    "techStack": ["Python", "Django", "PostgreSQL", "Docker", "AWS", "Kubernetes"]
  }'

# Delete engineer
curl -X DELETE http://localhost:8080/api/v1/software-engineers/1

# Health check
curl http://localhost:8080/db-check
```

### Alternative: Using Postman

1. Import the requests from [requests.http](requests.http) or create them manually
2. Set the base URL to `http://localhost:8080`
3. Use the endpoint paths and JSON bodies shown above

## API Endpoints

### Base URL: `/api/v1/software-engineers`

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/v1/software-engineers` | Get all software engineers | - | `200 OK` with array of engineers |
| GET | `/api/v1/software-engineers/{id}` | Get engineer by ID | - | `200 OK` with engineer or `404 Not Found` |
| POST | `/api/v1/software-engineers` | Create new engineer with AI recommendations | SoftwareEngineerDTO | `201 Created` with created engineer + AI learning path |
| PUT | `/api/v1/software-engineers/{id}` | Update existing engineer | SoftwareEngineerDTO | `200 OK` with updated engineer or `404 Not Found` |
| DELETE | `/api/v1/software-engineers/{id}` | Delete engineer | - | `204 No Content` or `404 Not Found` |

### Health Check

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| GET | `/health` | Application health status | `200 OK` with status message |
| GET | `/db-check` | Database connectivity check | `200 OK` with database info |

### Request/Response Examples

**Create Engineer (POST) - With AI Learning Path**
```json
{
  "name": "John Doe",
  "techStack": ["Java", "Spring Boot", "PostgreSQL"]
}
```

**Response (201 Created) - Including AI Recommendations**
```json
{
  "id": 1,
  "name": "John Doe",
  "techStack": ["Java", "Spring Boot", "PostgreSQL"],
  "learningPathRecommendations": "## 🚀 Next Skills to Learn (Top 3)\n\n1. **Docker & Kubernetes** - Containerization is essential for modern Spring Boot deployment...\n2. **Apache Kafka** - Event-driven architecture complements Spring Boot microservices...\n3. **AWS/Cloud Services** - Cloud deployment skills are highly valued...\n\n## 📖 Learning Path\n\n### Docker & Kubernetes\n- Free: Official Docker documentation...\n- Paid: Udemy - Docker Mastery...\n- Practice: Deploy your Spring Boot app in containers\n\n..."
}
```

**Get All Engineers (GET)**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "techStack": ["Java", "Spring Boot", "PostgreSQL"],
    "learningPathRecommendations": "..."
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "techStack": ["Python", "Django", "MongoDB"],
    "learningPathRecommendations": null
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

## AI-Powered Learning Recommendations

### How It Works

When you create a new Software Engineer via POST request, the application:

1. **Analyzes the tech stack** provided in the request
2. **Calls Spring AI** integrated with Groq's LLaMA 3.3 70B model
3. **Generates a personalized learning roadmap** including:
   - **Next Skills to Learn**: Top 3 complementary technologies based on current stack
   - **Learning Path**: Free resources, paid courses, and practice recommendations
   - **Portfolio Projects**: Beginner, intermediate, and advanced project ideas
4. **Stores recommendations** in the database with the engineer profile

### Example AI-Generated Recommendations

For a developer with `["Java", "Spring Boot", "PostgreSQL"]`, the AI generates:

```markdown
## 🚀 Next Skills to Learn (Top 3)

1. **Docker & Kubernetes** - Essential for containerization and orchestration
2. **Apache Kafka** - Event-driven architecture for microservices
3. **AWS/Cloud Services** - Cloud deployment and scalability

## 📖 Learning Path

### Docker & Kubernetes
- Best free resource: Official Docker documentation
- Top paid course: Udemy - Docker Mastery (Stephen Grider)
- Practice: Containerize your Spring Boot applications

### Apache Kafka
- Best free resource: Apache Kafka documentation + Confluent tutorials
- Top paid course: Kafka: The Definitive Guide (O'Reilly)
- Practice: Build event-driven microservices

## 🛠️ Portfolio Projects

1. **Beginner:** REST API with Docker deployment
2. **Intermediate:** Microservices with Kafka messaging
3. **Advanced:** Full-stack e-commerce platform with K8s orchestration
```

### Configuration

The AI service is configured in [application.properties](src/main/resources/application.properties):

```properties
# Spring AI Configuration (Groq)
spring.ai.openai.base-url=https://api.groq.com/openai
spring.ai.openai.api-key=${GROQ_API_KEY}
spring.ai.openai.chat.options.model=llama-3.3-70b-versatile
```

### Key Components

- **[AiService.java](src/main/java/com/rvg/ai/AiService.java)** - Service class for AI chat interactions
- **[SoftwareEngineerService.java](src/main/java/com/rvg/service/SoftwareEngineerService.java#L45)** - Integrates AI recommendations on engineer creation
- **Prompt Engineering** - Optimized prompt for actionable, concise learning recommendations

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
  - Tests AI service integration with mocked AI responses
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
│   │   │   ├── ai/
│   │   │   │   └── AiService.java ⭐ NEW - AI integration
│   │   │   ├── controller/
│   │   │   │   ├── HealthController.java
│   │   │   │   └── SoftwareEngineerController.java
│   │   │   ├── service/
│   │   │   │   └── SoftwareEngineerService.java (updated with AI)
│   │   │   ├── repository/
│   │   │   │   └── SoftwareEngineerRepository.java
│   │   │   ├── dto/
│   │   │   │   ├── SoftwareEngineerDTO.java (updated)
│   │   │   │   └── SoftwareEngineerMapper.java (updated)
│   │   │   ├── errors/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── NotFoundException.java
│   │   │   ├── data/
│   │   │   │   └── DataLoader.java
│   │   │   ├── SoftwareEngineer.java (updated with recommendations field)
│   │   │   └── Application.java
│   │   └── resources/
│   │       └── application.properties (updated with AI config)
│   └── test/
│       └── java/com/rvg/
│           ├── controller/
│           │   └── SoftwareEngineerControllerTest.java
│           ├── service/
│           │   └── SoftwareEngineerServiceTest.java
│           └── ApplicationTests.java
├── requests.http ⭐ NEW - Ready-to-use HTTP requests for testing
├── docker-compose.yml
├── pom.xml (updated with Spring AI dependencies)
├── .env (create this file with GROQ_API_KEY)
└── README.md
```

## Key Components

### Entity Layer
- **SoftwareEngineer** - JPA entity representing a software engineer with ID, name, tech stack, and AI-generated learning recommendations

### Repository Layer
- **SoftwareEngineerRepository** - Spring Data JPA repository for database operations

### Service Layer
- **SoftwareEngineerService** - Business logic layer handling CRUD operations with AI integration
- **AiService** - Service for interacting with AI models via Spring AI

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
7. Add corresponding requests to [requests.http](requests.http) for easy testing
8. Run tests to ensure everything works

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

### AI API Issues

**Groq API errors:**
1. Verify your API key is correct in `.env`
2. Check API key has not expired at [https://console.groq.com](https://console.groq.com)
3. Ensure you have API credits available (Groq offers generous free tier)
4. Check logs: `logging.level.org.springframework.ai=DEBUG`

**Model timeout:**
- LLaMA 3.3 70B typically responds in 2-5 seconds
- If timeout occurs, check network connectivity
- Review Spring AI logs for detailed error messages

**AI recommendations not generating:**
- Ensure GROQ_API_KEY is set in `.env`
- Check application logs for Spring AI errors
- Verify Groq service status
- Test API key with a simple cURL request to Groq API

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

## What's New in This Version

### ⭐ AI-Powered Features
- **Personalized Learning Paths**: Automatically generated recommendations based on tech stack
- **Spring AI Integration**: Seamless integration with Groq's LLaMA 3.3 70B model
- **Intelligent Analysis**: Smart career growth suggestions and project ideas

### 🧪 Easy Testing
- **requests.http File**: Pre-configured HTTP requests for instant API testing
- **IDE Integration**: Works with REST Client extensions in VS Code, IntelliJ, and WebStorm
- **No Setup Required**: Just open and click "Send Request"

### 🚀 Performance Improvements
- **Virtual Threads**: Enabled Java 21 virtual threads for better scalability
- **Optimized Data Loading**: Conditional initial data loading to avoid duplicates

### 🏗️ Architecture Updates
- **New AI Service Layer**: Clean separation of AI concerns
- **Enhanced Entity Model**: Added `learningPathRecommendations` field with proper JPA annotations
- **Updated DTOs**: Full support for AI-generated content

### 📝 Database Schema Changes
- New `learningPathRecommendations` column (TEXT) in `software_engineer` table
- Proper `@ElementCollection` for tech stack with join table
- Migration handled automatically via Hibernate DDL

## Quick Start Guide

### First Time Setup (5 minutes)

1. **Get your Groq API key**:
   ```bash
   # Visit https://console.groq.com and create a free account
   # Copy your API key
   ```

2. **Create .env file**:
   ```bash
   # Create .env in project root
   echo "DB_URL=jdbc:postgresql://localhost:5432/postgres" > .env
   echo "DB_USERNAME=your_username" >> .env
   echo "DB_PASSWORD=your_password" >> .env
   echo "GROQ_API_KEY=your_groq_api_key_here" >> .env
   ```

3. **Start everything**:
   ```bash
   docker-compose up -d    # Start database
   mvn spring-boot:run     # Start application
   ```

4. **Test the API**:
   - Open [requests.http](requests.http) in VS Code
   - Install REST Client extension if needed
   - Click "Send Request" on any HTTP request
   - Watch AI generate personalized learning paths!

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
- Spring AI team for the powerful AI integration framework
- Groq for providing fast, free LLM inference
- PostgreSQL community
- All contributors and students using this project for learning

---

**Happy Coding!** 🚀
