# HobbyHub Backend – Spring Boot API

HobbyHub is a social platform for connecting people through shared hobbies and interest-based groups.

This backend is built with **Java 17**, **Spring Boot 3.5.3**, and **PostgreSQL**, and is deployed on **Render**.

- **Live API (Render):** `https://hobbyhub-backend-whhm.onrender.com`
- **Frontend (GitHub Pages):** `https://kgitika.github.io/hobbyhub-frontend/`

> Auth uses JWT. Public endpoints: /auth/signup, /auth/login, /actuator/health.
>
>
> Everything else requires `Authorization: Bearer <token>`.
>

---

## Features

- **User registration & authentication** (JWT)
- **User profile** via `/users/me`
- **Hobby selection and tracking**
- **Groups**: join hobby-based groups
- **Events** per group & RSVP
- **Recommendations** (hobbies/groups/events)
- **CORS** configured for local dev and GitHub Pages
- Placeholder: `/activities/recent` (returns empty list for now)

---

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 3.5.3 |
| ORM | Hibernate / JPA |
| Database | PostgreSQL |
| Pooling | HikariCP |
| Build | Maven (wrapper included) |
| Packaging | Fat JAR (Spring Boot) |
| Deploy | Render (Docker) |
| Docs | Minimal `openapi.yaml` (optional) |

---

## Project Structure (High-Level)

**`src/main/java/com/hobbyhub/hobbyhub/`**

- **security/** – `SecurityConfig`, `JwtAuthFilter`
- **config/** – Global application configuration (e.g., CORS settings) *(if present)*
- **controllers/** – REST API controllers
- **services/** – Business logic layer
- **repositories/** – Spring Data JPA repositories
- **entities/** – JPA entity classes
- **logging/** – `RequestResponseLoggingFilter` and related logging utilities
- **HobbyHubApplication.java** – Main application entry point

**`src/main/resources/`**

- **application.properties** – Default configuration
- **application-local.properties** – Local development configuration
- **application-prod.properties** – Production configuration

---

## Key Endpoints

> Public
>
- `POST /auth/signup` — create account
- `POST /auth/login` — returns JWT
- `GET /actuator/health` — health probe

> Authenticated (Authorization: Bearer <token>)
>
- `GET /users/me` — current user profile
- `GET /users/{id}/hobbies` — user’s selected hobbies
- `POST /users/{id}/hobbies` — set/update hobbies (e.g., `{"hobbyIds":[1,2,3]}`)
- `GET /groups/{hobbyId}` — groups for a hobby
- `POST /groups` — create group
- `POST /groups/{hobbyId}/join` — join a group
- `GET /groups/{groupId}/events` — events in a group
- `GET /users/{id}/recommendations` — hobby recommendations
- `GET /recommendations/groups` — group recommendations *(TODO)*
- `GET /activities/recent` — recent activity *(placeholder, empty list)*

---

## Configuration

### Profiles

- **local**: for development on your machine
- **prod**: for Render

Set with `SPRING_PROFILES_ACTIVE=local|prod`.

### CORS (already configured)

Allowed origins:

- `http://localhost:5173`
- `http://127.0.0.1:5173`
- `https://kgitika.github.io` (GitHub Pages frontend)

### 

## Security & CORS

- **SecurityConfig**: stateless JWT, public auth routes, protected APIs.
- **CORS**: allows `http://localhost:5173`, `http://127.0.0.1:5173`, `https://kgitika.github.io`.
- **Request/Response logging**: `RequestResponseLoggingFilter` added **before** auth filter.

> Consider storing secrets in environment variables (e.g., JWT_SECRET) and rotating them regularly.
>

---

## Database Model (Representative)

> Actual table names/relations come from your JPA entities; below is a typical shape.
>
- **User**: `id`, `name`, `email` (unique), `password`
- **Hobby**: `id`, `name`, `description`
- **Group**: `id`, `title`, `description`, `hobby_id → Hobby`
- **Event**: `id`, `title`, `description`, `date`, `location`, `group_id → Group`
- **Tag**: `id`, `name`
- **user_hobby**: `user_id`, `hobby_id`
- **hobby_tag**: `hobby_id`, `tag_id`

## Future Enhancements

- Finalize **Connections** feature (frontend already hides the UI)
- Push notifications for upcoming events
- AI-assisted hobby tagging
- Group chat
- Admin moderation tools

---

## Learning Highlights

- Robust, layered REST API with Spring Boot
- Relational modeling with JPA & PostgreSQL (many-to-many joins)
- JWT authentication & stateless security
- Dockerized build and cloud deployment on Render
- Operational skills: logs, CORS, schema management, data migration