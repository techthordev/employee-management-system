# Module 07: GitLab & DevOps Workflow

This module documents the professional version control workflow and DevOps integration for the entire learning journey, specifically tailored for GitLab on Fedora Linux.

## 🛠️ Tooling & Authentication

### GitLab CLI (`glab`)
We use the official GitLab CLI to manage repositories, pipelines, and issues directly from the Fedora terminal.
* **Status Check:** `glab auth status`
* **Repository View:** `glab repo view`

### SSH Authentication
To ensure secure and passwordless communication, we use SSH keys (ED25519) shared across platforms (GitLab/GitHub).
* **Key Location:** `~/.ssh/id_ed25519`
* **Configuration:** Remote URLs are set to `git@gitlab.com:...` to leverage SSH-agent.

---

## 🚀 Git Workflow Standards

### Branching Policy
* **Main Branch:** The `main` branch is the source of truth.
* **Protection:** The `main` branch is protected on GitLab to prevent accidental forced pushes, ensuring stability for CI/CD.

### Essential Commands
1. **Initialize & Link:**
   ```bash
   git init
   git branch -M main
   git remote add origin git@gitlab.com:techthordev/<repo-name>.git

```

2. **Commit & Push:**
```bash
git add .
git commit -m "feat: descriptive message"
git push -u origin main

```



---

## 🤖 CI/CD Integration (Planned)

The project is prepared for GitLab CI/CD integration using `.gitlab-ci.yml`.

* **Build:** Automated Gradle builds for Java 25.
* **Test:** Execution of JUnit 5 tests.
* **Containerize:** Building images via Podman/Buildah for the GitLab Container Registry.

---

## 💡 Best Practices

* **CLI over GUI:** Prefer `glab` and `git` CLI for speed and automation.
* **Atomic Commits:** Keep commits small and focused.
* **SSH over HTTPS:** Avoid PAT (Personal Access Tokens) by using SSH keys.
* **Fedora Native:** Utilize Fedora's native Git and SSH integration.
