# BookDataBase
A basic reading list GUI app that connects to an (my)SQL server.

Requirement:
JDK8

Additional Requirements:
Installed MySQL:
https://dev.mysql.com/downloads/

Installed JDBC i.e MySQL Connector:
https://www.mysql.com/products/connector/

USERNAME & PASSWORD
In the constructor for of sqlModel.java, add the username and password to your MySQL server to the getConnection method.

public SQLModel() throws SQLException {
        connection = DriverManager.getConnection("" +
                "jdbc:mysql://localhost:3306/my_reading_list?autoReconnect=true&useSSL=false", "TYPE USERNAME", "TYPE PASSWORD");
        createTable();
        createConnection();
    }

