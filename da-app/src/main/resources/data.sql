--INSERT INTO oauth_client_details
--	(client_id, client_secret, scope, authorized_grant_types,
--	web_server_redirect_uri, authorities, access_token_validity,
--	refresh_token_validity, additional_information, autoapprove)
--VALUES
--	('fooClientIdPassword', '$2a$10$7yhqrOyvOieA6H2R9WOgxOXvCAEagNihZe1hFsluwFEJy9HbYCz2q', 'bar,foo,read,write',
--	'password,authorization_code,refresh_token,client_credentials', null, null, 36000, 36000, null, true);
--INSERT INTO oauth_client_details
--	(client_id, client_secret, scope, authorized_grant_types,
--	web_server_redirect_uri, authorities, access_token_validity,
--	refresh_token_validity, additional_information, autoapprove)
--VALUES
--	('sampleClientId', 'secret', 'read,write,foo,bar',
--	'implicit', null, null, 36000, 36000, null, false);
--INSERT INTO oauth_client_details
--	(client_id, client_secret, scope, authorized_grant_types,
--	web_server_redirect_uri, authorities, access_token_validity,
--	refresh_token_validity, additional_information, autoapprove)
--VALUES
--	('barClientIdPassword', '$2a$10$7yhqrOyvOieA6H2R9WOgxOXvCAEagNihZe1hFsluwFEJy9HbYCz2q', 'bar,foo,read,write',
--	'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);

INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	("core_client", "$2a$10$ePZ9kwbN2JyipmgJeCT4RudBZJ5Cwt6HeI1KzdHwJt37.WUFAE.a2", "read,write,delete", "password,authorization_code,refresh_token", null, null, 36000, 36000, null, true);	

INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	("example_client", "$2a$10$ePZ9kwbN2JyipmgJeCT4RudBZJ5Cwt6HeI1KzdHwJt37.WUFAE.a2", "read,write,delete", "password,authorization_code,refresh_token", null, null, 36000, 36000, null, true);

INSERT INTO tbl_province (name)
VALUES ('Hà Nội');
INSERT INTO tbl_province (name)
VALUES ('Hải Phòng');
INSERT INTO tbl_province (name)
VALUES ('Hưng Yên');

INSERT INTO tbl_district (name, province_id)
VALUES ('Thanh Trì', '1');
INSERT INTO tbl_district (name, province_id)
VALUES ('Thanh Xuân', '1');
INSERT INTO tbl_district (name, province_id)
VALUES ('Hoàng Mai', '1');
INSERT INTO tbl_district (name, province_id)
VALUES ('Cát Bà', '2');
INSERT INTO tbl_district (name, province_id)
VALUES ('Thuỷ Nguyên', '2');
INSERT INTO tbl_district (name, province_id)
VALUES ('Lạch Tray', '2');

INSERT INTO tbl_commune (name, district_id)
VALUES ('Văn Điển', '1');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Tam Hiệp', '1');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Tứ Hiệp', '1');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Thanh Xuân Nam', '2');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Thanh Xuân Bắc', '2');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Bằng Liệt', '3');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Hoàng Liệt', '3');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Cát Bà commune 1', '4');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Cát Bà commune 2', '4');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Lạch Tray commune 1', '6');
INSERT INTO tbl_commune (name, district_id)
VALUES ('Lạch Tray commune 2', '6');

INSERT INTO tbl_employee (code, name, email, phone, age, province_id, district_id, commune_id)
VALUES ('abcde00', 'Đạt', 'dat@gmail.com', '0968436800', '25', '1', '1', '1');
INSERT INTO tbl_employee (code, name, email, phone, age, province_id, district_id, commune_id)
VALUES ('abcde01', 'Nam', 'nam@gmail.com', '0968444480', '24', '1', '1', '2');
INSERT INTO tbl_employee (code, name, email, phone, age, province_id, district_id, commune_id)
VALUES ('abcde02', 'Sơn', 'son@gmail.com', '0968436811', '23', '1', '2', '4');
INSERT INTO tbl_employee (code, name, email, phone, age, province_id, district_id, commune_id)
VALUES ('abcde03', 'Hùng', 'hung@gmail.com', '0968436877', '20', '2', '4', '8');

INSERT INTO tbl_certificate (name)
VALUES ('Toán');
INSERT INTO tbl_certificate (name)
VALUES ('Lý');
INSERT INTO tbl_certificate (name)
VALUES ('Hoá');

INSERT INTO tbl_employee_certificate (employee_id, certificate_id, province_id, start_date, expiration_date)
VALUES ('1', '1', '1', '2002-10-20', '2024-1-1');
INSERT INTO tbl_employee_certificate (employee_id, certificate_id, province_id, start_date, expiration_date)
VALUES ('1', '2', '1', '2019-2-20', '2024-11-1');
INSERT INTO tbl_employee_certificate (employee_id, certificate_id, province_id, start_date, expiration_date)
VALUES ('2', '1', '2', '2002-10-20', '2024-3-4');
INSERT INTO tbl_employee_certificate (employee_id, certificate_id, province_id, start_date, expiration_date)
VALUES ('2', '3', '3', '2012-10-20', '2024-2-1');
INSERT INTO tbl_employee_certificate (employee_id, certificate_id, province_id, start_date, expiration_date)
VALUES ('3', '3', '2', '2002-10-20', '2025-1-1');