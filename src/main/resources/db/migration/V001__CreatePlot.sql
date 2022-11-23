CREATE TABLE plots (
    id VARCHAR(40) PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    cultivated_area DOUBLE NOT NULL,
    crop VARCHAR(40),
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP
)