# Spring Data Batch

## Resources
* [SimpleJpaRepository](https://github.com/spring-projects/spring-data-jpa/blob/master/src/main/java/org/springframework/data/jpa/repository/support/SimpleJpaRepository.java#L366)
* [Custom Repository Implementation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations)
* [JPA Batch Inserts](https://frightanic.com/software-development/jpa-batch-inserts/)
* [MySQL General Log](https://dev.mysql.com/doc/refman/8.0/en/query-log.html)


## Technology Stack
* Spring JPA
* Hibernate
* C3p0
* MySQL


## Time Taken
* JPA Repository with flush() - 37359ms
* Custom Repository using entity manager - 17575ms


## Verify Batch Query Execution in MySQL
connect mysql and execute following queries;

* SET GLOBAL general_log = 'ON';
* SET general_log_file = 'D:\Installed\mysql\data\AshimKhadka.log'
* SET log_output = 'FILE' OR SET log_output = 'TABLE'
* show variables;
* SET GLOBAL general_log = 'OFF';


## Point to note
* use auto increment in entity
	``` java
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "increment")
	```
* hibernate batch size
	``` java
	hibernate.jdbc.batch_size=50
	```