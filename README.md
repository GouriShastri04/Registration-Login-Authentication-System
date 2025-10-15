# Registration & Authentication System

A **User Registration and Authentication System** built with **Spring Boot**, **JWT**, and **MySQL**. Developed during my internship to demonstrate backend development skills including secure authentication, email verification, password reset, and user interaction features.

---

## Features
- User Registration with email and password  
- JWT Authentication for secure login  
- Email Verification to activate accounts  
- Password Reset via email link  
- Consultation Scheduling with date and time  
- Contact Form to receive messages  
- Stateless Session Management using Spring Security  

---

## Technologies Used
Spring Boot, Spring Security, MySQL, JPA, Hibernate, JWT, JavaMailSender, Maven, Java 17

---

## Setup
1. Clone the repository:  
```bash
git clone https://github.com/GouriShastri04/Registration-Login-Authentication-System.git
Configure application.properties with your MySQL database and email credentials

---

**## API Endpoints**
POST /api/auth/register → Register a new user

POST /api/auth/login → Login and receive JWT token

GET /api/auth/verify → Verify email

POST /api/auth/forgot-password → Request password reset

POST /api/auth/reset-password → Reset password using token

POST /api/auth/consultation/schedule → Schedule consultation

POST /api/auth/contact → Submit contact message

**Learnings**
-Implemented JWT-based authentication and stateless sessions
-Integrated email verification and password reset functionality
-Built RESTful APIs using Spring Boot and JPA
-Hands-on experience with Spring Security, MySQL, and backend architecture
