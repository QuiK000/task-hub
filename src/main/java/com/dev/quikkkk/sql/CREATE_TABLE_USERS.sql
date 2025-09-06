CREATE TABLE users
(
    id                      VARCHAR(36) PRIMARY KEY,
    created_date            TIMESTAMP           NOT NULL,
    last_modified_date      TIMESTAMP           NULL,
    created_by              VARCHAR(255)        NOT NULL,
    last_modified_by        VARCHAR(255)        NULL,
    username                VARCHAR(255) UNIQUE NOT NULL,
    email                   VARCHAR(255) UNIQUE NOT NULL,
    password                VARCHAR(255)        NOT NULL,
    enabled                 BOOLEAN             NOT NULL DEFAULT true,
    locked                  BOOLEAN             NOT NULL DEFAULT false,
    expired                 BOOLEAN             NOT NULL DEFAULT false,
    email_verified          BOOLEAN             NOT NULL DEFAULT true,
    created_date_user       TIMESTAMP           NOT NULL,
    last_modified_date_user TIMESTAMP           NULL
);