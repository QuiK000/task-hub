CREATE TABLE roles
(
    id                 VARCHAR(36) PRIMARY KEY,
    name               VARCHAR(255) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NULL,
    created_by         VARCHAR(255) NOT NULL,
    last_modified_by   VARCHAR(255) NULL
);