package com.puns.punsgame;

/**
 * Created by Maciej Szalek on 2018-05-14.
 */

public class Pun {

    Integer id;
    String category;
    String password;

    Pun(){}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getPassword() {
        return password;
    }
}
