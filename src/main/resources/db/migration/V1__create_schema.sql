CREATE TABLE insurance_request
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    vehicle_type       TEXT             NOT NULL,
    postal_code        TEXT             NOT NULL,
    annual_mileage     INTEGER          NOT NULL,
    calculated_premium DOUBLE PRECISION NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);

CREATE TABLE post_code
(
    id          SERIAL PRIMARY KEY,
    postal_code VARCHAR(10)  NOT NULL UNIQUE,
    state       VARCHAR(100) NOT NULL,
    region      VARCHAR(100),
    district    VARCHAR(100),
    city        VARCHAR(100),
    quarter     VARCHAR(100)
);

CREATE TABLE region_factor
(
    id     SERIAL PRIMARY KEY,
    state  VARCHAR(100)     NOT NULL UNIQUE,
    factor DOUBLE PRECISION NOT NULL
);

CREATE TABLE database_migration
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    file_name  TEXT NOT NULL UNIQUE,
    applied_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);