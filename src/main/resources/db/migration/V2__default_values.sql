INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO users(full_name, username, password) VALUES('Admin IWKZ', 'admin', '$2a$10$cq/cWw.fmpft1jdHhZA8ceEUoKi6YWh1U32aE54bhKspAA6MreQiG');

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