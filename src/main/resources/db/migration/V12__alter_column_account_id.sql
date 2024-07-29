ALTER TABLE `tb_pix_key`
    MODIFY COLUMN `account_id` VARCHAR(36) NOT NULL;
ALTER TABLE `tb_transaction_password`
    MODIFY COLUMN `account_id` VARCHAR(36) NOT NULL;