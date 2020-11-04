DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       bigserial primary key,
    username varchar(255) unique,
    password varchar(255)
);

create table user_role
(
    user_id bigint,
    roles   varchar(255)
);
alter table user_role add constraint fk_user_role_user foreign key (user_id) references users (Id);

INSERT INTO users (id, username, password)
VALUES (1, 'admin', 'a');
INSERT INTO users (id, username, password)
VALUES (2, 'user1', 'user1');
INSERT INTO users (id, username, password)
VALUES (3, 'user2', 'user2');

INSERT INTO user_role (user_id, roles)
VALUES (1, 'ADMIN');
INSERT INTO user_role (user_id, roles)
VALUES (2, 'USER');
INSERT INTO user_role (user_id, roles)
VALUES (3, 'BANNED');

create table statistic
(
    id       bigserial primary key,
    pic_filename varchar(255),
    digit int,
    accuracy numeric,
    date date default now()
);
INSERT INTO statistic (pic_filename, digit, accuracy)
VALUES ('2.png', 2, 0.99);