CREATE TABLE message (
    message_id SERIAL PRIMARY KEY,
    dialog_id INT NOT NULL,
    sender_name VARCHAR(30) NOT NULL,
    text VARCHAR(255) NOT NULL,
    send_timestamp TIMESTAMP NOT NULL, -- тип timestamp представляет и дату, и время
    FOREIGN KEY (dialog_id) REFERENCES dialog(dialog_id)
);