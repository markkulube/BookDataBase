package com.kulubema.bookdatabase;

/**
 * @author Mark Kulube (markkulube@gmail.com)
 *
 * Functions as a typical controller mediating data between the GUI and the model objects.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class BookListController implements Initializable {

    @FXML
    private TableView bookListTable;

    @FXML
    private TableColumn title, author, genre, status, bookID;

    @FXML
    private TextField titleNew, authorNew, genreNew;

    @FXML
    private Button addNewBook, read, notRead;

    private BookListModel bookListModel;

    private SQLModel sqlModel;

    public BookListController() throws SQLException {
        bookListModel = new BookListModel();
        sqlModel = new SQLModel();

    }

    /**
     * Each time the program is run, the reading list is initialized with all records
     * saved in the database.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookID.setVisible(false);
        ResultSet resultSet = null;
        try {
            Statement statement = sqlModel.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM READING_LIST");

            while(resultSet.next()) {
                String bookID = resultSet.getString("BookID");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                String genre = resultSet.getString("Genre");
                String status = resultSet.getString("Status");

                Integer tempBookID = Integer.parseInt(bookID);

                if(tempBookID > bookListModel.getBookID()) {
                    bookListModel.setBookID(tempBookID);
                }

                Book book = new Book(bookID, title, author, genre);
                book.setRdStatus(status);

                bookListModel.AddBook(book);

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        bookID.setCellValueFactory(new PropertyValueFactory<Book, String>("bookID"));
        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        genre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        status.setCellValueFactory(new PropertyValueFactory<Book, String>("rdStatus"));

        bookListTable.setItems(bookListModel.getBookList());
        bookListTable.refresh();


    }

    /**
     * Add a new book to the reading list and propagate the changes to the database
     * via the sqlModel object.
     *
     * @param event
     * @throws SQLException
     */
    public void NewBook(ActionEvent event) throws SQLException {


        Integer bookID = bookListModel.getBookID();
        String title = titleNew.getText();
        String author = authorNew.getText();
        String genre = genreNew.getText();
        String status = "Not Read";

        if(!title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {

            bookListModel.newBook(title, author, genre, status);
            sqlModel.SqlNewBook(bookID, title, author, genre, status);

            titleNew.clear(); authorNew.clear(); genreNew.clear();
            bookListTable.refresh();
        }
    }

    /**
     * Delete a record from the reading list and propagate the deletion to the database
     * via the sqlModel object.
     *
     * @param event
     * @throws SQLException
     */
    public void RemoveBook(ActionEvent event) throws SQLException {
        if (!bookListTable.getSelectionModel().getSelectedCells().isEmpty()) {

            Book book = (Book) bookListTable.getSelectionModel().getSelectedItem();

            bookListModel.RemoveBook(book);
            sqlModel.SqlRemoveBook(Integer.parseInt(book.getBookID()));

            bookListTable.getItems().remove(book);
            bookListTable.refresh();
        }

        bookListTable.getSelectionModel().clearSelection();
        bookListTable.refresh();


    }

    /**
     * Update the "read status" of a book and propagate the update to the database
     * via the sqlModel object.
     * @param event
     * @throws SQLException
     */
    public void ChangeStatus(ActionEvent event) throws SQLException {
        if(!bookListTable.getSelectionModel().getSelectedCells().isEmpty()) {

            Book book = (Book) bookListTable.getSelectionModel().getSelectedItem();

            if(((Button)(event.getSource())).equals(read)) {

                book.setRdStatus("Read");
                sqlModel.SqlChangeStatus(Integer.parseInt(book.getBookID()), "Read");

            } else {

                book.setRdStatus("Not Read");
                sqlModel.SqlChangeStatus(Integer.parseInt(book.getBookID()), "Not Read");

            }
            bookListTable.refresh();
        }
    }
}

