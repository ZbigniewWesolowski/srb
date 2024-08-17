INSERT INTO lanes (name) VALUES ('Lane 1');
INSERT INTO lanes (name) VALUES ('Lane 2');

INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');

INSERT INTO users (username, password, role_id) VALUES ('admin', 'admin', 2);
INSERT INTO users (username, password, role_id) VALUES ('user', 'user', 1);