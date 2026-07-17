# AI Complaint Intelligence Platform

## Overview
This project is structured as a BMAD microservice platform for complaint intelligence.

- **B — Business domains**
  - Complaint intake
  - Complaint classification
  - Analytics / reporting
  - Frontend user experience
  - Database persistence

- **M — Microservices**
  - `services/complaint-service`: complaint API and persistence
  - `services/analysis-service`: analysis / classification service (skeleton)
  - `frontend/`: React-based frontend UI
  - `postgres`: PostgreSQL database running via Docker Compose

- **A — APIs / communication**
  - `complaint-service` exposes REST endpoints for complaint creation, retrieval, and status updates
  - `analysis-service` is prepared for analysis APIs and service-to-service integration
  - `frontend` can call `complaint-service` APIs for UI actions

- **D — Data**
  - PostgreSQL is the primary datastore
  - Complaint entity persists title, description, category, status, created/updated timestamps
  - Rule-based classification is implemented in `complaint-service` for category assignment

## Project structure

- `docker-compose.yml` — container definitions for services and PostgreSQL
- `frontend/` — Vite + React frontend app
- `services/complaint-service/` — Spring Boot complaint microservice
- `services/analysis-service/` — Spring Boot analysis microservice skeleton
- `pom.xml` — root Maven aggregator

## Complaint service packages

- `model` — JPA entities and enums
- `repository` — Spring Data repositories
- `dto` — request and response DTOs
- `service` — business logic and classification rules
- `controller` — REST APIs
- `exception` — global error handling

## APIs

### Complaint service

- `POST /complaints`
  - Create a complaint
  - Request body: `title`, `description`

- `GET /complaints/{id}`
  - Get complaint by ID

- `GET /complaints`
  - Get all complaints

- `PATCH /complaints/{id}/status`
  - Update complaint status
  - Request body: `status`

## How to run

### Prerequisites

- Docker & Docker Compose
- Java 17 or newer for local Maven builds
- Maven

### Start with Docker Compose

```bash
docker compose up --build
```

This will start:
- `complaint-service` on port `8080`
- `analysis-service` on port `8081`
- `frontend` on port `4173`
- `postgres` on port `5432`

### Run complaint service locally

```bash
cd services/complaint-service
mvn spring-boot:run
```

If running locally, ensure Postgres is available and environment variables are set:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/complaintsdb
export SPRING_DATASOURCE_USERNAME=ai_user
export SPRING_DATASOURCE_PASSWORD=ai_pass
```

## Notes

- The complaint classification currently uses keyword rules and can be replaced later with AI/NLP logic.
- Tests are configured with JUnit 5, Mockito, and Testcontainers for PostgreSQL integration.
- The root Maven `pom.xml` is an aggregator that includes service modules.

## Next improvements

- Add `analysis-service` endpoints for asynchronous complaint processing
- Add API security / authentication
- Add dashboard analytics endpoints and UI integration
- Replace rule-based classification with an NLP/AI model
