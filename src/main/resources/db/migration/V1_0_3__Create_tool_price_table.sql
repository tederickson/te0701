CREATE TABLE tool_price (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    store_id    SMALLINT      NOT NULL,
    tool_code   varchar(4)    NOT NULL,
    price       DECIMAL(4,2)  NOT NULL,
    start_date  date NOT NULL,
    end_date    date NOT NULL,

    INDEX tool_price_idx1 (store_id, tool_code)
);
