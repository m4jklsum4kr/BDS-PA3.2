package org.but.feec.javafx.api;


import java.sql.Date;

public class LibCreateView {
    private Long isbn;
    private String title;
    private String author_name;
    private String genre;
    private String publisher;

    private Long publisher_id;

    public Long getIsbn() {return isbn; }

    public void setIsbn(Long isbn) {this.isbn = isbn; }


    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}


    public String getAuthor_name() {return author_name;}

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {this.genre = genre;}


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getPublisher_id(){return publisher_id;}
    public void setPublisher_id(Long publisher_id) {this.publisher_id = publisher_id;}


    @Override
    public String toString() {
        return "PersonCreateView{" +
                "isbn='" + isbn + '\'' +
                ", bookTitle='" + title + '\'' +
                ", publisherID='" + publisher_id + '\'' +
                ", publisher ='" + publisher + '\'' +
                ", author_name='" + author_name + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
