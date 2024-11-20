CREATE TABLE IF NOT EXISTS rooms(
    id serial PRIMARY KEY,
    capacity int not null,
    name varchar(255) not null,
    is_active boolean not null default(true)
);

CREATE TABLE IF NOT EXISTS weekdays(
    id serial primary key,
    day varchar(255) not null,
    is_active boolean not null default(true),
    start_time time not null,
    end_time time not null,
    room serial not null,

    UNIQUE(day, room),
    CONSTRAINT room_fk FOREIGN KEY (room) REFERENCES rooms(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sections(
    id serial primary key,
    name varchar(255) not null,
    short_name varchar(255) not null,
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS settings(
    id serial primary key,
    theme varchar(255) not null
);

CREATE TABLE IF NOT EXISTS users(
    email varchar(255) primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null,
    section serial not null,
    is_account_non_expired	boolean not null default(true),
    is_account_non_locked boolean not null default(true),
    is_credentials_non_expired boolean not null default(true),
    is_enabled boolean not null default(true),
    settings serial not null,

    CONSTRAINT section_fk FOREIGN KEY (section) REFERENCES sections(id) ON DELETE CASCADE,
    CONSTRAINT settings_fk FOREIGN KEY (settings) REFERENCES settings(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS authorities(
    id serial primary key,
    email varchar(50) not null,
    authority varchar(50) not null,

    UNIQUE(email, authority),
    CONSTRAINT email_fk FOREIGN KEY (email) REFERENCES users(email) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION add_authority() RETURNS TRIGGER AS '
    BEGIN
        INSERT INTO authorities (email, authority) VALUES (NEW.email, ''USER'');
        RETURN NEW;
    END;
' LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_add_authority on "public"."users";

CREATE TRIGGER trigger_add_authority
    AFTER INSERT
    ON users
    FOR EACH ROW
    EXECUTE FUNCTION add_authority();