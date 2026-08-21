# Student Management System

## Overview

A Spring Boot REST API for managing students, courses, authentication, authorization, student profiles, and course enrollment.

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

- `ADMIN`
- `STUDENT`

Admin endpoints require the `ADMIN` role.

Student endpoints require the `STUDENT` role.

Protected requests require:

```text
Authorization: Bearer <JWT_TOKEN>