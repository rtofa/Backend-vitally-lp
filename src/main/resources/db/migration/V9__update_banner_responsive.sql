ALTER TABLE tb_banner ADD COLUMN desktop_image_url VARCHAR(255);
ALTER TABLE tb_banner ADD COLUMN mobile_image_url VARCHAR(255);
ALTER TABLE tb_banner DROP COLUMN image_url;