# ACS2 - Access Control Service

<badge placeholders (build status, license, version, etc.)>

## 🧭 Overview

ACS2 (Access Control Service) is a microservice designed to externally manage access permissions and group-based authorization for distributed systems.

<!-- <architecture diagram here> -->

---

## 🧱 Modules

- `acs-client` — Java client for interacting with the ACS2 API
- `acs-definitions-server` — Definitions midpoint and cluster controller
- `acs-server` — Main backend service providing access control logic
- `acs-spring` — Spring Boot integration module

---

## 📦 Installation & Setup

### Requirements

- Java 17+
- MariaDB (or compatible database)
- Gradle or Docker

### Local Setup

```bash
git clone https://github.com/<your-org>/acs2.git
cd acs2
./gradlew build
./gradlew :acs-server:bootRun
```

### Docker Setup

```bash
docker-compose up
```

<neconeco: docker-compose.yml snippet if needed>

---

## ⚙️ Configuration

### Environment Variables

| Variable Name            | Description                   | Default Value           | Required |
|--------------------------|-------------------------------|-------------------------|----------|
| `ACS_DB_URL`             | JDBC URL for the database     | `jdbc:postgresql://...` | ✅        |
| `ACS_JWT_SECRET`         | Secret for signing JWT tokens | —                       | ✅        |
| `ACS_PORT`               | Port for the ACS server       | `8080`                  | ❌        |
| `SPRING_PROFILES_ACTIVE` | Spring profile to use         | `dev`                   | ❌        |

---

## 🌍 API Reference

> Comprehensive OpenAPI/Swagger documentation: [<neconeco: swagger link>](<neconeco: swagger link>)

| Method | Endpoint           | Description             |
|--------|--------------------|-------------------------|
| GET    | `/api/groups`      | List all access groups  |
| POST   | `/api/permissions` | Create a new permission |
| PUT    | `/api/users/{id}`  | Update user roles       |
| DELETE | `/api/groups/{id}` | Delete a group          |

---

## 🌱 Integrating ACS2 in Spring Security

`acs-spring` simplifies integrating ACS2 into your Spring Boot project.

### Basic Setup

```java
@Configuration
@EnableAcsSecurity
public class SecurityConfig {
    // Your access control configuration
}
```

### Features

- Declarative method security
- Automatic token extraction
- Context-aware user and group resolution
- Seamless integration with Spring Security

### Customization

- Provide your own `PermissionEvaluator`
- Extend `AcsSecurityConfigurerAdapter`

<neconeco: diagram or flow how acs-spring integrates>

---

## ☕ Java Client Usage (`acs-client`)

`acs-client` is a lightweight Java library for communicating with the ACS2 backend.

### Client initialization

```java
AcsClientV1 client = AcsClient.v1()
        .withOkHttpAdapter()
        // https://acs.example.com
        .withBaseUrl(YOUR_ACS2_URL)
        .withHttpSerializer(new GsonHttpSerializer(new Gson()))
        .build();
```

### Sample Usage

```java

```

---

## 🧪 Testing

```bash
# Unit tests
./gradlew test

# Integration tests
./gradlew integrationTest
```

---

## 🧰 Development Guide

### Project Structure

```plaintext
acs2/
├── acs-client/
├── acs-definitions-server/
├── acs-server/
├── acs-spring/
└── k8s/
```

### Dev Architecture

<neconeco: local development architecture diagram>

### Extending the Model

- Add roles in `Permission` enum
- Extend `PermissionService`
- Update client or integration layers

---

## 💬 Contributing

1. Fork the repo
2. Create your branch: `feature/my-feature`
3. Commit your changes
4. Submit a Pull Request

---

## 📄 License

Licensed under the [MIT License](<license file link>).

---

## 📞 Contact / Support

For issues, open a GitHub Issue.
