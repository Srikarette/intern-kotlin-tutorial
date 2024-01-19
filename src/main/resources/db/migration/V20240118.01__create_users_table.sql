CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; -- run for this file.

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         UUID        NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(255),
    address    VARCHAR(255),
    gender     VARCHAR(20) NOT NULL
)