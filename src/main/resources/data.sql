DROP TABLE IF EXISTS account;

CREATE TABLE account (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  credit_card_number VARCHAR(16) NOT NULL,
  amount DECIMAL DEFAULT NULL
);

INSERT INTO account (credit_card_number, amount) VALUES
  ('1234567890123456', 10000.0);