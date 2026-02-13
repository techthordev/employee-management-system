# Spring Boot

## Project Structure
- entities/ -> JPA Entities
- repositories/ -> Spring Data Repositories
- services/ -> Business Logic
- controllers/ -> REST Endpoints
- security/ -> SecurityConfig, UserDetailsService
- swagger/ -> OpenAPI / Springdoc

## Security
- Basic Auth or JWT
- BCryptPasswordEncoder recommended
- Test endpoints with Postman or Swagger UI

## Build & Run
- ./gradlew build
- ./gradlew bootRun
