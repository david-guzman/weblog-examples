create table caller (
  name varchar(64) primary key,
  password varchar(255)
);

create table caller_groups (
  caller_name varchar(64),
  group_name varchar(64)
);

insert into caller values ('testUser', '2AFDA027CD5419CA06C339CE79CEFB777AA06B4A74362058A60E4E3596FAFF3DAF9C7DC4A312025CFC69658D72DE22A2D8FF53AC7A66634111813582464482AF');

insert into caller_groups values ('testUser','USER');
