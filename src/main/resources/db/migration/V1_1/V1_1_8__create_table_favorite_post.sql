CREATE TABLE favorite_post (
    favorite_post_id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(post_id),
    FOREIGN KEY (client_id) REFERENCES client(client_id),
    CONSTRAINT uk_favorite_post UNIQUE(client_id, post_id)
);