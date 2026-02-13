-- PostgreSQL init script example
DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'rustconnector') THEN
    CREATE USER rustconnector WITH PASSWORD 'rustconnector';
  END IF;
END $$;

DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'ems_db') THEN
    CREATE DATABASE ems_db OWNER rustconnector;
  END IF;
END $$;
