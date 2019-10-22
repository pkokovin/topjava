DELETE FROM user_roles;
delete from meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

insert into meals (user_id, date_time, description, calories) values
(100000, '2015-05-30 10:00', 'User Breakfast', 500),
(100000, '2015-05-30 13:00', 'User Dinner', 1000),
(100000, '2015-05-30 20:00', 'User Supper', 500),
(100000, '2015-05-31 10:00', 'User Breakfast', 500),
(100000, '2015-05-31 13:00', 'User Dinner', 1000),
(100000, '2015-05-31 20:00', 'User Supper', 510),
(100001, '2016-05-30 10:00', 'Admin Breakfast', 500),
(100001, '2016-05-30 13:00', 'Admin Dinner', 1000),
(100001, '2016-05-30 20:00', 'Admin Supper', 500),
(100001, '2016-05-31 10:00', 'Admin Breakfast', 500),
(100001, '2016-05-31 13:00', 'Admin Dinner', 1000),
(100001, '2016-05-31 20:00', 'Admin Supper', 510);
