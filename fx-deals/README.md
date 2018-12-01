# FX Deals

## Database Setup
* mysql> create database fxdeal_db;
* mysql> create user 'fx_user'@'localhost' identified by 'fx_pwd';
* mysql> grant all on fxdeal_db.* to 'fx_user'@'localhost';

## Resources
* [Entity Detached](https://techsparx.com/software-development/spring/detached-entity-persist.html)
* [Hibernate C3p0](https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#database-connectionprovider-c3p0)