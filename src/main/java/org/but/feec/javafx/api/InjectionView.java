package org.but.feec.javafx.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InjectionView {
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty first_name = new SimpleStringProperty();
    private final StringProperty last_name = new SimpleStringProperty();


    public void setId(long id) {this.id.set(id);}
    public void setUsername(String username) {this.username.set(username);}
    public void setFirst_name(String first_name) {this.first_name.set(first_name);}
    public void setLast_name(String surname) {this.last_name.set(surname);}


    public long getId() {return idProperty().get();}
    public String getUsername() {
        return usernameProperty().get();
    }
    public String getFirst_name() {
        return first_nameProperty().get();
    }
    public String getLast_name() {
        return last_nameProperty().get();
    }


    public LongProperty idProperty() {
        return id;
    }
    public StringProperty usernameProperty() {
        return username;
    }
    public StringProperty first_nameProperty() {
        return first_name;
    }
    public StringProperty last_nameProperty() {
        return last_name;
    }
}