# CRM Ticketing API

A RESTful CRM Ticketing System built using Spring Boot, Hibernate SessionFactory, MySQL, HikariCP, Swagger OpenAPI, SLF4J Logging, and Logback.

The application allows support teams to manage Agents, Tickets, and Comments through a layered architecture following enterprise Java development practices.

---

## Project Overview

The CRM Ticketing API is designed to simulate a customer support ticket management platform where:

- Customers raise support tickets
- Tickets are assigned to support agents
- Agents work on tickets and add comments
- Tickets can be tracked and managed throughout their lifecycle

---

## Business Workflow

```text
Create Ticket
      ↓
Assign Agent
      ↓
Add Comments
      ↓
Track Progress
      ↓
Resolve Ticket
      ↓
Close Ticket
```

---

# Technology Stack

| Technology | Purpose |
|------------|----------|
| Java 17 | Programming Language |
| Spring Boot | Application Framework |
| Hibernate ORM | Database Persistence |
| Hibernate SessionFactory | ORM Session Management |
| MySQL | Relational Database |
| HikariCP | Database Connection Pool |
| Maven | Build Tool |
| Lombok | Boilerplate Code Reduction |
| Swagger OpenAPI | API Documentation |
| SLF4J | Logging Facade |
| Logback | Logging Implementation |

---

# Project Architecture

```text
Controller Layer
       ↓
Service Layer
       ↓
DAO Layer
       ↓
Hibernate SessionFactory
       ↓
MySQL Database
```

---

# Package Structure

```text
src/main/java/org/crm/crmticketingapi

├── controller
│   ├── AgentController
│   ├── TicketController
│   └── CommentController
│
├── service
│   ├── AgentService
│   ├── TicketService
│   └── CommentService
│
├── service/impl
│   ├── AgentServiceImpl
│   ├── TicketServiceImpl
│   └── CommentServiceImpl
│
├── dao
│   ├── AgentDao
│   ├── TicketDao
│   └── CommentDao
│
├── dao/impl
│   ├── AgentDaoImpl
│   ├── TicketDaoImpl
│   └── CommentDaoImpl
│
├── entity
│   ├── Agent
│   ├── Ticket
│   └── Comment
│
├── dto
│   └── request
        └── CreateAgentRequest
        └── CreateCommentRequest
        └── CreateTicketRequest
    └── response
        └── ErrorResponse
│
├── config
│   ├── DataSourceConfig
│   ├── HibernateConfig
│   └── TransactionConfig
    └── SwaggerConfig
│
├── exception
│   ├── ResourceNotFoundException
│   └── GlobalExceptionHandler
│
└── enums
    └── Department
    └── IssueType
    └── Priority
    └── TicketStatus
```

---

# Features Implemented

## Agent Management

- Create Agent
- Get Agent By ID
- Get All Agents
- Update Agent
- Delete Agent

---

## Ticket Management

- Create Ticket
- Get Ticket By ID
- Get All Tickets
- Update Ticket
- Delete Ticket

---

## Comment Management

- Create Comment
- Get Comment By ID
- Get All Comments
- Update Comment
- Delete Comment

---

# CRUD Operations

| Entity | Create | Read | Update | Delete |
|----------|----------|----------|----------|----------|
| Agent | ✅ | ✅ | ✅ | ✅ |
| Ticket | ✅ | ✅ | ✅ | ✅ |
| Comment | ✅ | ✅ | ✅ | ✅ |

---

# Requirements Coverage

## 1. Enums 

Implemented domain-specific enums for maintaining controlled values.

### Department

```java
public enum Department {
    TECHNICAL,
    BILLING,
    SALES,
    SUPPORT
}
```

### TicketStatus

```java
public enum TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED
}
```

### Priority

```java
public enum Priority {
    LOW,
    MEDIUM,
    HIGH
}
```

### IssueType

```java
public enum IssueType {
    GENERAL,
    BUG,
    PAYMENT,
    TECHNICAL
}
```

---

## 2. Validations 

Bean Validation annotations added to entities and DTOs.

Examples:

```java
@NotBlank
private String name;

@Email
private String email;

@NotNull
private Department department;
```

Validation ensures:

- Mandatory fields are provided
- Email formats are valid
- Null values are prevented
- Data consistency is maintained

---

## 3. Service Layer 

Business logic is separated from controllers.

Example responsibilities:

### AgentService

- Create agent
- Retrieve agents
- Delete agents
- Validation handling

### TicketService

- Create tickets
- Assign agents
- Update ticket counts

### CommentService

- Create comments
- Validate ticket existence
- Validate agent existence

---

## 4. Swagger Documentation 

Integrated using SpringDoc OpenAPI.

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

Provides:

- API testing interface
- Endpoint documentation
- Request schemas
- Response schemas

---

## 5. Logback Configuration 

Implemented custom Logback configuration.

### Why Logback?

- Production-grade logging
- Configurable log formats
- File logging support
- Log level management

Location:

```text
src/main/resources/logback.xml
```

---

## 6. Database Indexes 

Indexes added for frequently queried columns.

Example:

```java
@Table(
    name = "agents",
    indexes = {
        @Index(
            name = "idx_agent_department",
            columnList = "department"
        )
    }
)
```

Database verification:

```sql
SHOW INDEX FROM agents;
```

Benefits:

- Faster search operations
- Better query performance
- Optimized filtering

---

## 7. SLF4J Logging 

Implemented logging across Service Layer.

Example:

```java
logger.info(
    "Creating agent with email {}",
    request.getEmail()
);

logger.warn(
    "Agent not found with id {}",
    id
);
```

Benefits:

- Request tracking
- Debugging support
- Error monitoring
- Production observability

---

# Global Exception Handling

Implemented centralized exception handling.

### Custom Exception

```java
ResourceNotFoundException
```

### Global Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler
```

Sample Error Response:

```json
{
  "timestamp": "2026-06-09T22:40:44",
  "status": 404,
  "error": "Not Found",
  "message": "Agent not found with id : 999"
}
```

---

# Database Configuration

Configured using:

### HikariCP

```java
HikariDataSource
```

### Hibernate SessionFactory

```java
LocalSessionFactoryBean
```

### Transaction Management

```java
HibernateTransactionManager
```

---

# REST API Endpoints

## Agents

```http
POST   /api/agents
GET    /api/agents
GET    /api/agents/{id}
PUT    /api/agents/{id}
DELETE /api/agents/{id}
```

---

## Tickets

```http
POST   /api/tickets
GET    /api/tickets
GET    /api/tickets/{id}
PUT    /api/tickets/{id}
DELETE /api/tickets/{id}
```

---

## Comments

```http
POST   /api/comments
GET    /api/comments
GET    /api/comments/{id}
PUT    /api/comments/{id}
DELETE /api/comments/{id}
```

---

# Dependency Injection

Constructor Injection is used throughout the project.

Example:

```java
@RequiredArgsConstructor(
    onConstructor = @__(@Autowired)
)
public class AgentServiceImpl {

    private final AgentDao agentDao;
}
```

No field injection is used.

---

# Configuration Files

```text
application.properties
```

Contains:

- Database configuration
- Hibernate properties
- HikariCP settings

```text
pom.xml
```

Contains:

- Spring Boot dependencies
- Hibernate
- Lombok
- Swagger
- MySQL
- Validation
- Logging

---

# Project Status

| Requirement | Status |
|------------|---------|
| Hibernate SessionFactory | ✅ |
| HikariCP | ✅ |
| Spring Boot Configuration | ✅ |
| Controller Layer | ✅ |
| Service Layer | ✅ |
| DAO Layer | ✅ |
| CRUD Operations | ✅ |
| Enums | ✅ |
| Validations | ✅ |
| Swagger Documentation | ✅ |
| Global Exception Handling | ✅ |
| SLF4J Logging | ✅ |
| Logback XML | ✅ |
| Database Indexes | ✅ |
| Response Status Codes | ✅ |

---

# Author

**Chaitanya Vinjamuri**

Software Developer Trainee
