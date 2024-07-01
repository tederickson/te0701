CREATE TABLE customer (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    first_name  varchar(100) NOT NULL,
    last_name   varchar(100) NOT NULL,
    phone       varchar(10)  NOT NULL,
    password    varchar(255) NOT NULL,
    email       varchar(100) NULL,
    status      varchar(10)  NOT NULL,
    create_date date         NOT NULL,

    KEY user_idx1 (phone)
);
