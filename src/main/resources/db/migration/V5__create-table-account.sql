CREATE TABLE tb_account
(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    account_number VARCHAR(8) NOT NULL,
    account_type ENUM('CHECKING', 'SAVINGS') NOT NULL,
    account_status ENUM('ACTIVE', 'INACTIVE', 'BLOCKED') NOT NULL,
    branch_number VARCHAR(4) DEFAULT '0001' NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 100.00 NOT NULL,
    client_id VARCHAR(36) NOT NULL,
    account_access_id VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL
);