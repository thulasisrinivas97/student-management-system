# Student Management System

## Overview

A Spring Boot REST API for managing students, courses, authentication,
authorization, student profiles, and course enrollment.

## Technologies

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- H2 Database
- Maven
- Swagger / OpenAPI

## Features

### Authentication
- Admin login using username and password
- Student validation using student code and date of birth
- JWT-based authentication
- Role-based authorization

### Admin
- Admit a student
- Create courses
- View all courses
- Search students by name
- View students enrolled in a course
- Assign a course to a student

### Student
- View own profile
- Update own profile
- View enrolled courses
- Leave a course

## Security

The application uses JWT authentication.

Two roles are supported:

- ADMIN
- STUDENT

Admin endpoints require the `ADMIN` role.

Student endpoints require the `STUDENT` role.

Protected requests require:

Authorization: Bearer <JWT_TOKEN>

## Prerequisites

- Java installed
- Maven installed

Verify:

java -version
mvn -version

## Running the Application

Clone the repository:

git clone <YOUR_GITHUB_REPOSITORY_URL>

Navigate to the project:

cd student-management-system

Start the application:

mvn spring-boot:run

The application runs on:

http://localhost:8080

## Swagger UI

Swagger UI:

http://localhost:8080/swagger-ui/index.html

OpenAPI documentation:

http://localhost:8080/v3/api-docs

## Authentication APIs

### Admin Login

POST /api/auth/admin/login

Example:

{
  "username": "admin",
  "password": "<ADMIN_PASSWORD>"
}

### Student Validation

POST /api/auth/student/validate

Example:

{
  "studentCode": "STU001",
  "dateOfBirth": "1997-08-12"
}

Both endpoints return a JWT token on successful authentication.

## Admin APIs

POST /api/admin/students

POST /api/admin/courses

GET /api/admin/courses

GET /api/admin/students/search?name=<name>

POST /api/admin/students/{studentId}/courses/{courseId}

GET /api/admin/courses/{courseId}/students

## Student APIs

GET /api/students/me/profile

PUT /api/students/me/profile

DELETE /api/students/me/courses/{courseId}

## Testing

The following functionality was tested successfully:

- Admin authentication
- Student authentication
- JWT authentication
- Student profile retrieval
- Student profile update
- Course creation
- Course retrieval
- Course assignment
- Students enrolled in a course
- Student leaving a course
- Student attempting to access admin APIs
- Protected endpoint access without a token
- Invalid JWT access

## Authorization

Admin users can access admin endpoints.

Student users can access student endpoints.

A student attempting to access an admin endpoint receives:

403 Forbidden

Unauthenticated access to protected endpoints is rejected.

## Project Structure

src/
├── main/
│   ├── java/
│   │   └── com/platformcommons/sms/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── repository/
│   │       ├── security/
│   │       └── service/
│   └── resources/
│
├── pom.xml
└── README.md