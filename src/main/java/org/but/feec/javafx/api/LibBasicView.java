package org.but.feec.javafx.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LibBasicView {
    private final LongProperty id = new SimpleLongProperty();
    private final LongProperty isbn = new SimpleLongProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty author_name = new SimpleStringProperty();
    private final StringProperty genre = new SimpleStringProperty();
    private final StringProperty publisher = new SimpleStringProperty();

    public Long getId() {
        return idProperty().get();
    }

    public void setId(Long id) {
        this.idProperty().setValue(id);
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
        this.author_nameProperty().set(author_name);
    }

    public String getGenre() {
        return genreProperty().get();
    }

    public void setGenre(String genre) {
        this.genreProperty().setValue(genre);
    }

    public String getPublisher() {
        return publisherProperty().get();
    }

    public void setPublisher(String publisher) {
        this.publisherProperty().set(publisher);
    }

    public LongProperty idProperty() {
        return id;
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

    public long getIsbn() {
        return isbn.get();
    }

    public LongProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn.set(isbn);
    }
}
