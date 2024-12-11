CREATE TABLE dialog (
    dialog_id SERIAL PRIMARY KEY,
    sender_id INT NOT NULL,
    recipient_id INT NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES client(client_id),
    FOREIGN KEY (recipient_id) REFERENCES client(client_id)
);