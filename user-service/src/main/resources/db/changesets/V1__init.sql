create table if not exists user_schema.users_table
(
    id           UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    email        varchar(255) not null unique,
    login        varchar(255) not null unique,
    number_phone varchar(255) unique,
    password     varchar(255) not null,
    created      timestamp(6),
    updated      timestamp(6)
);
