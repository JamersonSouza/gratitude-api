CREATE TABLE tb_card (
    id BIGSERIAL PRIMARY KEY,
    identifier UUID NOT NULL,
    card_type_enum VARCHAR(255) NOT NULL,
    text VARCHAR(255),
    color VARCHAR(50),
    is_favorite BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    user_id BIGINT,

    CONSTRAINT fk_user
        FOREIGN KEY(user_id) REFERENCES tb_user(id)
);
