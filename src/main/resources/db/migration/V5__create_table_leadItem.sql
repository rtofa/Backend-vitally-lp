CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_lead_item (

    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    lead_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,


    CONSTRAINT fk_lead_item_lead FOREIGN KEY (lead_id)
        REFERENCES tb_lead(id) ON DELETE CASCADE,

    CONSTRAINT fk_lead_item_product FOREIGN KEY (product_id)
        REFERENCES tb_product(id) ON DELETE RESTRICT
);