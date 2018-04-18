INSERT INTO users (dtype, username  , password, enabled, age, lastname, firstname, gender, street, city, zipcode, housenumber, doctors_user_id) VALUES
	('doctor', 'sanderklijsen@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, '2015-07-27 00:00:00.000', 'klijsen', 'sander', 'male', 'straat', 'Eindhoven', '1010xx', '10', null),
	('doctor', 'timkurvers@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, '2015-07-27 00:00:00.000', 'kurvers', 'tim', 'male', 'straat', 'Eindhoven', '1010xx', '10', null),
  ('patient', 'devinpennings@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, '2015-07-27 00:00:00.000', 'pennings', 'devin', 'male', 'straat', 'Eindhoven', '1010xx', '10', 1),
 	('patient',  'daveyvandenbogaard@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, '2015-07-27 00:00:00.000', 'bogaard', 'davey', 'male', 'straat', 'Eindhoven', '1010xx', '10', 1),
 	('admin',  'stefangies@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, '2015-07-27 00:00:00.000' , 'gies', 'stefan', 'male', 'straat', 'Eindhoven', '1010xx', '10', null),
 	('admin',  'ottonaus@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, '2015-07-27 00:00:00.000' , 'naus', 'otto', 'male', 'straat', 'Eindhoven', '1010xx', '10', null),
 	('patient',  'jeroenwasser@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, '2015-07-27 00:00:00.000' , 'wasser', 'jeroen', 'male', 'straat', 'Eindhoven', '1010xx', '10', 1),
 	('doctorEmployee', 'doctoremployee@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', 1, '2015-07-27 00:00:00.000' , 'wasser', 'jeroen', 'male', 'straat', 'Eindhoven', '1010xx', '10', 1);

drop table if exists oauth_client_details;
create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

drop table if exists oauth_client_token;
create table oauth_client_token (
  token_id VARCHAR(255),
  token varbinary(max),
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

drop table if exists oauth_access_token;
create table oauth_access_token (
  token_id VARCHAR(255),
  token varbinary(max),
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication varbinary(max),
  refresh_token VARCHAR(255)
);

drop table if exists oauth_refresh_token;
create table oauth_refresh_token (
  token_id VARCHAR(255),
  token varbinary(max),
  authentication varbinary(max)
);

drop table if exists oauth_code;
create table oauth_code (
  code VARCHAR(255), authentication varbinary(max)
);

drop table if exists oauth_approvals;
create table oauth_approvals (
    user_id VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt TIMESTAMP
);

drop table if exists ClientDetails;
create table ClientDetails (
  appId VARCHAR(255) PRIMARY KEY,
  resourceIds VARCHAR(255),
  appSecret VARCHAR(255),
  scope VARCHAR(255),
  grantTypes VARCHAR(255),
  redirectUrl VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(255)
);
