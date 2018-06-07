package com.kulubema.bookdatabase;

/**
 * @author Mark Kulube (markkulube@gmail.com)
 *
 * The BookListModel stores data to display whilst the BookListApp is running.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookListModel {

    private int bookID;

    private ObservableList<Book> bookList;

    public BookListModel() {
        bookID = 1;
        bookList = FXCollections.observableArrayList();
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public ObservableList getBookList() {
        return bookList;
    }

    /**
     * Add new Book object to the (ObservableList) bookList for display in the reading list GUI.
     *
     * @param title Title of book.
     * @param author Aurthor of book.
     * @param genre Genre of book.
     * @param status "Read" status of book.
     */
    public void newBook(String title, String author, String genre, String status) {
        Book newBook = new Book( String.valueOf(bookID), title, author, genre);
        newBook.setRdStatus(status);
        AddBook(newBook);
    }

    /**
     * Delete a Book object from the reading list display GUI.
     * @param book Book object to be removed from (ObservableList) bookList.
     */
    public void RemoveBook(Book book) {
        bookList.remove(book);
    }

    /**
     * Add a Book object from the (ObservableList) bookList.
     * @param book
     */
    public void AddBook(Book book) {
        bookList.add(book);
        bookID++;
    }
}
