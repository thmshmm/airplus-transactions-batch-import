DROP TABLE IF EXISTS creditcard_transactions;

CREATE TABLE creditcard_transactions  (
  id SERIAL NOT NULL PRIMARY KEY,
  card_no VARCHAR(20) NOT NULL,
  invoice_no VARCHAR(20),
  invoice_date DATE,
  invoice_item_no INTEGER,
  purchase_date DATE ,
  entry_date DATE,
  service_provider VARCHAR(50),
  service_description VARCHAR(50),
  currency VARCHAR(3),
  amount NUMERIC
);