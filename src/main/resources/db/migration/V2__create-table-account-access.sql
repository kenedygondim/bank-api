CREATE TABLE  tb_account_access
(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    login VARCHAR(11) NOT NULL,
    password VARCHAR(150) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL
);