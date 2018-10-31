INSERT INTO users (login, password) VALUES ('Alex', '$2a$10$EPqC9wy0JnH76bdsnoaMrePI3qm31r02ApWJnseAbXQu1xb0c7hye');
INSERT INTO users (login, password) VALUES ('Misha', '$2a$10$EPqC9wy0JnH76bdsnoaMrePI3qm31r02ApWJnseAbXQu1xb0c7hye');
INSERT INTO authorities (role, user_login) VALUES ('USER_ROLE', 'Alex');
INSERT INTO authorities (role, user_login) VALUES ('ADMIN', 'Misha');
