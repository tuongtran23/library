DROP TABLE IF EXISTS book;

CREATE TABLE book (
    id   BIGSERIAL NOT NULL UNIQUE PRIMARY KEY,
   name  VARCHAR(255) NOT NULL UNIQUE
);
