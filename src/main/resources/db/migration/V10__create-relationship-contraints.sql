ALTER TABLE tb_pix_key
ADD CONSTRAINT fk_account_access_user
FOREIGN KEY (account_id)
REFERENCES tb_account(id);

ALTER TABLE tb_client
ADD CONSTRAINT fk_user_address
FOREIGN KEY (address_id)
REFERENCES tb_address(id);

ALTER TABLE tb_account
ADD CONSTRAINT fk_account_client
FOREIGN KEY (client_id)
REFERENCES tb_client(id)
ON DELETE CASCADE;

ALTER TABLE tb_account
ADD CONSTRAINT fk_account_account_access
FOREIGN KEY (account_access_id)
REFERENCES tb_account_access(id)
ON DELETE CASCADE;

ALTER TABLE tb_transaction_password
ADD CONSTRAINT fk_transaction_password_account
FOREIGN KEY (account_id)
REFERENCES tb_account(id)
ON DELETE CASCADE;

ALTER TABLE tb_transaction
ADD CONSTRAINT fk_transaction_sender
FOREIGN KEY (sender_id)
REFERENCES tb_account(id);

ALTER TABLE tb_transaction
ADD CONSTRAINT fk_transaction_receiver
FOREIGN KEY (receiver_id)
REFERENCES tb_account(id);