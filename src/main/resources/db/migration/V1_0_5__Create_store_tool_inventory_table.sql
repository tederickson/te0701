CREATE TABLE store_tool_inventory (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    store_id        SMALLINT    NOT NULL,
    tool_code       varchar(4)  NOT NULL,
    max_available   SMALLINT    NOT NULL,

    INDEX store_tool_inventory_idx1 (store_id, tool_code)
);
