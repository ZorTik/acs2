
# ACS2 - Access Control Service

## Overview

ACS2 (Access Control Service) is a microservice designed to handle access management and group-based authorization for distributed systems. It provides an API for managing permissions, roles, and user access across multiple services. ACS2 allows for flexible integration with various backends, including Spring Boot applications.

![image](https://github.com/user-attachments/assets/062c3aaf-8d3b-464c-b1ec-01717c9d56be)

## Modules

- **`acs-client/java`**: Java client for interacting with the ACS2 API.
- **`acs-core`**: Shared components module for acs-plane and acs-server.
- **`acs-plane`**: Definitions server and control panel
- **`acs-proto`**: Protocol definitions and library for communication between ACS2 services.
- **`acs-server`**: Core backend service providing access control logic.
- **`acs-spring-boot-starter`**: Spring Boot integration module for seamless integration with Spring Security.
- **`examples`**: Example modules demonstrating use-cases of different ACS2 functionalities

## Installation & Setup

### Requirements

- Java 17 or higher
- MariaDB (or any compatible database)
- Recommended: Docker/Kubernetes for production deployment

### Local Setup (Development)

To set up ACS2 locally, clone the repository and use one of pre-defined configurations.

Docker Compose:
```bash
docker compose up -d
```
This setup directly runs gradle from within the main folder, so changing files and restarting the container has
immediate effect.

*Compose is configured with a test MariaDB database.*

Kubernetes:
```bash
kubectl apply -k k8s/overlays/dev
```

## Configuration

### Environment Variables

ACS2 relies on several environment variables for configuration. Below are the key variables:

| Variable Name            | Description                         | Default Value | Required |
|--------------------------|-------------------------------------|---------------|----------|
| `DATASOURCE_HOST`        | Database host string (without port) | —             | ✅        |
| `DATASOURCE_PORT`        | Database port                       | 3306          | ❌        |
| `DATASOURCE_DB`          | Database database name              | —             | ✅        |
| `DATASOURCE_USR`         | Database user                       | —             | ✅        |
| `DATASOURCE_PWD`         | Database password                   | —             | ✅        |
| `ACS_DELIMITER`          | Permission nodes delimiter          | .             | ❌        |
| `ACS_DEFINITIONS_SOURCE` | Definitions source address          | —             | ✅        |
| `ACS_DEFINITIONS_FORMAT` | Definitions string format           | YAML          | ❌        |

Definitions source URL example:
```
http://definitions-server/api/realm/<realm>/definitions/v1
```
*Realm is your custom realm that you configured in the definitions server*

## API Reference

### Endpoints

| Method | Endpoint           | Description             |
|--------|--------------------|-------------------------|
| GET    | `/api/groups`      | List all access groups  |
| POST   | `/api/permissions` | Create a new permission |
| PUT    | `/api/users/{id}`  | Update user roles       |
| DELETE | `/api/groups/{id}` | Delete an access group  |

For complete API documentation, refer to the OpenAPI/Swagger specification [here](<swagger link>).

## Integrating ACS2 with Spring Security

The `acs-spring-boot-starter` module provides easy integration with Spring Boot and Spring Security.

### Adding starter module to your project
To integrate ACS2 with your Spring Boot application, add the following dependency to your `build.gradle` or `pom.xml`:

```groovy
dependencies {
    implementation 'com.github.ZorTik:acs-spring-boot-starter:0.1.0'
}
```

### Basic Setup
The starter automatically configures connection of your service with ACS2. First, please customize details
in your application.yml/application.properties file:

```yaml
acs.client:
  base-url: http://acs-base-url
```

Now, you can access the AcsClientV1 instance anywhere in your Spring application:

```java
@Autowired
private AcsClientV1 acsClient;
```

### Enabling Spring Security integration

First, enable Spring Security integration using the `@EnableAcsSecurity` annotation:
```java
@EnableAcsSecurity
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

Next, create implementation of `AcsUserDetailsService` and register it using `AcsSecurityConfigurer`:
```java
public class MyCustomAcsUserDetailsService extends AcsUserDetailsService {
    
    public AcsUserDetailsService(
            @NotNull AcsClientV1 client,
            @NotNull SubjectProvider systemSubjectProvider, String userSubjectType) {
        super(client, systemSubjectProvider, userSubjectType);
    }
    
    @Override
    public UserDetails loadUserByUsernameAndAuthorities(
            String username, Collection<? extends GrantedAuthority> authorities) {
        // Custom logic to load user details by username.
        // Authorities are already provided by ACS2.
    }

    @Override
    public boolean existsByUsername(String username) {
        // Check if a user exists by username.
        // If returned false, this prevents from calling ACS22 for authorities.
    }
}

@Configuration
public class SecurityConfig implements AcsSecurityConfigurer {
    @Override
    AcsUserDetailsService userDetailsService(
            AcsClientV1 client, SubjectProvider systemSubjectProvider, String userSubjectType) {
        return new MyCustomAcsUserDetailsService();
    }
}
```

### Features

- Declarative method security.
- Context-aware group resolution.
- Seamless integration with Spring Security's `@PreAuthorize`, `@Secured`, and `@RolesAllowed` annotations.

### Customization

- Implement your own `SystemSubjectProviderFactory` for your own system subject resolution logic.

## Java Client Usage (`acs-client/java`)

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

boolean allGranted = response.all();
boolean anyGranted = response.anyOf("node1.subnode1", "node2");
```

## Testing

To run tests for ACS2, use the following Gradle commands:

- Unit tests:

```bash
./gradlew test
```

## Development Guide

### Project Structure

```plaintext
acs2/
├── acs-client/               # Java client for interacting with the ACS2 API.
├── acs-core/                 # Shared components module for acs-plane and acs-server.
├── acs-plane/                # Definitions server and control panel
├── acs-proto/                # Protocol definitions and library for communication between ACS2 services.
├── acs-server/               # Core backend service providing access control logic.
├── acs-spring-boot-starter/  # Spring Boot integration module for seamless integration with Spring Security.
└── k8s/                      # Kubernetes configurations
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
