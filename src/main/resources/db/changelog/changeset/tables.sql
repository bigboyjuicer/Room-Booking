-- liquibase formatted sql

-- changeset Maksim Zinin:create-address
CREATE TABLE IF NOT EXISTS address(
    id serial PRIMARY KEY,
    region varchar(255) not null,
    city varchar(255) not null,
    street varchar(255) not null,
    building varchar(255) not null,

    UNIQUE(city, street, building)
);
-- rollback DROP TABLE address;

-- changeset Maksim Zinin:create-rooms
CREATE TABLE IF NOT EXISTS rooms(
    id serial PRIMARY KEY,
    capacity int not null,
    name varchar(255) not null,
    is_active boolean not null default(true),
    image_path varchar(255) not null,
    address int not null,

    CONSTRAINT address_fk FOREIGN KEY (address) REFERENCES address(id) ON DELETE CASCADE
);
-- rollback DROP TABLE rooms;

-- changeset Maksim Zinin:create-weekdays
CREATE TABLE IF NOT EXISTS weekdays(
    id serial primary key,
    day integer not null,
    is_active boolean not null default(true),
    start_time time,
    end_time time,
    room integer not null,

    UNIQUE(day, room),
    CONSTRAINT room_fk FOREIGN KEY (room) REFERENCES rooms(id) ON DELETE CASCADE
);
-- rollback DROP TABLE weekdays;

-- changeset Maksim Zinin:create-sections
CREATE TABLE IF NOT EXISTS sections(
    id serial primary key,
    name varchar(255) not null,
    short_name varchar(255) not null,
    UNIQUE(name)
    );
-- rollback DROP TABLE sections;

-- changeset Maksim Zinin:create-settings
CREATE TABLE IF NOT EXISTS settings(
    id serial primary key,
    theme varchar(255) not null
    );
-- rollback DROP TABLE settings;

-- changeset Maksim Zinin:create-users
CREATE TABLE IF NOT EXISTS users(
    email varchar(255) primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null,
    section int not null,
    is_account_non_expired	boolean not null default(true),
    is_account_non_locked boolean not null default(true),
    is_credentials_non_expired boolean not null default(true),
    is_enabled boolean not null default(true),
    settings int not null,

    CONSTRAINT section_fk FOREIGN KEY (section) REFERENCES sections(id) ON DELETE CASCADE,
    CONSTRAINT settings_fk FOREIGN KEY (settings) REFERENCES settings(id) ON DELETE CASCADE
);
-- rollback DROP TABLE users;

-- changeset Maksim Zinin:create-authorities
CREATE TABLE IF NOT EXISTS authorities(
    id serial primary key,
    email varchar(50) not null,
    authority varchar(50) not null,

    UNIQUE(email, authority),
    CONSTRAINT email_fk FOREIGN KEY (email) REFERENCES users(email) ON DELETE CASCADE
);
-- rollback DROP TABLE authorities;

-- changeset Maksim Zinin:create-refresh_tokens
CREATE TABLE IF NOT EXISTS refresh_tokens(
    id serial primary key,
    email varchar(255) not null,
    refresh_token varchar(255) not null,

    UNIQUE(email),
    UNIQUE(refresh_token),
    CONSTRAINT email_fk FOREIGN KEY (email) REFERENCES users(email) ON DELETE CASCADE
);
-- rollback DROP TABLE refresh_tokens;

-- changeset Maksim Zinin:create-bookings
CREATE TABLE IF NOT EXISTS bookings(
    id serial primary key,
    room int not null,
    email varchar(256) not null,
    time timestamp not null,

    UNIQUE(room, time),
    CONSTRAINT room_fk FOREIGN KEY (room) REFERENCES rooms(id) ON DELETE CASCADE,
    CONSTRAINT email_fk FOREIGN KEY (email) REFERENCES users(email) ON DELETE CASCADE
);
-- rollback DROP TABLE bookings;