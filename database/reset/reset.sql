-- Reset database (für Development)
\connect employee_management

TRUNCATE TABLE employee RESTART IDENTITY CASCADE;

-- Re-insert data
\i /docker-entrypoint-initdb.d/data/03-data.sql