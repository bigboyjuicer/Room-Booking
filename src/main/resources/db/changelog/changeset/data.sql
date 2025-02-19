-- liquibase formatted sql

-- changeset Maksim Zinin:insert-address
INSERT INTO address(region, city, street, building) VALUES ('Europe/Moscow', 'Moscow', 'Lenina', '4');
-- rollback DELETE FROM address;

-- changeset Maksim Zinin:insert-rooms
INSERT INTO rooms(capacity, name, image_path, address) VALUES (20, 'Aboba', 'src/main/resources/static/images/20d88a2e-8c38-48ac-b831-82f2495053aa_9b48d2bfb99846347b0e096218fd5764.jpg', 1);
-- rollback DELETE FROM rooms;

-- changeset Maksim Zinin:insert-weekdays
INSERT INTO weekdays(day, start_time, end_time, room) VALUES (1, '09:00:00', '18:00:00', 1);
INSERT INTO weekdays(day, start_time, end_time, room) VALUES (2, '12:00:00', '21:00:00', 1);
INSERT INTO weekdays(day, is_active, room) VALUES (3, false, 1);
INSERT INTO weekdays(day, start_time, end_time, room) VALUES (4, '15:00:00', '18:00:00', 1);
INSERT INTO weekdays(day, start_time, end_time, room) VALUES (5, '09:00:00', '15:00:00', 1);
INSERT INTO weekdays(day, is_active, room) VALUES (6, false, 1);
INSERT INTO weekdays(day, is_active, room) VALUES (7, false, 1);
-- rollback DELETE FROM weekdays;

-- changeset Maksim Zinin:insert-sections
INSERT INTO sections(name, short_name) VALUES ('Development and Operations', 'DevOps');
INSERT INTO sections(name, short_name) VALUES ('Information Technology', 'IT');
-- rollback DELETE FROM sections;

-- changeset Maksim Zinin:insert-settings
INSERT INTO settings(theme) VALUES ('Default');
-- rollback DELETE FROM settings;

-- changeset Maksim Zinin:inset-users
INSERT INTO users(email, first_name, last_name, password, section, settings) VALUES ('m.zinin@inbox.ru', 'Maksim', 'Zinin', '$2a$12$1uXSj1ddft2Keu0qzO7z2uIoK3/pzLHT68XzodIDcW8pGwlWNVrA.', 2, 1);
INSERT INTO users(email, first_name, last_name, password, section, settings) VALUES ('bigboyjuicer@vk.com', 'BigBoy', 'Juicer', '$2a$12$1uXSj1ddft2Keu0qzO7z2uIoK3/pzLHT68XzodIDcW8pGwlWNVrA.', 1, 1);
-- rollback DELETE FROM users;

-- changeset Maksim Zinin:insert-authorities
INSERT INTO authorities(email, authority) VALUES ('m.zinin@inbox.ru', 'ADMIN');
-- rollback DELETE FROM authorities;

-- changeset Maksim Zinin:insert-refresh_tokens
INSERT INTO refresh_tokens(email, refresh_token) VALUES ('m.zinin@inbox.ru', '123');
INSERT INTO refresh_tokens(email, refresh_token) VALUES ('bigboyjuicer@vk.com', '1234');
-- rollback DELETE FROM refresh_tokens;