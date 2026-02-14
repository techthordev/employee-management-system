# Module 01: Gradle Build Automation

This module covers the build system foundation for the EMS environment, utilizing **Gradle 9.x** to orchestrate **Java 25** and **Spring Boot 4** applications on Fedora Linux.

[![Gradle](https://img.shields.io/badge/Gradle-9.x-blue?logo=gradle)](https://gradle.org/)
[![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)](https://openjdk.org/)

---

## ⚡ Getting Started

On Fedora, we manage Gradle versions efficiently using **SDKMAN**.

### Version Management
* **Check current version:** `gradle -v`
* **List available versions:** `sdk list gradle`
* **Install specific version:** `sdk install gradle 9.3.1` (or latest)
* **Set default:** `sdk default gradle 9.3.1`

> 💡 **Note:** Upgrading Gradle automatically provides the compatible Groovy version required for the Build DSL.

---

## 🛠️ Build & Run Commands

Standard workflow commands for the terminal:

* **Compile & Build:**
  ```bash
  gradle build

```

* **Launch Spring Boot:**
```bash
gradle bootRun

```


* **Clean Build Artifacts:**
```bash
gradle clean

```


* **Dependency Analysis:**
```bash
gradle dependencies

```



---

## 📦 Project Configuration

We utilize the **Groovy DSL** (`build.gradle`) for its maturity and wide support in the Spring ecosystem.

### Core Ecosystem (Preview)

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '4.0.0' // Target version
    id 'io.spring.dependency-management' version '1.1.7'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

```

### Essential Stack

* **Web:** `spring-boot-starter-web`
* **Persistence:** `spring-boot-starter-data-jpa` & `postgresql` (v18+)
* **Migrations:** `flyway-core`
* **Observability:** `spring-boot-starter-actuator`
* **Docs:** `springdoc-openapi-starter-webmvc-ui`

---

## 🏆 Best Practices

* **Java Toolchains:** Always define the Java version in the `toolchain` block to ensure Gradle uses the correct JDK 25 regardless of the system's `$JAVA_HOME`.
* **Clean Scripts:** Keep the `build.gradle` declarative. Move complex logic into custom tasks or separate script files.
* **Wrapper usage:** Although SDKMAN is used locally, always include the **Gradle Wrapper** (`gradlew`) in projects to ensure consistent builds in GitLab CI/CD pipelines.
* **Modern Dependencies:** Regularly check for dependency updates via `gradle help --task help` or specialized plugins.

---

## 📚 References

* [Official Gradle User Guide](https://docs.gradle.org/current/userguide/userguide.html)
* [Spring Boot Gradle Plugin Reference](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/)
