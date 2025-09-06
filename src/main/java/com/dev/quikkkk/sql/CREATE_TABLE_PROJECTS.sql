CREATE TABLE projects
(
    id                 VARCHAR(36) PRIMARY KEY,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NULL,
    created_by         VARCHAR(255) NOT NULL,
    last_modified_by   VARCHAR(255) NULL,
    title              VARCHAR(255) NOT NULL,
    description        TEXT         NOT NULL,
    owner_id           VARCHAR(36)  NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);