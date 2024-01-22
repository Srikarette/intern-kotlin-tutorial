CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; -- run for this file.

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    order_id     UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    name         VARCHAR(50)  NOT NULL,
    price        VARCHAR(50)  NOT NULL,
    address      VARCHAR(255),
    order_date   VARCHAR(255) NOT NULL,
    order_status VARCHAR(20)  NOT NULL,
    user_id      UUID         NOT NULL
)