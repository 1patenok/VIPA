CREATE TABLE completed_order (
    completed_order_id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    price DECIMAL(15, 2) NOT NULL,
    address VARCHAR(150) NOT NULL,
    delivery_method VARCHAR(50) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(client_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);