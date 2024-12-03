CREATE TABLE message (
    message_id SERIAL PRIMARY KEY,
    sender_id INT NOT NULL,
    recipient_id INT NOT NULL,
    text VARCHAR(255) NOT NULL,
    send_time TIMESTAMP NOT NULL,
    send_date DATE NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (sender_id) REFERENCES client(client_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    FOREIGN KEY (recipient_id) REFERENCES client(client_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);