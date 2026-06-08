DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

--
-- Table structure for table `users`
--

CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(68) NOT NULL,
    active BOOLEAN NOT NULL,
    PRIMARY KEY (username)
);

--
-- Inserting data for table `users`
--

INSERT INTO users (username, password, active)
VALUES
    ('john', '{bcrypt}$2a$10$S5uaxiDJoIAAURXmOkZFfe6EK176SRtqRdWHC5GupFlzMpVGGcBI6', TRUE),
    ('mary', '{bcrypt}$2a$10$S5uaxiDJoIAAURXmOkZFfe6EK176SRtqRdWHC5GupFlzMpVGGcBI6', TRUE),
    ('susan', '{bcrypt}$2a$10$S5uaxiDJoIAAURXmOkZFfe6EK176SRtqRdWHC5GupFlzMpVGGcBI6', TRUE);

--
-- Table structure for table `authorities`
--

CREATE TABLE roles (
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    UNIQUE (username, role),
    FOREIGN KEY (username) REFERENCES users (username)
);

--
-- Inserting data for table `authorities`
--

INSERT INTO roles (username, role)
VALUES
    ('john', 'ROLE_EMPLOYEE'),
    ('mary', 'ROLE_EMPLOYEE'),
    ('mary', 'ROLE_MANAGER'),
    ('susan', 'ROLE_EMPLOYEE'),
    ('susan', 'ROLE_MANAGER'),
    ('susan', 'ROLE_ADMIN');