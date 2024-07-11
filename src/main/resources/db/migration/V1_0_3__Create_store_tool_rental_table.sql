CREATE TABLE store_tool_rental (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    store_id        SMALLINT    NOT NULL,
    tool_code       varchar(4)  NOT NULL,
    checkout_date   date        NOT NULL,
    amount          SMALLINT    NOT NULL,
    customer_id     bigint      NULL,

    INDEX store_tool_rental_idx1 (store_id, tool_code, checkout_date)
);
