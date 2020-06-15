package com.kulubema.bookdatabase;

import java.io.FileReader;
/**
 * @author Mark Kulube (markkulube@gmail.com)
 *
 * An SQLModel object connects the database to the BookListApp.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SQLModel {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    // JDBC driver name, database URL and port
    private String jdbcDriver;
    private String databaseURL;
    private long port;

    //  Database credentials
    private String username;
    private String password;

    public SQLModel() throws SQLException {
    	initDatabaseCredentials();
        createDatabase();
//        connection = DriverManager.getConnection("" +
//                "jdbc:mysql://localhost:3306/book_list?autoReconnect=true&useSSL=false", username, password);
        //connection = DriverManager.getConnection("jdbc:mysql://localhost:"  + port + "/book_list?autoReconnect=true&useSSL=false", username, password);
        connection = DriverManager.getConnection(databaseURL.substring(0, databaseURL.length() - 1)  + ":" + port + "/book_list?autoReconnect=true&useSSL=false", username, password);
        createTable();
        createConnection();
    }
    
    
    /**
     * Initialize username, password, port, jdbcDriver, and databaseURL from src/config.json
     */
    public void initDatabaseCredentials() {
    	
        JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("config.json"));
 
			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
			JSONObject jsonObject = (JSONObject) obj;
			
			this.username = (String) jsonObject.get("MYSQL_USER");
			this.password = (String) jsonObject.get("MYSQL_PASS");
			this.port = (long) jsonObject.get("MYSQL_PORT");
			this.jdbcDriver = (String) jsonObject.get("MYSQL_DRIVER");
			this.databaseURL = (String) jsonObject.get("MYSQL_URL");

	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * This method creates the book_list database in the MySQL server..
     */

    public void createDatabase() {
        connection = null;
        statement = null;

        try {
            // Register JDBC driver
            Class.forName(jdbcDriver);

            // Open Connection
            connection = DriverManager.getConnection(databaseURL, username, password);

            statement = connection.createStatement();

            String sql = "CREATE DATABASE IF NOT EXISTS BOOK_LIST;";

            statement.execute(sql);
            statement.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method creates the reading_list table in the database.
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        String q = "CREATE TABLE IF NOT EXISTS READING_LIST(" +
                "BookID int(11)," +
                "Title varchar(100)," +
                "Author varchar(100)," +
                "Genre varchar(100)," +
                "Status varchar(100)" +
                ");";
        statement = connection.createStatement();
        statement.execute(q);
        statement.close();
    }

    public void createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM READING_LIST");

            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Insert data of new book added to the list in to the MYSQL server database
     *
     * @param bookID Integer - Index number in database.
     * @param title String - Title of book.
     * @param author String - Author of book.
     * @param genre String - Genre of book.
     * @param status String -  "Read" status of book.
     * @throws SQLException
     */
    public void SqlNewBook(Integer bookID, String title, String author, String genre, String status) throws SQLException {
        String sqlScript = "INSERT INTO READING_LIST VALUES(?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlScript);

        preparedStatement.setInt(1, bookID);
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, author);
        preparedStatement.setString(4, genre);
        preparedStatement.setString(5, status);

        preparedStatement.execute();
        preparedStatement.close();
    }

    /**
     * Update the database with new "read status" of a book.
     *
     * @param bookID Integer - Index number in database.
     * @param status String - "Read" status of book.
     * @throws SQLException
     */
    public void SqlChangeStatus(Integer bookID, String status) throws SQLException {
        Statement preparedStatement = connection.createStatement();
        String sqlScript = "UPDATE READING_LIST " +
                "SET Status = " + "'" + status + "'" + " WHERE BookID = " + bookID +";";
        preparedStatement.execute(sqlScript);
    }

    /**
     * Delete the record of a book from the database.
     * @param bookID Integer - Index number in database.
     * @throws SQLException
     */
    public void SqlRemoveBook(Integer bookID) throws SQLException {
        Statement preparedStatement = connection.createStatement();
        String sqlScript = "DELETE FROM READING_LIST " +
                "WHERE BookID = " + bookID + ";";
        preparedStatement.execute(sqlScript);
    }

    public Connection getConnection() {
        return connection;
    }
}
