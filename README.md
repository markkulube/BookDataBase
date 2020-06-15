# BookDataBase
A basic reading list desktop GUI app that connects to, and stores data in a MySQL server.

## Requirements:
- JDK8

- MySQL server:<br />
https://dev.mysql.com/downloads/<br />
<br />
- JDBC i.e MySQL Connector:<br />
https://www.mysql.com/products/connector/<br />
<br />
### Configurations
In src/config.json specify the username and password to the MySQL server.<br />
- e.g:<br />
	{
		
		"MYSQL_USER": "root",
		"MYSQL_PASS": "diploid46",
		"MYSQL_DRIVER": "com.mysql.jdbc.Driver",
		"MYSQL_URL": "jdbc:mysql://localhost/",
		"MYSQL_PORT": 3306
	}