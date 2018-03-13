INSERT INTO users (dtype, username, password, enabled, age) VALUES
	('admin', 'hallo@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, 15),
('patient', 'haf@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, 16),
 	('patient',  'af@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, 17),
 	('admin',  'afk@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, 17);

INSERT INTO diagnosis (category, date, report) VALUES
  ('category1', '20120618', 'report1'),
  ('category1', '20120618', 'report1')

  INSERT INTO users_diagnoses(patient_user_id, diagnoses_id) VALUES
 (2, 1),
  (2, 2)

