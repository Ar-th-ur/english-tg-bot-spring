CREATE TABLE IF NOT EXISTS users
(
    id          INT          NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY,
    telegram_id BIGINT       NOT NULL UNIQUE,
    chat_id     BIGINT       NOT NULL UNIQUE,
    state       VARCHAR(200) NOT NULL DEFAULT 'COMMAND',
    exercise_id INT
);

CREATE TABLE IF NOT EXISTS times
(
    id     INT           NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY,
    name   VARCHAR(256)  NOT NULL UNIQUE,
    theory VARCHAR(2000) NOT NULL
);

CREATE TABLE IF NOT EXISTS exercises
(
    id       INT          NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY,
    sentence VARCHAR(500) NOT NULL,
    answer   VARCHAR(100) NOT NULL
);

ALTER TABLE users
    ADD CONSTRAINT fk_exercise_id FOREIGN KEY (exercise_id) REFERENCES exercises (id)


