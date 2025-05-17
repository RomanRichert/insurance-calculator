# Insurance Premium Calculator

This application calculates insurance premiums based on:

- Estimated annual mileage
- Vehicle type
- Vehicle registration location (ZIP/postal code)

It provides a REST API for applicants and third-party integrations, and it persists all requests and results in a
PostgreSQL database.

---

## How to Start the Application

### Prerequisites

- **Java 17+**
- **Maven 3.9+**
- **Docker** (required for local PostgreSQL via Testcontainers)

### Run Locally

```bash
./mvnw clean compile quarkus:dev
```

> Quarkus will start in development mode with hot-reload enabled.  
> The default port is `https://localhost:8443`.

---

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

---

## Technology Stack

- **Quarkus**: Chosen for its excellent developer experience, fast startup time, and native compilation support.
- **Kotlin**: For concise syntax, null safety, and great interoperability with Java and Quarkus.
- **PostgreSQL**: Reliable, production-ready open-source database with excellent support and advanced SQL.

---

## Quality Assurance

- **Unit & Integration Testing**: JUnit 5, Mockito, RestAssured, and Testcontainers.
- **Automatic Flyway migration** on startup.
- **CSV Import** of ZIP code regions is enabled by default.

---