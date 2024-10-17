CREATE TABLE brand
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE product
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(30) NOT NULL,
    price    INT         NOT NULL,
    brand_id BIGINT,
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES Brand (id)
);