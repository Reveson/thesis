CREATE KEYSPACE thesis WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor' : 3};
USE thesis;

CREATE TABLE feed_author
(
    author_id bigint,
    timestamp timeuuid,
    content   text,
    photo     text,
    PRIMARY KEY ((author_id), timestamp)
) WITH CLUSTERING ORDER BY (timestamp desc);

CREATE TABLE feed_subscriber
(
    subscriber_id bigint,
    timestamp     timeuuid,
    author_id     bigint,
    author_name   text,
    content       text,
    photo         text,
    PRIMARY KEY ((subscriber_id), timestamp)
) WITH CLUSTERING ORDER BY (timestamp desc);

CREATE TABLE comment
(
    post_id     timeuuid,
    timestamp   timeuuid,
    author_id   bigint,
    author_name text,
    content     text,
    PRIMARY KEY ((post_id), timestamp)
) WITH CLUSTERING ORDER BY (timestamp desc);

CREATE TABLE reaction
(
    post_id     timeuuid,
    timestamp   timeuuid,
    author_id   bigint,
    author_name text,
    PRIMARY KEY ((post_id), timestamp)
) WITH CLUSTERING ORDER BY (timestamp desc);

CREATE TABLE notification
(
    user_id   bigint,
    timestamp timeuuid,
    content   text,
    is_read   boolean,
    PRIMARY KEY ((user_id), timestamp)
) WITH CLUSTERING ORDER BY (timestamp desc);

CREATE TABLE message
(
    channel_id   bigint,
    timestamp timeuuid,
    author_id   bigint,
    content   text,
    PRIMARY KEY ((channel_id), timestamp)
) WITH CLUSTERING ORDER BY (timestamp desc);

CREATE TABLE channel
(
    user_id   bigint,
    channel_id bigint,
    channel_name   text,
    PRIMARY KEY (user_id)
);