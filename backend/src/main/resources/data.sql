INSERT INTO roles (id, name)
VALUES (1, 'OWNER'),
       (2, 'CAREGIVER'),
       (3, 'ADMIN')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO services (id, name, description)
VALUES (1, 'PASEAR', 'Pasear a mi mascota por parques y calles.'),
       (2, 'BAÑAR', 'Bañar a mi mascota con mucho cuidado.'),
       (3, 'VETERINARIO', 'Llevar a mi mascota al veterinario para que sea atendida.')
    ON CONFLICT(id) DO NOTHING;