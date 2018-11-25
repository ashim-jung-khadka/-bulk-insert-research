# Batch Processing

## Repository
- Default
- Flushing
- JdbcTemplate
- Jdbi
- StatelessSession

## Technology Stack
- JPA
- Hibernate
- C3p0
- Jdbi
- JDBC
- MySQL

## Exeuction time
- Data Size : 500,000
	``` properties
	Batch Size : 300
	DefaultBulkOperations - Done in 21 sec
	FlushingBulkOperations - Done in 19 sec
	JdbcTemplateBulkOperations - Done in 18 sec
	JdbiBulkOperations - Done in 16 sec
	StatelessSessionBulkOperations - Done in 15 sec
	```

	``` properties
	Batch Size : 500
	DefaultBulkOperations - Done in 20 sec
	FlushingBulkOperations - Done in 18 sec
	JdbcTemplateBulkOperations - Done in 12 sec
	JdbiBulkOperations - Done in 14 sec
	StatelessSessionBulkOperations - Done in 13 sec
	```

- Data Size : 100,000
	``` properties
	Batch Size : 500
	DefaultBulkOperations - Done in 5 sec
	FlushingBulkOperations - Done in 4 sec
	JdbcTemplateBulkOperations - Done in 4 sec
	JdbiBulkOperations - Done in 2 sec
	StatelessSessionBulkOperations - Done in 2 sec
	```

## Resources
- [Hibernate Batch](https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#batch)
- [Hibernate Stateless Session](https://gist.github.com/jelies/5181262)
- [MySql LOAD_DATA_LOCAL](https://dev.mysql.com/doc/refman/5.7/en/load-data.html)
