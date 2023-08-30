CREATE TABLE IF NOT EXISTS chat_schema.chat
(
    id             uuid not null primary key,
    user_id_first  uuid not null,
    user_id_second uuid not null
);
