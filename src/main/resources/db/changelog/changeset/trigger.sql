-- liquibase formatted sql

-- changeset Maksim Zinin:create-trigger
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
-- rollback DROP TRIGGER IF EXISTS trigger_add_authority ON "public"."users";
-- rollback DROP FUNCTION IF EXISTS add_authority();