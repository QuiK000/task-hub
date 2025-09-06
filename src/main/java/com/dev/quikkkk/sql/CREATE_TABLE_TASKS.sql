CREATE TABLE tasks
(
    id                 VARCHAR(36) PRIMARY KEY,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NULL,
    created_by         VARCHAR(255) NOT NULL,
    last_modified_by   VARCHAR(255) NULL,
    title              VARCHAR(255) NOT NULL,
    description        TEXT         NOT NULL,
    status             VARCHAR(20)  NOT NULL CHECK (status IN ('TODO', 'IN_PROGRESS', 'DONE')),
    project_id         VARCHAR(36)  NOT NULL,
    assignee_id        VARCHAR(36)  NULL,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    FOREIGN KEY (assignee_id) REFERENCES users (id) ON DELETE SET NULL
);