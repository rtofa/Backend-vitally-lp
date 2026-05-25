CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_banner (

    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(255),
    image_url VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    display_order INTEGER NOT NULL
);