CREATE TABLE IF NOT EXISTS superhero_power
(
    superhero_id INTEGER REFERENCES superhero (id),
    power_id     INTEGER REFERENCES power (id)
);