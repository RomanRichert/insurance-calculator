# Insurance Premium Calculator

This application calculates insurance premiums based on:

- Estimated annual mileage
- Vehicle type
- Vehicle registration location (postal code)

It provides a REST API for applicants and third-party integrations, and it persists all requests and results in a
PostgreSQL database.

---

## How to Start the Application

### Prerequisites

- **Java 17+**
- **Maven 3.9+**
- **Docker** (required for local PostgreSQL via Testcontainers)
-
    - **pnpm** (`npm install -g pnpm`)

### Run Locally

```bash
./mvnw clean compile quarkus:dev
```

> Quarkus will start in development mode with hot-reload enabled.  
> The default port is `https://localhost:8443`.

---

### Run Everything with One Command

You can launch the complete stack (backend, frontend, PostgreSQL) using:

```bash
chmod +x ./build-and-run.sh
./build-and-run.sh
```

This will:

    Build the backend

    Start the frontend and database via docker-compose

    Run Flyway migrations

    Import ZIP code data from CSV

    Expose everything on the following ports

---

## Ports and Endpoints

| Service         | Protocol | Port    | Description                                                  |
|-----------------|----------|---------|--------------------------------------------------------------|
| Frontend (Nuxt) | HTTP     | `3000`  | Accessible at [http://localhost:3000](http://localhost:3000) |
| Backend (API)   | HTTP     | `8080`  | Redirects to HTTPS by default                                |
| Backend (API)   | HTTPS    | `8443`  | Main API endpoint                                            |
| Swagger         | -        | -       | Endpoint: /q/swagger                                         |
| PostgreSQL      | TCP      | `5432`  | Internal to Docker network                                   |
| PostgreSQL      | TCP      | `15432` | Exposed for development                                      |

## SSL Support

The application supports HTTPS via self-signed certificates.
To enable it, configure the following in `application.yaml`:

```yaml
quarkus:
  http:
    ssl:
      certificate:
        key-store-file: src/main/resources/keystore.p12
        key-store-password: password
      enable: true
```

A shell script `update-keystore.sh` is included to regenerate a self-signed certificate manually.

```bash
chmod +x ./update-keystore.sh
./update-keystore.sh
```

---

## Quality Assurance

- **Unit & Integration Testing**: JUnit 5, Mockito, RestAssured, and Testcontainers.
- **Automatic Flyway migration** on startup.
- **CSV Import** of ZIP code regions is enabled by default.

---

## Design Decisions

### Why Quarkus over Spring or Micronaut?

- Minimal config
- Native compilation ready
- Built-in Swagger/OpenAPI, Flyway, CORS, JSON error mapping

### Why Kotlin instead of Java?

- Concise, null-safe, expressive
- Seamless Java interop
- Great support in Quarkus
- Reduces boilerplate, improves readability

### Why Java 17?

- LTS version
- Modern features like sealed classes and records
- Best supported version for Kotlin and Quarkus 3

### Why PostgreSQL?

- Strong support for schema evolution, indexing, constraints
- Native support in Flyway and Testcontainers
- Mature and widely adopted

### Why are services used even if they just call repositories?

- They separate business logic from persistence
- Allow injecting cross-cutting concerns (logging, validation)
- Enable testability and mocking
- Simplify future extension
- This is aligned with clean architecture principles.

### Why isn’t MapStruct used for mapping?

- Mappings are straightforward and few
- Kotlin does not support MapStruct well
- Extension functions (.toDTO() etc.) are more idiomatic in Kotlin

---

## Documentation

Detailed developer documentation (architecture, API, setup, features) is maintained in AsciiDoc and generated via
Antora.

See src/main/doc

---

## Planned But Not Implemented

These features were considered but deprioritized due to time constraints:

- Authorization via Keycloak for securing API endpoints
- Frontend routing and navigation to multiple pages to reflect each backend domain
- Integration with Prometheus and Grafana

---