drop table if exists deals;
drop table if exists deal_count;
drop table if exists invalid_deals;

create table deals (
  id bigint not null,
  amount double precision,
  deal_id varchar(255),
  deal_time datetime,
  file_name varchar(255),
  from_currency varchar(255),
  to_currency varchar(255),
  primary key (id)
);

create table deal_count
  (id bigint not null,
  count_of_deals integer not null,
  currency_code varchar(255),
  primary key (id)
);

create table invalid_deals(
  deal_id varchar(30),
  from_currency varchar(10),
  to_currency varchar(10),
  deal_time varchar(30),
  amount varchar(20),
  file_name varchar(50)
);


-- drop table if exists hibernate_sequence;
-- create table hibernate_sequence (next_val bigint) engine=myisam
-- insert into hibernate_sequence values ( 1 )
-- insert into hibernate_sequence values ( 1 )