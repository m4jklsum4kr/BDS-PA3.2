package org.but.feec.javafx.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LibDetailView {
    private LongProperty id = new SimpleLongProperty();
    private LongProperty isbn = new SimpleLongProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty author_name = new SimpleStringProperty();
    private StringProperty genre = new SimpleStringProperty();
    private StringProperty publisher = new SimpleStringProperty();
    private StringProperty publication_date = new SimpleStringProperty();

    public Long getId() {
        return idProperty().get();
    }

    public void setId(Long id) {
        this.idProperty().setValue(id);
    }

    public Long getIsbn() {
        return isbnProperty().get();
    }

    public void setIsbn(Long isbn) {
        this.isbnProperty().setValue(isbn);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public void setTitle(String title) {
        this.titleProperty().setValue(title);
    }

    public String getAuthor_name() {
        return author_nameProperty().get();
    }

    public void setAuthor_name(String author_name) {
        this.author_nameProperty().setValue(author_name);
    }

    public String getGenre() {
        return genreProperty().get();
    }

    public void setGenre(String genre) {
        this.genreProperty().set(genre);
    }

    public String getPublisher() {
        return publisherProperty().get();
    }

    public void setPublisher(String publisher) {
        this.publisherProperty().setValue(publisher);
    }

    public String getPublication_date() {
        return publication_dateProperty().get();
    }

    public void setPublication_date(String publication_date) {
        this.publication_dateProperty().setValue(publication_date);
    }

    public LongProperty idProperty() {
        return id;
    }

    public LongProperty isbnProperty() {
        return isbn;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty author_nameProperty() {
        return author_name;
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public StringProperty publisherProperty() {
        return publisher;
    }


    public StringProperty publication_dateProperty() {
        return publication_date;
    }


}
