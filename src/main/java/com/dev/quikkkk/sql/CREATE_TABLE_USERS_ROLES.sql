CREATE TABLE users_roles
(
    user_id  VARCHAR(36) NOT NULL,
    roles_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (user_id, roles_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (roles_id) REFERENCES roles (id) ON DELETE CASCADE
);