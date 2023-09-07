CREATE TABLE IF NOT EXISTS notification_schema.user_notification_permissions
(
    id UUID PRIMARY KEY NOT NULL,
    agree_email_notification BOOLEAN,
    agree_push_notification BOOLEAN,
    agree_sms_notification BOOLEAN
);