CREATE TABLE tb_client (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    date_of_birth VARCHAR(10) NOT NULL,
    email VARCHAR(30) NOT NULL,
    phone_number VARCHAR(11) NOT NULL,
    address_id VARCHAR(36) NOT NULL
)