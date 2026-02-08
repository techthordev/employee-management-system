-- ==========================================
-- Flyway V2
-- Authentication & Authorization schema
-- ==========================================

-- --------------------------
-- SCHEMA
-- --------------------------
CREATE SCHEMA IF NOT EXISTS auth;
ALTER SCHEMA auth OWNER TO springconnector;

-- --------------------------
-- USERS
-- --------------------------
CREATE TABLE IF NOT EXISTS auth.users (
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled  BOOLEAN      NOT NULL DEFAULT TRUE
);

ALTER TABLE auth.users OWNER TO springconnector;

-- --------------------------
-- ROLES
-- --------------------------
CREATE TABLE IF NOT EXISTS auth.roles (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

ALTER TABLE auth.roles OWNER TO springconnector;

-- --------------------------
-- USER ↔ ROLE (N:M)
-- --------------------------
CREATE TABLE IF NOT EXISTS auth.user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES auth.users (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
        REFERENCES auth.roles (id)
        ON DELETE CASCADE
);

ALTER TABLE auth.user_roles OWNER TO springconnector;
