# Microservices Project

Spring Boot microservice architecture with two services

## Services
- **user-service** (port 8081) - user management
- **company-service** (port 8082) - company management
- **api-gateway** (port 8080) - single entry point
- **config_server** (8888) - centralized configuration
- **eureka-server** (port 8761) - service registration

## Launch

1. Create .env file based on .env.example
2. Fill the variables with your own values
3. Run:

```bash 
docker compose up --build
```

## API

All requests go through the API Gateway on port 8080

- `GET /users` - list of users with their companies
- `POST /users` - create a user
- `GET /companies` - list companies with their employees
- `POST / companies` - create a company