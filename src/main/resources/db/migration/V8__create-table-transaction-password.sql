CREATE TABLE tb_transaction_password
(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    account_id VARCHAR(8) NOT NULL,
    password VARCHAR(120) NOT NULL
);