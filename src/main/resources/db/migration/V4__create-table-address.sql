CREATE TABLE tb_address
(
    id    VARCHAR(11) NOT NULL PRIMARY KEY,
    postal_code  VARCHAR(8)  NOT NULL,
    state_abbr   VARCHAR(2)  NOT NULL,
    city         VARCHAR(40) NOT NULL,
    neighborhood VARCHAR(40) NOT NULL,
    street       VARCHAR(40) NOT NULL,
    house_number VARCHAR(10) NOT NULL,
    complement   VARCHAR(10)
)