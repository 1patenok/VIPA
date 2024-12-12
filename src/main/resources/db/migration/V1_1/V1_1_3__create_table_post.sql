CREATE TABLE post (
    post_id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    number_of_views INT NOT NULL,
    client_id INT NOT NULL,
    category_id INT NOT NULL,
    description TEXT NOT NULL CHECK (length(description) <= 1000),
    address VARCHAR(150) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (client_id) REFERENCES client(client_id)
);