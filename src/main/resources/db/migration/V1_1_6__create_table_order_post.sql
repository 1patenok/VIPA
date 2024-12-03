CREATE TABLE order_post (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES completed_order(completed_order_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post(post_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);