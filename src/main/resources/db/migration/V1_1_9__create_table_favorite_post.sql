CREATE TABLE favorite_post (
    favorite_post_id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(post_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES client(client_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);