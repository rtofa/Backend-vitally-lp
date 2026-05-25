CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_product (

    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price NUMERIC(10, 2),
    image_url VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE NOT NULL
);