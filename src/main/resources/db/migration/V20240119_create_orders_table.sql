CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; -- run for this file.

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id          UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        VARCHAR(50)  NOT NULL,
    price       VARCHAR(50)  NOT NULL,
    address     VARCHAR(255),
    orderDate   VARCHAR(255) NOT NULL,
    orderStatus VARCHAR(20)  NOT NULL,
    user_id     UUID         NOT NULL
)