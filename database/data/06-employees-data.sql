\connect employee_management

-- Insert 60 employees distributed across departments
-- 10 per region, mixed across departments

-- Hispanic/Latino employees (mixed departments)
INSERT INTO employee (first_name, last_name, email, department_id) VALUES
    ('Lucas', 'Martinez', 'lucas@techthordev.com.br', 1),      -- HR
    ('Sofia', 'Ramirez', 'sofia@techthordev.com.br', 2),       -- IT
    ('Mateo', 'Gonzalez', 'mateo@techthordev.com.br', 3),      -- Finance
    ('Valentina', 'Herrera', 'valentina@techthordev.com.br', 4), -- Sales
    ('Diego', 'Morales', 'diego@techthordev.com.br', 5),       -- Marketing
    ('Camila', 'Rojas', 'camila@techthordev.com.br', 6),       -- Operations
    ('Andres', 'Vargas', 'andres@techthordev.com.br', 7),      -- Support
    ('Paula', 'Castillo', 'paula@techthordev.com.br', 8),      -- R&D
    ('Javier', 'Torres', 'javier@techthordev.com.br', 2),      -- IT
    ('Natalia', 'Silva', 'natalia@techthordev.com.br', 4),     -- Sales
    
-- Brazilian employees
    ('Bruno', 'Pereira', 'bruno@techthordev.com.br', 1),       -- HR
    ('Mariana', 'Almeida', 'mariana@techthordev.com.br', 2),   -- IT
    ('Rafael', 'Costa', 'rafael@techthordev.com.br', 3),       -- Finance
    ('Fernanda', 'Araujo', 'fernanda@techthordev.com.br', 4),  -- Sales
    ('Thiago', 'Ribeiro', 'thiago@techthordev.com.br', 5),     -- Marketing
    ('Beatriz', 'Lopes', 'beatriz@techthordev.com.br', 6),     -- Operations
    ('Gabriel', 'Teixeira', 'gabriel@techthordev.com.br', 7),  -- Support
    ('Larissa', 'Mendes', 'larissa@techthordev.com.br', 8),    -- R&D
    ('Eduardo', 'Barbosa', 'eduardo@techthordev.com.br', 2),   -- IT
    ('Renata', 'Farias', 'renata@techthordev.com.br', 3),      -- Finance
    
-- Spanish employees
    ('Juan', 'Lopez', 'juan@techthordev.com.br', 1),           -- HR
    ('Ana', 'Fernandez', 'ana@techthordev.com.br', 2),         -- IT
    ('Carlos', 'Navarro', 'carlos@techthordev.com.br', 3),     -- Finance
    ('Lucia', 'Ortega', 'lucia@techthordev.com.br', 4),        -- Sales
    ('Miguel', 'Serrano', 'miguel@techthordev.com.br', 5),     -- Marketing
    ('Elena', 'Molina', 'elena@techthordev.com.br', 6),        -- Operations
    ('Pablo', 'Reyes', 'pablo@techthordev.com.br', 7),         -- Support
    ('Isabel', 'Cruz', 'isabel@techthordev.com.br', 8),        -- R&D
    ('Sergio', 'Iglesias', 'sergio@techthordev.com.br', 2),    -- IT
    ('Rocio', 'Campos', 'rocio@techthordev.com.br', 4),        -- Sales
    
-- Italian employees
    ('Marco', 'Bianchi', 'marco@techthordev.com.br', 1),       -- HR
    ('Alessia', 'Ricci', 'alessia@techthordev.com.br', 2),     -- IT
    ('Lorenzo', 'Conti', 'lorenzo@techthordev.com.br', 3),     -- Finance
    ('Giulia', 'Moretti', 'giulia@techthordev.com.br', 4),     -- Sales
    ('Matteo', 'Galli', 'matteo@techthordev.com.br', 5),       -- Marketing
    ('Chiara', 'Rinaldi', 'chiara@techthordev.com.br', 6),     -- Operations
    ('Davide', 'Romano', 'davide@techthordev.com.br', 7),      -- Support
    ('Francesca', 'Marino', 'francesca@techthordev.com.br', 8),-- R&D
    ('Simone', 'Ferrari', 'simone@techthordev.com.br', 2),     -- IT
    ('Elisa', 'Colombo', 'elisa@techthordev.com.br', 3),       -- Finance
    
-- English/American employees
    ('Alex', 'Johnson', 'alex@techthordev.com.br', 1),         -- HR
    ('Emily', 'Brown', 'emily@techthordev.com.br', 2),         -- IT
    ('Ryan', 'Walker', 'ryan@techthordev.com.br', 3),          -- Finance
    ('Olivia', 'Harris', 'olivia@techthordev.com.br', 4),      -- Sales
    ('Ethan', 'Clark', 'ethan@techthordev.com.br', 5),         -- Marketing
    ('Megan', 'Lewis', 'megan@techthordev.com.br', 6),         -- Operations
    ('Noah', 'Hall', 'noah@techthordev.com.br', 7),            -- Support
    ('Chloe', 'Young', 'chloe@techthordev.com.br', 8),         -- R&D
    ('Daniel', 'King', 'daniel@techthordev.com.br', 2),        -- IT
    ('Lauren', 'Wright', 'lauren@techthordev.com.br', 4),      -- Sales
    
-- Arabic employees
    ('Ahmed', 'Khaled', 'ahmed@techthordev.com.br', 1),        -- HR
    ('Yasmin', 'Hassan', 'yasmin@techthordev.com.br', 2),      -- IT
    ('Omar', 'Farouk', 'omar@techthordev.com.br', 3),          -- Finance
    ('Nour', 'Salem', 'nour@techthordev.com.br', 4),           -- Sales
    ('Karim', 'Adel', 'karim@techthordev.com.br', 5),          -- Marketing
    ('Leila', 'Aziz', 'leila@techthordev.com.br', 6),          -- Operations
    ('Samir', 'Nasser', 'samir@techthordev.com.br', 7),        -- Support
    ('Dina', 'Mostafa', 'dina@techthordev.com.br', 8),         -- R&D
    ('Hassan', 'Mahmoud', 'hassan@techthordev.com.br', 2),     -- IT
    ('Mariam', 'Youssef', 'mariam@techthordev.com.br', 3);     -- Finance

SELECT setval('employee_id_seq', (SELECT MAX(id) FROM employee));