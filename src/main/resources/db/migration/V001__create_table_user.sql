CREATE TABLE tb_user (
    id BIGSERIAL PRIMARY KEY,
    user_identifier UUID NOT NULL,
    email VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    name VARCHAR(128) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    CONSTRAINT uk_tb_user_user_identifier UNIQUE (user_identifier),
    CONSTRAINT uk_tb_user_email UNIQUE (email)
);