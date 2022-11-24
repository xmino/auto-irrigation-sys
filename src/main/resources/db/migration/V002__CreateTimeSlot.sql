CREATE TABLE time_slot (
    id VARCHAR(40) PRIMARY KEY,
    init TIMESTAMP NOT NULL,
    duration_minutes INTEGER NOT NULL,
    amount_water INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP,
    plot_id VARCHAR(40) NOT NULL,
    CONSTRAINT fk_slot_plot FOREIGN KEY (plot_id) REFERENCES plots (id)
)