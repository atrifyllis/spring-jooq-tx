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

-- when trying to add these permissions to initdb script it failed, so they were moved here
ALTER DEFAULT PRIVILEGES IN SCHEMA PUBLIC GRANT ALL ON TABLES to app_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA PUBLIC GRANT ALL ON SEQUENCES to app_user;
---------------------------
-- Create a sample table   --
---------------------------

CREATE TABLE public.expense
(
    expense_id       UUID PRIMARY KEY,
    amount           DECIMAL(12, 2) NOT NULL,
    description      VARCHAR(255)   NOT NULL,
    tenant_id        UUID           NOT NULL,
    optlock          BIGINT         NOT NULL,
    created_by       VARCHAR(255),
    created          TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by VARCHAR(255),
    last_modified    TIMESTAMP WITHOUT TIME ZONE,
    enabled          BOOLEAN
);

---------------------------
-- Enable RLS            --
---------------------------

ALTER TABLE public.expense
    ENABLE ROW LEVEL SECURITY;

---------------------------
-- Create the RLS Policy --
---------------------------

DROP POLICY IF EXISTS tenant_isolation_policy ON public.expense;

CREATE POLICY tenant_isolation_policy ON public.expense
    USING (tenant_id = current_setting('app.tenant_id')::UUID);



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
    email            VARCHAR(255),
    optlock          BIGINT NOT NULL,
    created_by       VARCHAR(255),
    created          TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by VARCHAR(255),
    last_modified    TIMESTAMP WITHOUT TIME ZONE,
    enabled          BOOLEAN
);

ALTER TABLE public.user_info
    ENABLE ROW LEVEL SECURITY;

DROP POLICY IF EXISTS tenant_isolation_policy ON public.user_info;

CREATE POLICY tenant_isolation_policy ON public.user_info
    USING (tenant_id = current_setting('app.tenant_id')::UUID);
