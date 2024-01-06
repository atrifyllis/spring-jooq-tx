---------------------------
-- Create the app_user   --
---------------------------


DO
$do$
    BEGIN
        IF EXISTS (SELECT
                   FROM pg_catalog.pg_roles
                   WHERE rolname = 'app_user') THEN

            RAISE NOTICE 'Role "app_user" already exists. Skipping.';
        ELSE
            CREATE ROLE app_user LOGIN PASSWORD 'app_user';
        END IF;
    END
$do$;

-- CREATE ROLE IF NOT EXISTS app_user LOGIN PASSWORD 'app_user';

--------------------------------
-- Grant Access to the Schema --
--------------------------------

ALTER DEFAULT PRIVILEGES IN SCHEMA PUBLIC GRANT ALL ON TABLES to app_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA PUBLIC GRANT ALL ON SEQUENCES to app_user;



CREATE TABLE tenant
(
    id               UUID PRIMARY KEY,
    optlock          BIGINT NOT NULL,
    created_by       VARCHAR(255),
    created          TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by VARCHAR(255),
    last_modified    TIMESTAMP WITHOUT TIME ZONE,
    enabled          BOOLEAN
);

CREATE TABLE user_info
(
    id               UUID PRIMARY KEY,
    tenant_id        UUID REFERENCES tenant (id),
    email            VARCHAR(20),
    optlock          BIGINT NOT NULL,
    created_by       VARCHAR(255),
    created          TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by VARCHAR(255),
    last_modified    TIMESTAMP WITHOUT TIME ZONE,
    enabled          BOOLEAN
);
