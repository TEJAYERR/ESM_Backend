# Event Stalls Management System (Backend)

A production-oriented backend application for managing events, stalls, bookings, and online payments. The system enables administrators to manage events and stalls while allowing the public to browse events and reserve stalls securely through Razorpay.

---

## Features

### Public

- View available events
- View event details
- View available stalls
- Book a stall
- Secure online payment using Razorpay
- Payment verification

### Admin

- Create, update, and manage events
- Open and close events
- Upload event blueprint
- Create and manage stalls
- View bookings
- Manage event availability

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Security
- PostgreSQL
- Maven
- Razorpay

---

## Project Structure

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── ...
│   │           ├── config
│   │           ├── controller
│   │           ├── dto
│   │           ├── entity
│   │           ├── exception
│   │           ├── repository
│   │           ├── service
│   │           └── util
│   └── resources
│       ├── application.properties
│       └── ...
```

---

## Core Modules

- Event Management
- Stall Management
- Booking Management
- Razorpay Payment Integration
- Payment Verification
- Global Exception Handling
- Security Configuration

---

## Security

- HTTP Basic Authentication for admin endpoints
- Role-based authorization
- Bean Validation for request validation
- Centralized exception handling using `@RestControllerAdvice`

---

## Payment Flow

1. User selects an available stall.
2. Backend validates:
   - Event is open
   - Stall is available
3. Booking is created.
4. Razorpay Order is generated.
5. User completes payment.
6. Backend verifies the payment signature.
7. Booking is confirmed and the stall is reserved.

Users who have already initiated the payment process are allowed to complete their payment even if the event is closed while the payment is in progress. Once an event is closed, no new bookings can be initiated.

---

## Running Locally

### Prerequisites

- Java 21+
- Maven
- PostgreSQL

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/event-stalls-management-backend.git
```

### 2. Navigate to the project

```bash
cd event-stalls-management-backend
```

### 3. Configure the application

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD

spring.jpa.hibernate.ddl-auto=update

razorpay.key.id=YOUR_RAZORPAY_KEY_ID
razorpay.key.secret=YOUR_RAZORPAY_KEY_SECRET
```

### 4. Run the application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

The backend will start on:

```
http://localhost:8080
```

---

## Build

Create the executable JAR:

```bash
mvn clean package
```

Run the generated JAR:

```bash
java -jar target/<application-name>.jar
```

---

## Future Improvements

- JWT Authentication
- Refresh Tokens
- Booking Cancellation
- Refund Management
- Email Notifications
- Search & Filtering
- Pagination & Sorting
- Docker Support
- CI/CD Pipeline
- Monitoring & Logging
- Admin Dashboard

---

## License

This project is available for learning, portfolio, and personal use.
