CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nick VARCHAR NOT NULL,
    login VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    insert_time TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE(nick, login)
);

CREATE TABLE vehicles (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    brand VARCHAR NOT NULL,
    model VARCHAR NOT NULL,
    insert_time TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE insurance_offers (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    insurer VARCHAR NOT NULL,
    price BIGINT NOT NULL,
    insert_time TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE vehicles_insurance_offers (
    vehicle_id BIGINT REFERENCES vehicles(id) ON DELETE CASCADE,
    insurance_offer_id BIGINT REFERENCES insurance_offers(id) ON DELETE CASCADE,
    UNIQUE(vehicle_id, insurance_offer_id)
);