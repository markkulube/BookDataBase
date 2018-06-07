package com.kulubema.bookdatabase;

/**
 * @author Mark Kulube (markkulube@gmail.com)
 *
 * An SQLModel object connects the database to the BookListApp.
 */

import java.sql.*;

public class SQLModel {

    private Connection connection;

    public SQLModel() throws SQLException {
        connection = DriverManager.getConnection("" +
                "jdbc:mysql://localhost:3306/my_reading_list?autoReconnect=true&useSSL=false", "USERNAME", "PASSWORD");
        createTable();
        createConnection();
    }

    /**
     * This method creates the table for the reading list in the database.
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        String q = "CREATE TABLE IF NOT EXISTS READING_LIST(" +
                "Title varchar(100)," +
                "Author varchar(100)," +
                "Genre varchar(100)," +
                "Status varchar(100)" +
                ");";
        Statement stmt = connection.createStatement();
        stmt.execute(q);
        stmt.close();
    }

    public void createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM READING_LIST");

            stmt.close();
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
