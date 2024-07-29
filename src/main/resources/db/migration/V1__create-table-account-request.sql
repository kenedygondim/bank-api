CREATE TABLE tb_account_request
(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    date_of_birth VARCHAR(10) NOT NULL,
    email VARCHAR(30) NOT NULL,
    phone_number VARCHAR(11) NOT NULL,
    postal_code VARCHAR(8) NOT NULL,
    state_abbr VARCHAR(2) NOT NULL,
    city VARCHAR(40) NOT NULL,
    neighborhood VARCHAR(40) NOT NULL,
    street VARCHAR(40) NOT NULL,
    house_number VARCHAR(10) NOT NULL,
    complement VARCHAR(10),
    password VARCHAR(150) NOT NULL,
    request_status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL,
    created_at DATETIME NOT NULL
);
