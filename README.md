# Task Manager – Spring Boot Project

A simple **Task Management System** built with **Spring Boot**, **PostgreSQL**, and **Maven**.  
This project demonstrates CRUD operations, validation, exception handling, pagination, sorting, and entity relationships.

---

## 📌 Features

- **User Management**
    - Create, update, fetch, and delete users
    - Store timezone and active status

- **Task Management**
    - Create, update, fetch, and delete tasks
    - Assign tasks to users
    - Store task status (`PENDING`, `IN_PROGRESS`, `COMPLETED`)

- **Validation**
    - Request DTOs validated using `jakarta.validation` annotations

- **Global Exception Handling**
    - Custom exceptions (`TaskNotFoundException`, `UserNotFoundException`)
    - Standardized JSON error response

- **Pagination & Sorting**
    - Tasks and Users can be fetched with pagination (`page`, `size`)
    - Sorting supported on fields like `title`, `createdAt`, `firstName`, etc.

- **PostgreSQL Integration**
    - Spring Data JPA for repository layer
    - Automatically generates schema with Hibernate

---

---

## 🗄️ Database Setup (PostgreSQL)

1. Install **PostgreSQL** on your system.
2. Create a database (example: `taskdb`):

```sql
CREATE DATABASE taskdb;
```
## Update your application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

## 🚀 Running the Project (IntelliJ IDEA)
1. Clone the repository
   ```bash
   git clone https://github.com/AviK1904/Task-Manager-Api.git
   cd Task-Manager
   ```
2. Open in IntelliJ IDEA
   
    File → Open → Select project root.
   
    IntelliJ will auto-detect Maven dependencies.

3. Build the Project

   From IntelliJ → Right click pom.xml → "Reload Project"

   Or run:

   ```bash
   mvn clean install
   ```

4. Run the application

   In IntelliJ → Right-click TaskManagerApplication → Run.

   Or run:

   ```bash
   mvn spring-boot:run
   ```

## ✅ Future Improvements

Add authentication (Spring Security + JWT)

Implement role-based access (Admin/User)

Deploy on cloud (Heroku/AWS)
