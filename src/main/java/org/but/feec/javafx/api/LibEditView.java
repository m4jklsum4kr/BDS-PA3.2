package org.but.feec.javafx.api;

public class LibEditView {

    private Long id;
    private Long isbn;
    private String title;
    private String author_name;
    private String publisher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "PersonEditView{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author_name='" + author_name + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
