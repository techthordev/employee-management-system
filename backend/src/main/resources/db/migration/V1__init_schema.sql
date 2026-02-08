-- ==========================================
-- Flyway V1
-- Initial employee schema
-- ==========================================

CREATE TABLE IF NOT EXISTS public.employee (
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(45)  NOT NULL,
    last_name  VARCHAR(45)  NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE
);

ALTER TABLE public.employee OWNER TO springconnector;
