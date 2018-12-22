drop table if exists deal_count;
drop table if exists invalid_deal;
drop table if exists valid_deal;

CREATE TABLE deal_count (
	id BIGINT NOT NULL
	,count_of_deals INTEGER NOT NULL
	,from_currency_code VARCHAR(255)
	,to_currency_code VARCHAR(255)
	,PRIMARY KEY (id)
	) engine = InnoDB;

CREATE TABLE invalid_deal (
	id BIGINT NOT NULL
	,amount VARCHAR(255)
	,deal_id VARCHAR(255)
	,deal_time VARCHAR(255)
	,file_name VARCHAR(255)
	,from_currency VARCHAR(255)
	,to_currency VARCHAR(255)
	,PRIMARY KEY (id)
	) engine = InnoDB;

CREATE TABLE valid_deal (
	id BIGINT NOT NULL
	,amount DOUBLE PRECISION
	,deal_id VARCHAR(255)
	,deal_time DATETIME
	,file_name VARCHAR(255)
	,from_currency VARCHAR(255)
	,to_currency VARCHAR(255)
	,PRIMARY KEY (id)
	) engine = InnoDB;
