CREATE TABLE IF NOT EXISTS fx_rate (
    id            SERIAL PRIMARY KEY,
    buy_currency  VARCHAR(3) NOT NULL,
    sell_currency VARCHAR(3) NOT NULL,
    rate          DECIMAL(10, 4) NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);