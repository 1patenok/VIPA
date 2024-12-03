CREATE TABLE post (
    post_id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL DEFAULT 0,
    status VARCHAR(20),
    client_id INT NOT NULL,
    category_id INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(150) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(category_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
    FOREIGN KEY (client_id) REFERENCES client(client_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);