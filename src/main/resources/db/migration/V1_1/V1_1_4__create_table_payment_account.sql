CREATE TABLE payment_account (
    payment_account_id SERIAL PRIMARY KEY,
    card_number VARCHAR(20) NOT NULL UNIQUE,
    current_sum DECIMAL(15, 2)
);