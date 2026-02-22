-- ==================================================
-- RESET DATABASE (Development Only!)
-- ==================================================

-- Reset business data
TRUNCATE TABLE public.employee RESTART IDENTITY CASCADE;
TRUNCATE TABLE public.department RESTART IDENTITY CASCADE;

-- Reset auth data (uncomment if needed)
-- TRUNCATE TABLE auth.user_roles CASCADE;
-- TRUNCATE TABLE auth.users RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE auth.roles RESTART IDENTITY CASCADE;

-- Re-insert sample data
\echo 'Database reset complete. Re-run data scripts to restore sample data.'
