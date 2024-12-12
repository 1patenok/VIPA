CREATE TABLE completed_order (
    completed_order_id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    price INT NOT NULL,
    payment_method VARCHAR(35) NOT NULL,
    time_of_delivery VARCHAR(35) NOT NULL,
    address VARCHAR(150) NOT NULL,
    delivery_method VARCHAR(50) NOT NULL,
    status_of_delivery VARCHAR(25) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(client_id)
);