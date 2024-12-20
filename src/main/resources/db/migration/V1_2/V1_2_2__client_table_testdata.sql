INSERT INTO client(name, surname, birth_date, phone_number, email, password, image, role)
VALUES
('Вадим', 'Ипатов', CURRENT_DATE,'+79811319587', 'ipatovvadim138@yandex.ru', '$2a$10$Vtn1QPdrI9qOCsvE.40NPuX7VBU8kCJKAKsVnNGr8r0u//kEIotOW', '/images/client-images/hacker.jpg', 'ROLE_CLIENT'), -- пароль = Admin1337
('Никита', 'Трифонов', CURRENT_DATE,'+79119059964', 'trifa.nik@mail.ru', '$2a$10$Vtn1QPdrI9qOCsvE.40NPuX7VBU8kCJKAKsVnNGr8r0u//kEIotOW', '/images/client-images/man.jpg', 'ROLE_ADMIN'),-- пароль = Admin1337
('Денис', 'Попов', CURRENT_DATE,'+79333333333', 'popov@gmail.com', '$2a$10$Vtn1QPdrI9qOCsvE.40NPuX7VBU8kCJKAKsVnNGr8r0u//kEIotOW', '/images/client-images/redman.jpg', 'ROLE_CLIENT'),-- пароль = Admin1337
('Шухрат', 'Асроров', CURRENT_DATE,'+789112437947', 'shuhratasrorov@gmail.com', '$2a$10$Vtn1QPdrI9qOCsvE.40NPuX7VBU8kCJKAKsVnNGr8r0u//kEIotOW', '/images/client-images/shuhrat.jpg', 'ROLE_CLIENT'),-- пароль = Admin1337
('Георгий', 'Кедров', CURRENT_DATE,'+79114844859', 'kedrov@gmail.com', '$2a$10$Vtn1QPdrI9qOCsvE.40NPuX7VBU8kCJKAKsVnNGr8r0u//kEIotOW', '/images/client-images/womeninglaces.jpg', 'ROLE_CLIENT')-- пароль = Admin1337
;