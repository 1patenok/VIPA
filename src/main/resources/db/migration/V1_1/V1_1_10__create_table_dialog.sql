CREATE TABLE dialog (
    dialog_id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES client(client_id),
    FOREIGN KEY (post_id) REFERENCES post(post_id)
);