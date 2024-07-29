CREATE TABLE tb_transaction
(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    value DECIMAL(10, 2) NOT NULL,
    sender_id VARCHAR(36) NOT NULL,
    receiver_id VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL
);