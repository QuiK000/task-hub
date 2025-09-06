INSERT INTO users (id, created_date, last_modified_date, created_by, last_modified_by, username, email, password,
                   enabled, locked, expired, email_verified, created_date_user, last_modified_date_user)
VALUES ('6ba7b810-9dad-11d1-80b4-00c04fd430c1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system', 'admin',
        'admin@company.com', '$2a$10$r.jy5GkUOeHVoU7VU6zZ.OU6h5q5l5p5N5Y5V5E5R5T5Y5U5I5O5P5Q5', true, false, false,
        true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('6ba7b810-9dad-11d1-80b4-00c04fd430c2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system', 'johndoe',
        'john.doe@company.com', '$2a$10$r.jy5GkUOeHVoU7VU6zZ.OU6h5q5l5p5N5Y5V5E5R5T5Y5U5I5O5P5Q5', true, false, false,
        true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('6ba7b810-9dad-11d1-80b4-00c04fd430c3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system', 'alice',
        'alice.smith@company.com', '$2a$10$r.jy5GkUOeHVoU7VU6zZ.OU6h5q5l5p5N5Y5V5E5R5T5Y5U5I5O5P5Q5', true, false,
        false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('6ba7b810-9dad-11d1-80b4-00c04fd430c4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system', 'bob',
        'bob.johnson@company.com', '$2a$10$r.jy5GkUOeHVoU7VU6zZ.OU6h5q5l5p5N5Y5V5E5R5T5Y5U5I5O5P5Q5', true, false,
        false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);