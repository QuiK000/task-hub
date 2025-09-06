INSERT INTO projects (id, created_date, last_modified_date, created_by, last_modified_by, title, description, owner_id)
VALUES ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        '6ba7b810-9dad-11d1-80b4-00c04fd430c1', '6ba7b810-9dad-11d1-80b4-00c04fd430c1', 'Website Redesign',
        'Complete redesign of company website with modern UI/UX', '6ba7b810-9dad-11d1-80b4-00c04fd430c1'),
       ('b2c3d4e5-f6g7-8901-bcde-f23456789012', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        '6ba7b810-9dad-11d1-80b4-00c04fd430c3', '6ba7b810-9dad-11d1-80b4-00c04fd430c3', 'Mobile App Development',
        'Development of cross-platform mobile application', '6ba7b810-9dad-11d1-80b4-00c04fd430c3'),
       ('c3d4e5f6-g7h8-9012-cdef-345678901234', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        '6ba7b810-9dad-11d1-80b4-00c04fd430c1', '6ba7b810-9dad-11d1-80b4-00c04fd430c1', 'Database Migration',
        'Migration from legacy database to modern cloud solution', '6ba7b810-9dad-11d1-80b4-00c04fd430c1');