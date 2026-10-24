-- For the Database team: run once on outreachDB (MySQL).
-- Login needs a role per account (STAFF or ADMIN); without these changes every
-- logged-in request is refused with 403. The API's test copy of the schema
-- (src/main/resources/db/migration/V2__database_team_schema.sql) already has them.

CREATE TABLE role (
    id   INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO role (name) VALUES ('STAFF'), ('ADMIN');

ALTER TABLE credentials
    ADD COLUMN roleId INT NULL,
    ADD CONSTRAINT fk_credentials_role FOREIGN KEY (roleId) REFERENCES role (id),
    ADD CONSTRAINT uq_credentials_username UNIQUE (username);

-- The first ADMIN. Only an admin can register other accounts, so one must exist.
-- Passwords are stored as BCrypt hashes, never plain text. Generate one with:
--   htpasswd -bnBC 10 "" 'the-password' | tr -d ':\n'
-- and share the password itself privately, not in the repo.
INSERT INTO credentials (userId, username, password, roleId)
VALUES (1, 'admin', '<PASTE BCRYPT HASH HERE>', (SELECT id FROM role WHERE name = 'ADMIN'));

-- The existing rows (CSanchez, Amber123) store plain-text passwords, which the
-- API can never match. Delete them, or re-insert them with BCrypt hashes and a role.
