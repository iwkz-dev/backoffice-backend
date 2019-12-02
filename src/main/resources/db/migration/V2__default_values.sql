INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_USER');

/* admin1WK2.de! */
INSERT INTO users(full_name, username, password) VALUES('Admin IWKZ', 'admin', '$2a$10$cq/cWw.fmpft1jdHhZA8ceEUoKi6YWh1U32aE54bhKspAA6MreQiG');
INSERT INTO user_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (1, 2);
/* 1WK2.de!!! */
INSERT INTO users(full_name, username, password) VALUES('User IWKZ.ev', 'iwkz', '$2a$10$CBdf89XK1qRc4RfzYI/lPeXngYOLGA0l3eyO02hSt9l99X2ywfTei');
INSERT INTO user_roles(user_id, role_id) VALUES (2, 2);

INSERT INTO income_types(name) VALUES ('sedekah');
INSERT INTO income_types(name) VALUES ('kotak_jumat');
INSERT INTO income_types(name) VALUES ('kantin_jumat');
INSERT INTO income_types(name) VALUES ('toko_alfalah');
INSERT INTO income_types(name) VALUES ('dauerauftrag');
INSERT INTO income_types(name) VALUES ('sate_somay');

INSERT INTO bill_types(name, description) VALUES ('miete', 'brutto miete sudah termasuk air, heizungkosten & nebenkosten');
INSERT INTO bill_types(name) VALUES ('keperluan_rt');
INSERT INTO bill_types(name) VALUES ('kebersihan');
INSERT INTO bill_types(name) VALUES ('listrik');
INSERT INTO bill_types(name) VALUES ('nachzahlung');
INSERT INTO bill_types(name) VALUES ('internet_hosting');