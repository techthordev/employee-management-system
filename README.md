# 🏗️ Gradle Setup (Java)

[![Gradle](https://img.shields.io/badge/Gradle-9.3.1-blue?logo=gradle)](https://gradle.org/)

This guide explains how to set up, use, and maintain **Gradle 9.3.1** for **Spring Boot 4 + Java 25 projects** in the EMS Learning environment.

---

## ⚡ Getting Started

* **Check Gradle version:**

```bash
gradle -v
```

* **Upgrade Gradle via SDKMAN (if needed):**

```bash
sdk list gradle          # list available versions
sdk install gradle 9.3.1 # install 9.3.1 (current version)
sdk default gradle 9.3.1 # set as default
```

> ✅ The internal Groovy version (4.0.28) is automatically used by Gradle. No separate Groovy installation is required.

---

## 🛠️ Build & Run

* **Build project:**

```bash
gradle build
```

* **Run Spring Boot application:**

```bash
gradle bootRun
```

* **Clean project:**

```bash
gradle clean
```

* **List dependencies:**

```bash
gradle dependencies
```

* **Check available tasks:**

```bash
gradle tasks
```

---

## 📦 Dependencies

* Use `build.gradle` (Groovy DSL) for all Spring Boot / EMS projects.
* Recommended plugins:

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '4.0.2'
    id 'io.spring.dependency-management' version '1.1.2'
}
```

* Recommended dependencies:

  * `spring-boot-starter-web`
  * `spring-boot-starter-data-jpa`
  * `spring-boot-starter-security`
  * `spring-boot-devtools`
  * `postgresql`
  * `springdoc-openapi-ui`

---

## 🏆 Best Practices

* Keep **build scripts clean and organized** – separate logic into multiple files if needed.
* Use **Groovy DSL** (`build.gradle`) for new projects – stable and widely supported.
* Manage **dependencies carefully** to avoid conflicts.
* Regularly **upgrade Gradle** and plugins to stay compatible with **latest Java / Spring Boot**.
* **Wrapper (`gradlew`) is optional** if using SDKMAN; use only if reproducibility across developers/CI is needed.

---

## 📚 References

* [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)
* [Spring Boot + Gradle Guide](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/)
* [Groovy DSL Reference](https://docs.gradle.org/current/userguide/tutorial_using_tasks.html)
