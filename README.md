# HobbyHub Backend – Spring Boot API

HobbyHub is a social platform for connecting people through shared hobbies and interest-based groups. This backend is built using **Java 17**, **Spring Boot**, and **PostgreSQL**.


##  Features

- User Registration & Authentication
- Hobby selection and tracking
- Join hobby-based groups
- View events per group
- Tag-based recommendations for hobbies and groups
- REST API design with clean separation of concerns

---

##  Tech Stack

| Layer       | Technology       |
|------------|------------------|
| Language    | Java 17          |
| Framework   | Spring Boot 3.5.3 |
| ORM         | Hibernate + JPA  |
| Database    | PostgreSQL       |
| Connection  | HikariCP         |
| Packaging   | Maven            |
| Deployment  | Render (planned) |

---

## 🗃️ Database Models (Entities)

### User
- `id`: Long (PK)
- `name`: String
- `email`: String
- `password`: String

### Hobby
- `id`: Long (PK)
- `name`: String
- `description`: String

### Group
- `id`: Long (PK)
- `title`: String
- `description`: String
- `hobby_id`: FK → Hobby

### Event
- `id`: Long (PK)
- `title`: String
- `description`: String
- `date`: Date
- `location`: String
- `group_id`: FK → Group

### Tag
- `id`: Long (PK)
- `name`: String

### User_Hobby (Join Table)
- `user_id`, `hobby_id`

### Hobby_Tag (Join Table)
- `hobby_id`, `tag_id`

---

## 🌐 API Endpoints

| Method | Route                            | Description                          |
|--------|----------------------------------|--------------------------------------|
| GET    | `/users/{id}/hobbies`            | Get user’s selected hobbies          |
| POST   | `/users/{id}/hobbies`            | Add hobbies to a user                |
| GET    | `/groups/{hobbyId}`              | Fetch groups for a hobby             |
| POST   | `/groups/{hobbyId}/join`         | Join a group                         |
| GET    | `/groups/{groupId}/events`       | Get all events in a group            |
| GET    | `/users/{id}/recommendations`    | Get recommended hobbies based on tags|

---

Run the Application
  
   ./mvnw spring-boot:run

Testing
    ./mvnw test

🚀 Future Enhancements
JWT-based authentication

Push notifications for upcoming events

AI-assisted hobby tagging

Chat between group members

Admin dashboard for moderation

📚 Learning Highlights
Built robust RESTful APIs with Spring Boot

Designed relational models using PostgreSQL with JPA

Implemented many-to-many join tables for flexible hobby-group-user relations

Designed scalable backend architecture

Improved backend-debugging and API testing skills