
# ACS2 - Access Control Service

## Overview

ACS2 (Access Control Service) is a microservice designed to handle access management and group-based authorization for distributed systems. It provides an API for managing permissions, roles, and user access across multiple services. ACS2 allows for flexible integration with various backends, including Spring Boot applications.

![image](https://github.com/user-attachments/assets/b074a1fa-8a28-40e0-8441-145a8550d049)

## Modules

- **`acs-client`**: Java client for interacting with the ACS2 API.
- **`acs-definitions-server`**: Centralized definitions and configuration management.
- **`acs-server`**: Core backend service providing access control logic.
- **`acs-spring`**: Spring Boot integration module for seamless integration with Spring Security.

## Installation & Setup

### Requirements

- Java 17 or higher
- MariaDB (or any compatible database)
- Gradle or Docker (for setup)

### Local Setup

To set up ACS2 locally, clone the repository and run the project:

```bash
git clone https://github.com/<your-org>/acs2.git
cd acs2
./gradlew build
./gradlew :acs-server:bootRun
```

### Docker Setup

To run ACS2 using Docker Compose, use the following command:

```bash
docker-compose up
```

*Note: Ensure that Docker is installed and running before executing this command.*

## Configuration

### Environment Variables

ACS2 relies on several environment variables for configuration. Below are the key variables:

| Variable Name             | Description                         | Default Value              | Required |
|---------------------------|-------------------------------------|----------------------------|----------|
| `ACS_DB_URL`               | JDBC URL for the database           | `jdbc:postgresql://...`     | ✅        |
| `ACS_JWT_SECRET`           | Secret key for signing JWT tokens   | —                          | ✅        |
| `ACS_PORT`                 | Port for the ACS server             | `8080`                     | ❌        |
| `SPRING_PROFILES_ACTIVE`   | Spring profile to use               | `dev`                      | ❌        |

## API Reference

### Endpoints

| Method | Endpoint            | Description                       |
|--------|---------------------|-----------------------------------|
| GET    | `/api/groups`        | List all access groups            |
| POST   | `/api/permissions`   | Create a new permission           |
| PUT    | `/api/users/{id}`    | Update user roles                 |
| DELETE | `/api/groups/{id}`   | Delete an access group            |

For complete API documentation, refer to the OpenAPI/Swagger specification [here](<swagger link>).

## Integrating ACS2 with Spring Security

The `acs-spring` module provides easy integration with Spring Boot and Spring Security.

### Basic Setup

```java
@Configuration
@EnableAcsSecurity
public class SecurityConfig {
    // Your access control configuration
}
```

### Features

- Declarative method security.
- Automatic token extraction.
- Context-aware user and group resolution.
- Seamless integration with Spring Security's `@PreAuthorize`, `@Secured`, and `@RolesAllowed` annotations.

### Customization

- Implement your own `PermissionEvaluator` for more granular permission handling.
- Extend `AcsSecurityConfigurerAdapter` to customize security configuration.

## Java Client Usage (`acs-client`)

The `acs-client` module provides a lightweight Java client for interacting with the ACS2 backend.

### Client Initialization

```java
AcsClientV1 client = AcsClient.v1()
        .withOkHttpAdapter()
        .withBaseUrl("<ACS2_URL>")
        .withHttpSerializer(new GsonHttpSerializer(new Gson()))
        .build();
```

### Example Usage

```java
// Example of making a permission check
CheckAccessResponseV1 response = client.checkAccess(accessor, accessed, nodes);
```

## Testing

To run tests for ACS2, use the following Gradle commands:

- Unit tests:

```bash
./gradlew test
```

- Integration tests:

```bash
./gradlew integrationTest
```

## Development Guide

### Project Structure

```plaintext
acs2/
├── acs-client/           # Java client for interacting with the ACS2 API
├── acs-definitions-server/  # Server for managing definitions and configurations
├── acs-server/            # Core backend service
├── acs-spring/            # Spring Boot integration
└── k8s/                   # Kubernetes configurations
```

### Extending the Model

- Add new roles to the `Permission` enum for fine-grained access control.
- Extend `PermissionService` to implement custom business logic.
- Update the client or integration layers for enhanced functionality.

## Contributing

We welcome contributions to ACS2. To contribute, follow these steps:

1. Fork the repository.
2. Create a new branch: `feature/my-feature`.
3. Implement your changes.
4. Submit a Pull Request.

For detailed contribution guidelines, please refer to the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Support

For any issues, please open a GitHub Issue, and our team will respond as soon as possible.

---
