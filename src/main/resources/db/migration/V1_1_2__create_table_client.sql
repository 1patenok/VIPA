CREATE TABLE client (
    client_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL DEFAULT CURRENT_DATE,
    phone_number VARCHAR(15) NOT NULL UNIQUE CHECK (phone_number ~ '^[+]?\\d{10,15}$'),
    email VARCHAR(80) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
