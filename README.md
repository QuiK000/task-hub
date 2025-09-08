[README.md](https://github.com/user-attachments/files/22214344/README.md)
# 📋 Task Hub - Task Management System

A comprehensive REST API for project and task management with JWT authentication, role-based authorization, and Redis caching.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![MongoDB](https://img.shields.io/badge/MongoDB-6.0-green)
![Redis](https://img.shields.io/badge/Redis-7.0-red)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based authentication with RSA encryption
- Role-based access control (Admin, Manager, User)
- Secure password validation with custom constraints
- Refresh token mechanism
- Disposable email validation

### 📊 Project Management
- Create, read, update, delete projects
- Project ownership validation
- Audit trail for all operations

### ✅ Task Management
- Full CRUD operations for tasks
- Task assignment to users
- Task status management (TODO, IN_PROGRESS, DONE)
- Project-based task organization

### 💬 Comments System
- Add comments to tasks
- MongoDB-based storage for flexible comment structure
- Automatic timestamp tracking

### ⚡ Performance & Caching
- Redis caching for frequently accessed data
- Configurable TTL for cache entries
- Cache eviction on data updates

### 📚 API Documentation
- Comprehensive Swagger/OpenAPI 3.0 documentation
- Interactive API testing interface
- Detailed request/response examples

## 🛠️ Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.x
- **Security**: Spring Security with JWT
- **Database**: 
  - PostgreSQL (Main database)
  - MongoDB (Comments)
  - Redis (Caching)
- **Build Tool**: Maven
- **Documentation**: Swagger/OpenAPI 3.0
- **Validation**: Jakarta Bean Validation with custom validators

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controllers   │────│    Services     │────│  Repositories   │
│                 │    │                 │    │                 │
│  - Auth         │    │  - Auth         │    │  - User         │
│  - Project      │    │  - Project      │    │  - Project      │
│  - Task         │    │  - Task         │    │  - Task         │
│  - User         │    │  - User         │    │  - Role         │
│  - Comment      │    │  - Comment      │    │  - Comment      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │              ┌─────────────────┐             │
         │              │     Cache       │             │
         └──────────────│   (Redis)       │─────────────┘
                        └─────────────────┘
                                │
                    ┌─────────────────────────────┐
                    │       Databases             │
                    │  ┌─────────┐ ┌─────────────┐│
                    │  │PostgreSQL│ │   MongoDB   ││
                    │  │(Main DB) │ │ (Comments)  ││
                    │  └─────────┘ └─────────────┘│
                    └─────────────────────────────┘
```

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven 3.8+
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/QuiK000/task-hub.git
cd task-hub
```

### 2. Start Database Services

```bash
# Start PostgreSQL, MongoDB, and Redis using Docker Compose
docker-compose up -d

# Verify services are running
docker-compose ps
```

### 3. Configure Application

Create `application-local.properties` in `src/main/resources/`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/taskhub
spring.datasource.username=postgres
spring.datasource.password=password

# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/taskhub

# Redis Configuration
spring.data.redis.host=localhost
spring.data.redis.port=6379

# JWT Configuration
app.security.jwt.access-token-expiration=900000
app.security.jwt.refresh-token-expiration=604800000

# Cache Configuration
app.cache.ttl-seconds=3600
```

### 4. Generate RSA Keys

```bash
# Create keys directory
mkdir -p src/main/resources/keys/local-only

# Generate private key
openssl genrsa -out src/main/resources/keys/local-only/private_key.pem 2048

# Generate public key
openssl rsa -in src/main/resources/keys/local-only/private_key.pem \
    -outform PEM -pubout -out src/main/resources/keys/local-only/public_key.pem
```

### 5. Build and Run

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run

# Or run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### 6. Access the Application

- **API Base URL**: http://localhost:8080/api/v1
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

## 🔧 Docker Compose Setup

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: taskhub
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  mongodb:
    image: mongo:6.0
    ports:
      - "27017:27017"
    volumes:
      - mongo_data:/data/db

  redis:
    image: redis:7.0
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data

volumes:
  postgres_data:
  mongo_data:
  redis_data:
```

## 📚 API Documentation

### Authentication Endpoints

```http
POST /api/v1/auth/register    # User registration
POST /api/v1/auth/login       # User login
POST /api/v1/auth/refresh     # Refresh access token
GET  /api/v1/auth/me          # Get current user info
```

### Project Endpoints

```http
GET    /api/v1/projects           # Get all projects
POST   /api/v1/projects           # Create new project
GET    /api/v1/projects/{id}      # Get project by ID
PATCH  /api/v1/projects/{id}      # Update project
DELETE /api/v1/projects/{id}      # Delete project
```

### Task Endpoints

```http
GET    /api/v1/projects/{projectId}/tasks    # Get tasks by project
POST   /api/v1/projects/{projectId}/tasks    # Create task
GET    /api/v1/tasks/{id}                    # Get task by ID
PATCH  /api/v1/tasks/{id}                    # Update task
DELETE /api/v1/tasks/{id}                    # Delete task
```

### Comment Endpoints

```http
GET  /api/v1/tasks/{taskId}/comments    # Get task comments
POST /api/v1/tasks/{taskId}/comments    # Add comment
```

### User Management

```http
GET    /api/v1/users           # Get all users
GET    /api/v1/users/{id}      # Get user by ID
PUT    /api/v1/users/{id}      # Update user
DELETE /api/v1/users/{id}      # Delete user
```

## 🧪 Testing

### Run Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AuthenticationServiceImplTest

# Run tests with coverage
mvn test jacoco:report
```

### Test Data

For testing purposes, default roles are automatically created:
- `ROLE_ADMIN`
- `ROLE_MANAGER` 
- `ROLE_USER`

### Sample API Calls

#### 1. Register User
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john@example.com",
    "password": "Password123!",
    "confirmPassword": "Password123!"
  }'
```

#### 2. Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "Password123!"
  }'
```

#### 3. Create Project (with JWT token)
```bash
curl -X POST http://localhost:8080/api/v1/projects \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "My First Project",
    "description": "Project description"
  }'
```

## 🔒 Security Features

### JWT Configuration
- RSA-256 encryption
- Access token expiration: 15 minutes
- Refresh token expiration: 7 days
- Stateless authentication

### Password Requirements
- Minimum 8 characters
- At least 1 uppercase letter
- At least 1 lowercase letter  
- At least 1 digit
- At least 1 special character

### API Security
- Protected endpoints require valid JWT
- Role-based access control
- Method-level security with @PreAuthorize
- CSRF protection disabled for API

## 📈 Performance Features

### Redis Caching
- Automatic caching of frequently accessed data
- Configurable TTL (default: 1 hour)
- Cache eviction on updates
- Separate cache for Users, Projects, and Tasks

### Database Optimization
- JPA entity relationships optimized with lazy loading
- Proper indexing on foreign keys
- Connection pooling with HikariCP

## 🐛 Error Handling

The API provides consistent error responses:

```json
{
  "success": false,
  "data": null,
  "error": "User not found",
  "code": "USER_NOT_FOUND",
  "validationErrors": []
}
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**QuiK000**
- GitHub: [@QuiK000](https://github.com/QuiK000)
- Email: kexitttttt@gmail.com

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Redis team for the caching solution
- MongoDB team for the document database
- All contributors and testers

---

⭐ If you find this project helpful, please give it a star on GitHub!
