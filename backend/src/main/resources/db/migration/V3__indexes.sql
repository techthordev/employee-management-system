-- ==========================================
-- Flyway V3
-- Indexes & constraints for auth schema
-- ==========================================

-- --------------------------
-- USERS
-- --------------------------
CREATE INDEX IF NOT EXISTS idx_users_username
    ON auth.users (username);

-- --------------------------
-- ROLES
-- --------------------------
CREATE INDEX IF NOT EXISTS idx_roles_name
    ON auth.roles (name);

-- --------------------------
-- USER_ROLES
-- --------------------------
CREATE INDEX IF NOT EXISTS idx_user_roles_user_id
    ON auth.user_roles (user_id);

CREATE INDEX IF NOT EXISTS idx_user_roles_role_id
    ON auth.user_roles (role_id);
