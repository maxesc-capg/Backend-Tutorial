INSERT INTO category(name)
VALUES ('Eurogames');
INSERT INTO category(name)
VALUES ('Ameritrash');
INSERT INTO category(name)
VALUES ('Familiar');
INSERT INTO author(name, nationality)
VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality)
VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality)
VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality)
VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality)
VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality)
VALUES ('Phil Walker-Harding', 'US');
INSERT INTO game(title, age, category_id, author_id)
VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Azul', '8', 3, 5);

INSERT INTO customer(name)
VALUES ('Laufey Matters');
INSERT INTO customer(name)
VALUES ('Lewis Hamilton');
INSERT INTO customer(name)
VALUES ('Charles Leclerc');

INSERT INTO loans (id, game_id, customer_id, loan_start, loan_end) VALUES (1, 1, 1, '2024-01-01', '2024-01-10');
INSERT INTO loans (id, game_id, customer_id, loan_start, loan_end) VALUES (2, 2, 2, '2024-01-05', '2024-01-15');
INSERT INTO loans (id, game_id, customer_id, loan_start, loan_end) VALUES (3, 3, 3, '2024-01-10', '2024-01-20');
INSERT INTO loans (id, game_id, customer_id, loan_start, loan_end) VALUES (4, 1, 2, '2024-02-01', '2024-02-10');
INSERT INTO loans (id, game_id, customer_id, loan_start, loan_end) VALUES (5, 2, 3, '2024-02-05', '2024-02-15');
INSERT INTO loans (id, game_id, customer_id, loan_start, loan_end) VALUES (6, 3, 1, '2024-02-10', '2024-02-20');

--- Añadido para tests con H2
ALTER TABLE loans ALTER COLUMN id RESTART WITH 7;