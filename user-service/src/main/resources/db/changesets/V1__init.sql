create table if not exists user_schema.users_table
(
    created      timestamp(6),
    updated      timestamp(6),
    id           uuid not null primary key,
    email        varchar(255) not null unique,
    login        varchar(255) not null unique,
    number_phone varchar(255) unique,
    password     varchar(255) not null
);
