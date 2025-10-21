/* ============================================================
   Technical Test – Banking Microservices
   DB: devsu_test  |  Schemas: sch_people, sch_accounts
   ============================================================ */

DO $$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_database WHERE datname = 'devsu_test'
   ) THEN
      PERFORM dblink_exec('dbname=' || current_database(),
                          'CREATE DATABASE devsu_test');
   END IF;
END$$;

-- Schemas:
CREATE SCHEMA IF NOT EXISTS sch_people AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS sch_accounts AUTHORIZATION postgres;

-- PEOPLE schema (customer-people-service)
CREATE TABLE IF NOT EXISTS sch_people.person (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(120) NOT NULL,
    gender          VARCHAR(20)  NOT NULL,
    age             INT          NOT NULL,
    identification  VARCHAR(30)  NOT NULL UNIQUE,
    address         VARCHAR(200) NOT NULL,
    phone           VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS sch_people.customer (
    id              BIGSERIAL PRIMARY KEY,
    person_id       BIGINT NOT NULL UNIQUE
                        REFERENCES sch_people.person(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    customer_code   VARCHAR(36) NOT NULL UNIQUE,
    password        VARCHAR(120) NOT NULL,
    status          BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX IF NOT EXISTS idx_person_identification
    ON sch_people.person (identification);

-- ACCOUNTS schema (account-transaction-service)
CREATE TABLE IF NOT EXISTS sch_accounts.account (
    id                 BIGSERIAL PRIMARY KEY,
    account_number     VARCHAR(20)  NOT NULL UNIQUE,
    account_type       VARCHAR(20)  NOT NULL,
    initial_balance    NUMERIC(14,2) NOT NULL,
    available_balance  NUMERIC(14,2) NOT NULL,
    status             BOOLEAN       NOT NULL DEFAULT TRUE,
    customer_code      VARCHAR(36)   NOT NULL  
);

-- Valid values constraint for account_type
ALTER TABLE sch_accounts.account
    ADD CONSTRAINT chk_account_type
    CHECK (account_type IN ('Savings','Checking'));

-- transaction
CREATE TABLE IF NOT EXISTS sch_accounts.transaction (
    id                 BIGSERIAL PRIMARY KEY,
    transaction_date   DATE         NOT NULL,
    transaction_type   VARCHAR(20)  NOT NULL,  
    amount             NUMERIC(14,2) NOT NULL,  
    balance            NUMERIC(14,2) NOT NULL,  
    account_number     VARCHAR(20)   NOT NULL
        REFERENCES sch_accounts.account(account_number)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- Valid values and positivity constraints
ALTER TABLE sch_accounts.transaction
    ADD CONSTRAINT chk_transaction_type
    CHECK (transaction_type IN ('Deposit','Withdrawal'));

ALTER TABLE sch_accounts.transaction
    ADD CONSTRAINT chk_transaction_amount_positive
    CHECK (amount >= 0);

CREATE INDEX IF NOT EXISTS idx_account_customer_code
    ON sch_accounts.account (customer_code);

CREATE INDEX IF NOT EXISTS idx_tx_account_date
    ON sch_accounts.transaction (account_number, transaction_date);

-- Inser Sample data 
INSERT INTO sch_people.person (name, gender, age, identification, address, phone) VALUES
('Jose Lema',            'M', 35, 'ID-JL-001', 'Otavalo sn y principal',   '098254785'),
('Marianela Montalvo',   'F', 32, 'ID-MM-001', 'Amazonas y NNUU',          '097548965'),
('Juan Osorio',          'M', 29, 'ID-JO-001', '13 junio y Equinoccial',   '098874587')
ON CONFLICT (identification) DO NOTHING;

INSERT INTO sch_people.customer (person_id, customer_code, password, status) VALUES
((SELECT id FROM sch_people.person WHERE identification='ID-JL-001'), 'JL-001', '1234', TRUE),
((SELECT id FROM sch_people.person WHERE identification='ID-MM-001'), 'MM-001', '5678', TRUE),
((SELECT id FROM sch_people.person WHERE identification='ID-JO-001'), 'JO-001', '1245', TRUE)
ON CONFLICT (customer_code) DO NOTHING;

INSERT INTO sch_accounts.account (account_number, account_type, initial_balance, available_balance, status, customer_code) VALUES
('478758', 'Savings',  2000, 2000, TRUE, 'JL-001'),
('225487', 'Checking',  100,  100, TRUE, 'MM-001'),
('495878', 'Savings',     0,    0, TRUE, 'JO-001'),
('496825', 'Savings',   540,  540, TRUE, 'MM-001')
ON CONFLICT (account_number) DO NOTHING;
