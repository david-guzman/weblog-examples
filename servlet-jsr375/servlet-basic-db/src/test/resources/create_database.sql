create table caller (
  name varchar(64) primary key,
  password varchar(255)
);

create table caller_groups (
  caller_name varchar(64),
  group_name varchar(64)
);

insert into caller values ('testUser', 'PBKDF2WithHmacSHA256:2048:v94Nzm8s9uAtGCPTItnjs4+Bo+PYkfbgV7lZLg9+jnM=:HNDaNO4fB6QGHDDN+/2J5Q5UrLypuMkOvPnhb25M4IE=');

insert into caller_groups values ('testUser','USER');
