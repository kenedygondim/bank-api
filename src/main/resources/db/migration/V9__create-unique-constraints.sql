ALTER TABLE tb_account_access
    ADD CONSTRAINT unique_login UNIQUE (login);

ALTER TABLE tb_client
    ADD CONSTRAINT unique_cpf UNIQUE (cpf);
ALTER TABLE tb_client
    ADD CONSTRAINT unique_email UNIQUE (email);
ALTER TABLE tb_client
    ADD CONSTRAINT unique_phone_number UNIQUE (phone_number);

ALTER TABLE tb_account_request
    ADD CONSTRAINT unique_cpf UNIQUE (cpf);
ALTER TABLE tb_account_request
    ADD CONSTRAINT unique_email UNIQUE (email);
ALTER TABLE tb_account_request
    ADD CONSTRAINT unique_phone_number UNIQUE (phone_number);

ALTER TABLE tb_account
    ADD CONSTRAINT unique_account_number UNIQUE (account_number);

