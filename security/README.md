## 1. Prerequisites & Dependencies

### Generate a Secure Secret Key

For **HS512**, you need a 512-bit key (64 characters). Generate a secure Base64 string on your Fedora terminal:

```bash
openssl rand -base64 64

```

Copy this value into your environment variables or `application.properties`.

### Project Setup (`pom.xml`)

Ensure these core dependencies are present to support the security stack:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.13.0</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.13.0</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.13.0</version>
    <scope>runtime</scope>
</dependency>

```

---

## 2. Architecture & File Responsibilities

Based on the project structure in `br.com.techthordev.employee_management_system`:

| Component | File | Responsibility |
| --- | --- | --- |
| **Security Config** | `SecurityConfig` | The brain. Defines the Filter Chain, password encoder, and route permissions. |
| **Token Logic** | `JwtTokenProvider` | Utility class to create, sign, and parse JWTs using the Secret Key. |
| **Filter** | `JwtAuthenticationFilter` | Middleware that intercepts every request to check for a valid "Bearer" token. |
| **User Identity** | `CustomUserDetailsService` | Bridges Spring Security with your `UserRepository` to load user data. |
| **Domain** | `User` & `Role` | JPA Entities mapping the `auth` schema tables to Java objects. |
| **API Entry** | `AuthController` | Handles the `/login` POST request and returns the JWT. |

---

## 3. Step-by-Step Implementation Flow

### Step 1: Data Model (`entity` & `repository`)

Create `User` and `Role` entities. The `User` must implement `UserDetails` (or be mapped to it) so Spring Security understands your database structure. Use `UserRepository` to fetch users by their username.

### Step 2: JWT Utility (`security/JwtTokenProvider`)

This class uses your OpenSSL-generated secret. It contains:

* `generateToken(Authentication auth)`: Creates the string.
* `validateToken(String token)`: Checks expiration and signature.
* `getAuthentication(String token)`: Converts token claims back into Spring Security authorities.

### Step 3: The Interceptor (`security/JwtAuthenticationFilter`)

This filter extends `OncePerRequestFilter`.

1. It looks for the `Authorization` header.
2. It strips the `Bearer ` prefix.
3. If valid, it populates the `SecurityContextHolder`. This allows the request to proceed to the Controller as an "authenticated" user.

### Step 4: Security Firewall (`security/SecurityConfig`)

Here you define the rules:

* **PermitAll**: `/v1/auth/**` (Login), Swagger UI, and static resources.
* **Roles**: e.g., `/v1/admin/**` requires `ROLE_ADMIN`.
* **Stateless**: Disable CSRF and set Session Management to `STATELESS` (since we don't use Cookies/JSESSIONID).

---

## 4. Configuration (`application.properties`)

```properties
# JWT Settings
app.jwt.secret=${JWT_SECRET}
app.jwt.expiration-ms=3600000

# API Versioning context
server.servlet.context-path=/api

```

---

## 5. Testing & Verification

### A. Authentication Test (Login)

Request a token by providing valid credentials from the `auth.users` table.

```bash
curl -i -X POST http://localhost:8080/api/v1/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"john", "password":"password"}'

```

### B. Authorization Test (Protected Access)

Access the `EmployeeController` using the Bearer token.

```bash
curl -i -X GET http://localhost:8080/api/v1/employees \
     -H "Authorization: Bearer <paste_token_here>"

```

### C. Negative Test

Try to access `/api/v1/employees` without a header or with an invalid token. The server must return **403 Forbidden** or **401 Unauthorized**.

---

## 6. Global Exception Handling

If authentication fails, the `GlobalExceptionHandler` (mapping `BadCredentialsException`) ensures the client receives a clean JSON error response instead of a raw stack trace.

---

Soll ich diese `README.md` noch um die spezifischen SQL-Statements für das `auth`-Schema ergänzen, damit das Setup wirklich komplett "copy-paste"-fähig ist?