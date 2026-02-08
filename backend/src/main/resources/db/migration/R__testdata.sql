-- ==========================================
-- Flyway R__testdata
-- Development / Test data
-- ==========================================

-- ==========================
-- EMPLOYEES
-- ==========================
INSERT INTO public.employee (first_name, last_name, email) VALUES
('Lucas','Martinez','lucas@techthordev.com.br'),
('Sofia','Ramirez','sofia@techthordev.com.br'),
('Mateo','Gonzalez','mateo@techthordev.com.br'),
('Valentina','Herrera','valentina@techthordev.com.br'),
('Diego','Morales','diego@techthordev.com.br'),
('Camila','Rojas','camila@techthordev.com.br'),
('Andres','Vargas','andres@techthordev.com.br'),
('Paula','Castillo','paula@techthordev.com.br'),
('Javier','Torres','javier@techthordev.com.br'),
('Natalia','Silva','natalia@techthordev.com.br')
ON CONFLICT (email) DO NOTHING;

-- ==========================
-- USERS
-- Passwort: fun123 (BCrypt)
-- ==========================
INSERT INTO auth.users (username, password, enabled) VALUES
('john',  '{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q', true),
('mary',  '{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q', true),
('susan', '{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q', true)
ON CONFLICT (username) DO NOTHING;

-- ==========================
-- ROLES
-- ==========================
INSERT INTO auth.roles (name) VALUES
('ROLE_EMPLOYEE'),
('ROLE_MANAGER'),
('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

-- ==========================
-- USER ↔ ROLE MAPPING
-- ==========================
INSERT INTO auth.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM auth.users u
JOIN auth.roles r
ON
  (u.username = 'john'  AND r.name = 'ROLE_EMPLOYEE')
  OR
  (u.username = 'mary'  AND r.name IN ('ROLE_EMPLOYEE','ROLE_MANAGER'))
  OR
  (u.username = 'susan' AND r.name IN ('ROLE_EMPLOYEE','ROLE_MANAGER','ROLE_ADMIN'))
ON CONFLICT DO NOTHING;
