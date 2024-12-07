CREATE TABLE payment_account (
    payment_account_id SERIAL PRIMARY KEY,
    client_id INT NOT NULL UNIQUE,
    card_number VARCHAR(20) NOT NULL UNIQUE,
    current_sum DECIMAL(15, 2) DEFAULT 0,
    FOREIGN KEY (client_id) REFERENCES client(client_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);