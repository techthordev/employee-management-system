INSERT INTO auth.users (username, password, enabled, employee_id) VALUES
('john',  '$2a$10$j4Mw2WvZt0jYb/N1ONtFJeXVnFz5cQ.edx5cYNZm6Ow0Qb7uimPRq', true, 
    (SELECT id FROM public.employee WHERE email = 'lucas@techthordev.com.br')),
('mary',  '$2a$10$j4Mw2WvZt0jYb/N1ONtFJeXVnFz5cQ.edx5cYNZm6Ow0Qb7uimPRq', true, 
    (SELECT id FROM public.employee WHERE email = 'sofia@techthordev.com.br')),
('susan', '$2a$10$j4Mw2WvZt0jYb/N1ONtFJeXVnFz5cQ.edx5cYNZm6Ow0Qb7uimPRq', true, 
    (SELECT id FROM public.employee WHERE email = 'mateo@techthordev.com.br')),
('admin', '$2a$10$j4Mw2WvZt0jYb/N1ONtFJeXVnFz5cQ.edx5cYNZm6Ow0Qb7uimPRq', true, NULL)
ON CONFLICT (username) DO NOTHING;

INSERT INTO auth.roles (name) VALUES
('ROLE_EMPLOYEE'), ('ROLE_MANAGER'), ('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

INSERT INTO auth.user_roles (user_id, role_id)
SELECT u.id, r.id FROM auth.users u, auth.roles r
WHERE 
  (u.username = 'john'  AND r.name = 'ROLE_EMPLOYEE') OR
  (u.username = 'mary'  AND r.name IN ('ROLE_EMPLOYEE', 'ROLE_MANAGER')) OR
  (u.username = 'susan' AND r.name IN ('ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_ADMIN')) OR
  (u.username = 'admin' AND r.name IN ('ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_ADMIN'))
ON CONFLICT DO NOTHING;