CREATE TABLE post_image (
    post_image_id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(post_id),
    CONSTRAINT uk_post_image UNIQUE(post_image_id, post_id)
);