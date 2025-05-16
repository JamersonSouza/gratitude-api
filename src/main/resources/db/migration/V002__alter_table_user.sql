ALTER TABLE tb_user RENAME COLUMN user_identifier TO identifier;
ALTER TABLE tb_user RENAME CONSTRAINT uk_tb_user_user_identifier TO uk_tb_user_identifier;