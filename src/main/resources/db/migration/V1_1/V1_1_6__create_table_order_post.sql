CREATE TABLE order_post (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES t_order(id),
    FOREIGN KEY (post_id) REFERENCES post(post_id),
    CONSTRAINT uk_order_post UNIQUE(order_id, post_id)
);