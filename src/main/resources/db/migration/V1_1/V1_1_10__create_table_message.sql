CREATE TABLE message (
    message_id SERIAL PRIMARY KEY,
    sender_id INT NOT NULL,
    recipient_id INT NOT NULL,
    text VARCHAR(255) NOT NULL,
    send_timestamp TIMESTAMP NOT NULL, -- тип timestamp представляет и дату, и время
    FOREIGN KEY (sender_id) REFERENCES client(client_id),
    FOREIGN KEY (recipient_id) REFERENCES client(client_id)
);