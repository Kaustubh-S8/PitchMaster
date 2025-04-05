# 🎤 PitchMaster - Spring Boot RESTful Application

**PitchMaster** is a robust backend application built with Spring Boot. It helps manage users, presentations, and ratings for a pitching platform. Designed with a RESTful architecture, it supports role-based access, user management, presentation evaluation, and feedback collection.

---

## 🚀 Core Features

### 👤 User Management
- **Registration & Login:** Secure user registration and login using `UserController`.
- **Role-based Access Control:** Differentiates between `STUDENT` and `ADMIN` roles.
- **Status Management:** Admins can update the activity status of users.

### 🖥️ Presentation Management
- **CRUD Operations:** Admins can add new presentations.
- **View Presentations:** Students can view their presentations.
- **Status Toggle:** Update presentation status (e.g., Approved/Rejected).

### ⭐ Rating System
- **Rate Presentations:** Users can rate presentations.
- **Fetch Ratings:** Retrieve ratings per presentation or per student.

---

## 📦 Technology Stack

| Frontend        | Backend         | Database     | API Architecture |
|----------------|------------------|--------------|------------------|
| —               | Spring Boot     | PostgreSql   | RESTful API      |

---

## 🔑 Endpoints Overview

### 🔐 UserController

| Method | Endpoint                            | Description                          |
|--------|-------------------------------------|--------------------------------------|
| POST   | `/user/register`                    | Register a new user                  |
| POST   | `/user/login`                       | Login with email and password        |
| GET    | `/user/Udetails?uid={uid}`          | Get logged-in user details           |
| GET    | `/user/Admin/{uid}/allDetails`      | Admin view of all registered users   |
| PUT    | `/user/Admin/{adminid}/changeStatus?uid={uid}&status={status}` | Admin changes user status  |

---

### 📽️ PresentationController

| Method | Endpoint                                                      | Description                                  |
|--------|---------------------------------------------------------------|----------------------------------------------|
| POST   | `/presentation/Admin/addPresentation/userid/{uid}`           | Admin adds a new presentation                |
| GET    | `/presentation/presentationById?pid={pid}`                   | Get presentation by ID                       |
| GET    | `/presentation/allPresentationByUid?uid={uid}`               | Get all presentations by user ID (Student)   |
| PUT    | `/presentation/{pid}/status?status={status}`                 | Change presentation status                   |

---

### 🌟 RatingController

| Method | Endpoint                                                                    | Description                             |
|--------|-----------------------------------------------------------------------------|-----------------------------------------|
| POST   | `/rating/rate/userid/{uid}/presentationid/{pid}`                           | Submit rating for a presentation        |
| GET    | `/rating/presentationRating/{pid}`                                         | View ratings for a specific presentation|
| GET    | `/rating/studentPresentationRating/{uid}`                                  | Get all ratings for a student           |

---

## ⚙️ Setup & Installation

### ✅ Prerequisites
- Java 17+
- Spring Boot
- Maven
- PostgreSql
- Postman (for testing)

### 🏗️ Configuration

Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pitchmaster_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 🛠️ Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

API Base URL:  
🔗 `http://localhost:8080`

---

## 🔐 Roles & Access

| Role     | Permissions                                             |
|----------|----------------------------------------------------------|
| STUDENT  | View presentations, view ratings                        |
| ADMIN    | Register presentations, manage users, change statuses   |

---

## 🧪 Testing with Postman

- Use JSON format for requests.
- Test endpoints for both roles (admin/student).
- Set `Content-Type: application/json` in headers.

---

## 📁 Project Structure

```plaintext
com.PitchMaster.controller
├── UserController.java
├── PresentationController.java
└── RatingController.java
com.PitchMaster.service
├── UserService.java
├── PresentationService.java
└── RatingService.java
com.PitchMaster.Exception
├── UserNotFound.java
├── PresentationNotFound.java
└── RatingNotFound.java
com.PitchMaster.entity
├── User.java
├── Presentation.java
└── Rating.java
```

---

## 📜 License

MIT License. Feel free to fork, contribute, or extend!

## 📧 Contact

**Kaustubh Satam**  
✉️ satamkaustubh@gmail.com  
🔗 [GitHub - Kaustubh-S8](https://github.com/Kaustubh-S8)

