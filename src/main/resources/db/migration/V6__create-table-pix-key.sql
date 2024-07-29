CREATE TABLE tb_pix_key
(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    account_id VARCHAR(8) NOT NULL,
    key_type ENUM('CPF', 'PHONE_NUMBER', 'EMAIL', 'RANDOM') NOT NULL,
    key_value VARCHAR(40) NOT NULL
);