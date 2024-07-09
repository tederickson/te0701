CREATE TABLE store_tool_type_charge (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,

    store_id        SMALLINT     NOT NULL,
    tool_type       varchar(16)  NOT NULL,
    daily_charge    DECIMAL(4,2)  NOT NULL,

    INDEX store_tool_type_charge_idx1 (store_id, tool_type)
);
