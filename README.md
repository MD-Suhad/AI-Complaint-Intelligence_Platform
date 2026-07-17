# AI Complaint Intelligence Platform

## Overview
This repository is a BMAD microservice prototype for an intelligent complaint management system.
It targets complaint intake, AI classification, duplicate detection, priority routing, and analytics.

- **B — Business domains**
  - Complaint intake and tracking
  - AI-driven classification and sentiment analysis
  - Priority/SLA routing and escalation
  - Real-time dashboards and notifications
  - Transactional storage for audit and reporting

- **M — Microservices**
  - `services/complaint-service`: complaint CRUD, status, and persistence
  - `services/analysis-service`: AI/NLP classification service skeleton
  - `frontend/`: React/Vite user interface
  - `postgres`: PostgreSQL database for complaint storage

- **A — APIs / communication**
  - `complaint-service` exposes REST endpoints for complaint creation, retrieval, and updates
  - `analysis-service` is meant to handle classification and enrichment asynchronously
  - `frontend` interacts with backend APIs and displays complaint dashboards

- **D — Data**
  - PostgreSQL stores complaints, statuses, and audit metadata
  - Future expansion can add pgvector / Elasticsearch for semantic deduplication and search
  - Kafka or event streaming can be introduced for ingestion and asynchronous processing

## Project structure

- `docker-compose.yml` — service composition for backend, frontend, and database
- `frontend/` — React app built with Vite
- `services/complaint-service/` — Spring Boot complaint API microservice
- `services/analysis-service/` — Spring Boot analysis service skeleton
- `pom.xml` — root Maven aggregator

## System architecture

This platform follows an event-aware microservice pattern with a clean separation of intake, classification, persistence, and presentation.

### Architecture diagram

```mermaid
flowchart LR
  subgraph Ingestion
    Web[Web Form / API] -->|POST /complaints| ComplaintService[Complaint Service]
    SMS[SMS Gateway] -->|Ingest event| IngestionAdapter[Ingestion Adapter]
    Email[Email Parser] -->|Ingest event| IngestionAdapter
  end

  subgraph Core
    ComplaintService -->|Persist complaint| Postgres[(PostgreSQL)]
    ComplaintService -->|Produce event| KafkaCreate[(Kafka complaint-created)]
    KafkaCreate --> AnalysisService[Analysis Service]
    AnalysisService -->|Classify & enrich| AIModel[AI / NLP Model]
    AIModel -->|Return category, sentiment, urgency| AnalysisService
    AnalysisService -->|Update complaint| ComplaintService
    ComplaintService -->|Write embeddable text| VectorStore[(Vector Store / pgvector)]
    DedupService[Dedup Service] -->|Query similarity| VectorStore
    DedupService -->|Mark duplicate group| ComplaintService
  end

  subgraph Orchestration
    SLAService[SLA / Priority Service] -->|Monitor deadlines| Postgres
    SLAService -->|Emit escalation| NotificationService[Notification Service]
    AnalyticsService[Analytics / Dashboard Service] -->|Read views| Postgres
    Frontend[React UI] -->|REST| ComplaintService
    Frontend -->|REST| AnalyticsService
    NotificationService -->|Email / Push| User[Complainant]
  end

  style Ingestion fill:#edf7ff,stroke:#5b8cff
  style Core fill:#f7f3ff,stroke:#9a77ff
  style Orchestration fill:#f4fff0,stroke:#47b24b
```

### Component responsibilities

- `frontend`
  - complaint submission UI
  - complaint list, detail, and dashboard views
  - status updates and filtering

- `complaint-service`
  - transactional complaint CRUD
  - complaint lifecycle state
  - persistence in PostgreSQL

- `analysis-service`
  - AI/NLP enrichment of complaint text
  - category, sentiment, urgency classification
  - asynchronous processing to keep ingest fast

- `postgres`
  - canonical complaint storage
  - audit history and SLA state

## Complaint service packages

- `model` — JPA entities and enums
- `repository` — Spring Data repositories
- `dto` — request and response DTOs
- `service` — business logic and classification rules
- `controller` — REST APIs
- `exception` — global exception handling

## APIs

### Complaint service

- `POST /complaints`
  - Create a complaint
  - Request body: `title`, `description`, `sector`, `source`

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
set SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/complaintsdb
set SPRING_DATASOURCE_USERNAME=ai_user
set SPRING_DATASOURCE_PASSWORD=ai_pass
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
- Add architecture documentation in `ARCHITECTURE.md` for BMAD design, phase planning, and service decomposition

## Architecture reference

For a concrete system design, see [ARCHITECTURE.md](ARCHITECTURE.md).
