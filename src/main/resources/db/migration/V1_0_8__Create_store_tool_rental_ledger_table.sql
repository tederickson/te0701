CREATE TABLE store_tool_rental_ledger
  (
     id                  BIGINT NOT NULL auto_increment PRIMARY KEY,

     store_id            SMALLINT NOT NULL,
     checkout_date       DATE NOT NULL,
     tool_code           VARCHAR(4) NOT NULL,
     customer_id         BIGINT NULL,
     rental_day_count    SMALLINT NOT NULL,
     due_date            DATE NOT NULL,
     daily_rental_charge DECIMAL(9, 2) NOT NULL,
     charge_days         SMALLINT NOT NULL,
     pre_discount_charge DECIMAL(9, 2) NOT NULL,
     discount_percent    SMALLINT NOT NULL,
     discount_amount     DECIMAL(9, 2) NOT NULL,
     final_charge        DECIMAL(9, 2) NOT NULL,

     INDEX store_tool_inventory_idx1 (store_id, checkout_date)
  );