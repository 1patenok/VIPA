-- Категория "Электроника" (category_id = 1)
INSERT INTO post (title, price, status, number_of_views, client_id, category_id, description, address, created_at)
VALUES
('Телевизор Samsung', 50000, 'ACTIVE', 0, 1, 1, 'Телевизор с отличным качеством изображения', 'Ул. Ленина д.12', CURRENT_DATE),
('Смартфон iPhone', 70000, 'ACTIVE', 0, 5, 1, 'Мощный iPhone с большим экраном', 'Ул. Маяковского д.45', CURRENT_DATE),
('Ноутбук ASUS', 40000, 'ACTIVE', 0, 3, 1, 'Ноутбук для работы и игр', 'Ул. Тверская д.9', CURRENT_DATE);

-- Категория "Одежда и обувь" (category_id = 2)
INSERT INTO post (title, price, status, number_of_views, client_id, category_id, description, address, created_at)
VALUES
('Куртка кожаная', 15000, 'ACTIVE', 0, 4, 2, 'Кожаная куртка, размер M', 'Ул. Горького д.5', CURRENT_DATE),
('Кроссовки Nike', 5000, 'ACTIVE', 0, 5, 2, 'Кроссовки для спорта и повседневной носки', 'Ул. Пушкина д.15', CURRENT_DATE),
('Свитер шерстяной', 3000, 'ACTIVE', 0, 1, 2, 'Тёплый свитер, размер L', 'Ул. Суворова д.3', CURRENT_DATE);

-- Категория "Автомобили" (category_id = 3)
INSERT INTO post (title, price, status, number_of_views, client_id, category_id, description, address, created_at)
VALUES
('Мерседес', 1000000, 'ACTIVE', 0, 3, 3, 'Машина супер, 4 колеса, две фары', 'Ул. Попова д.39', CURRENT_DATE),
('Лада', 50000, 'ACTIVE', 0, 5, 3, 'Удобный автомобиль для города', 'Ул. Калинина д.8', CURRENT_DATE),
('BMW X5', 2500000, 'ACTIVE', 0, 1, 3, 'Роскошный внедорожник с полным приводом', 'Ул. Севастопольская д.22', CURRENT_DATE);

-- Категория "Недвижимость" (category_id = 4)
INSERT INTO post (title, price, status, number_of_views, client_id, category_id, description, address, created_at)
VALUES
('Квартира в центре', 8000000, 'ACTIVE', 0, 3, 4, 'Просторная квартира с видом на парк', 'Ул. Ленина д.50', CURRENT_DATE),
('Дача с участком', 3000000, 'ACTIVE', 0, 4, 4, 'Прекрасная дача с садом и огородом', 'Ул. Озёрная д.14', CURRENT_DATE);

-- Категория "Дом и сад" (category_id = 5)
INSERT INTO post (title, price, status, number_of_views, client_id, category_id, description, address, created_at)
VALUES
('Газонокосилка', 10000, 'ACTIVE', 0, 1, 5, 'Газонокосилка с хорошей мощностью', 'Ул. Садовая д.18', CURRENT_DATE),
('Садовый насос', 3000, 'ACTIVE', 0, 3, 5, 'Насос для полива садовых участков', 'Ул. Цветочная д.22', CURRENT_DATE),
('Качели садовые', 5000, 'ACTIVE', 0, 4, 5, 'Удобные качели для отдыха в саду', 'Ул. Черемушки д.5', CURRENT_DATE);

-- Категория "Детские товары" (category_id = 6)
INSERT INTO post (title, price, status, number_of_views, client_id, category_id, description, address, created_at)
VALUES
('Коляска для малыша', 15000, 'ACTIVE', 0, 1, 6, 'Коляска с системой амортизации', 'Ул. Ярославская д.10', CURRENT_DATE),
('Детская кроватка', 8000, 'ACTIVE', 0, 5, 6, 'Кроватка с регулируемой высотой', 'Ул. Белинского д.8', CURRENT_DATE),
('Игрушки для младенцев', 2000, 'ACTIVE', 0, 3, 6, 'Безопасные игрушки для новорожденных', 'Ул. Карла Маркса д.20', CURRENT_DATE);

-- Категория "Спорт и отдых" (category_id = 7)
INSERT INTO post (title, price, status, number_of_views, client_id, category_id, description, address, created_at)
VALUES
('Теннисные ракетки', 3000, 'ACTIVE', 0, 4, 7, 'Ракетки для начинающих и профессионалов', 'Ул. Спортивная д.13', CURRENT_DATE),
('Велосипед', 10000, 'ACTIVE', 0, 5, 7, 'Горный велосипед для активного отдыха', 'Ул. Мичурина д.7', CURRENT_DATE),
('Рюкзак для туризма', 1500, 'ACTIVE', 0, 1, 7, 'Удобный рюкзак для длительных походов', 'Ул. Пролетарская д.5', CURRENT_DATE);

-- Категория "Учебные материалы" (category_id = 8)
INSERT INTO post (title, price, status, number_of_views, client_id, category_id, description, address, created_at)
VALUES
('Учебник по математике', 500, 'ACTIVE', 0, 1, 8, 'Учебник для студентов и школьников', 'Ул. Университетская д.12', CURRENT_DATE),
('Практическая физика', 600, 'ACTIVE', 0, 4, 8, 'Книга с заданиями для студентов', 'Ул. Гагарина д.18', CURRENT_DATE);

-- Категория "Красота и здоровье" (category_id = 9)
INSERT INTO post (title, price, status, number_of_views, client_id, category_id, description, address, created_at)
VALUES
('Крем для лица', 1500, 'ACTIVE', 0, 1, 9, 'Крем с витаминами для увлажнения кожи', 'Ул. Кирова д.25', CURRENT_DATE),
('Массажный аппарат', 2000, 'ACTIVE', 0, 3, 9, 'Аппарат для расслабляющего массажа', 'Ул. Красная д.10', CURRENT_DATE);
