-- Reset database for development
\connect employee_management

-- Reset business data
TRUNCATE TABLE employee RESTART IDENTITY CASCADE;
TRUNCATE TABLE departments RESTART IDENTITY CASCADE;

-- Reset auth data (uncomment if you want to reset users)
-- TRUNCATE TABLE auth.authorities CASCADE;
-- TRUNCATE TABLE auth.users CASCADE;

-- Re-insert departments
\i /docker-entrypoint-initdb.d/05-departments.sql

-- Re-insert business data
\i /docker-entrypoint-initdb.d/06-employees.sql

-- Re-link users
\i /docker-entrypoint-initdb.d/07-links.sql

-- Re-insert auth data (if uncommented above)
-- \i /docker-entrypoint-initdb.d/03-auth-schema.sql
