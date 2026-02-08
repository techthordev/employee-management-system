-- ==================================================
-- DEFAULT SCHEMA (public)
-- ==================================================

GRANT ALL ON SCHEMA public TO springconnector;
ALTER SCHEMA public OWNER TO springconnector;

CREATE TABLE IF NOT EXISTS public.employee (
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(45)  NOT NULL,
    last_name  VARCHAR(45)  NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE
);

ALTER TABLE public.employee OWNER TO springconnector;

-- Default privileges
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO springconnector;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO springconnector;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL PRIVILEGES ON TABLES TO springconnector;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL PRIVILEGES ON SEQUENCES TO springconnector;
