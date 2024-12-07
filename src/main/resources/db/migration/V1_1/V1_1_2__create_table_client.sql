CREATE TABLE client (
    client_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(80) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
