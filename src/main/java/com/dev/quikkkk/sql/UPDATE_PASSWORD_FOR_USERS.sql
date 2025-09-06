UPDATE users
SET password = '$2a$10$ABCDEFGHIJKLMNOPQRSTUV.abcdefghijklmnopqrstuvwxyz12'
WHERE username = 'admin';
UPDATE users
SET password = '$2a$10$ABCDEFGHIJKLMNOPQRSTUV.abcdefghijklmnopqrstuvwxyz12'
WHERE username = 'johndoe';
UPDATE users
SET password = '$2a$10$ABCDEFGHIJKLMNOPQRSTUV.abcdefghijklmnopqrstuvwxyz12'
WHERE username = 'alice';
UPDATE users
SET password = '$2a$10$ABCDEFGHIJKLMNOPQRSTUV.abcdefghijklmnopqrstuvwxyz12'
WHERE username = 'bob';