package com.kulubema.bookdatabase;

/**
 * @author Mark Kulube (markkulube@gmail.com)
 *
 * A Book has a title, aurthor, genre and "read" status.
 */

import javafx.beans.property.SimpleStringProperty;

public class Book {

    // This variable is for indexing in the data and is hidden from the user.
    private SimpleStringProperty bookID;

    private SimpleStringProperty title;

    private SimpleStringProperty author;

    private SimpleStringProperty genre;

    private SimpleStringProperty rdStatus;

    public Book(String newBookID, String newTitle, String newAuthor, String newGenre) {
        bookID = new SimpleStringProperty(newBookID);
        title = new SimpleStringProperty(newTitle);
        author = new SimpleStringProperty(newAuthor);
        genre = new SimpleStringProperty(newGenre);
        rdStatus = new SimpleStringProperty("Not Read");
    }

    public String getBookID() {
        return bookID.get();
    }

    public void setBookID(String bookID) {
        this.bookID.set(bookID);
    }

    public String getGenre() {
        return genre.get();
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getRdStatus() {
        return rdStatus.get();
    }

    public SimpleStringProperty rdStatusProperty() {
        return rdStatus;
    }

    public void setRdStatus(String rdStatus) {
        this.rdStatus.set(rdStatus);
    }

}
