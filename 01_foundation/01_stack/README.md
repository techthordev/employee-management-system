# Java 25 & Gradle 9.3 Environment

This module defines the core development stack for the learning journey.

## 🚀 Prerequisites (Fedora)
Ensure Java 25 is installed via `dnf` or `sdkman`:
```bash
java --version # Should be 25+

🛠️ Build Configuration

We use Gradle 9.3 with the Groovy DSL. The focus is on leveraging modern Java features:

    Virtual Threads: Enabled by default in Spring Boot 4.

    Structured Concurrency: Utilized for managing multiple concurrent tasks safely.

📝 Best Practices

    No Secrets in Code: Use environment variables for all sensitive data.

    Tooling: Use glab for repository management and zed or IntelliJ for coding.