package com.puns.punsgame;

/**
 * Created by Maciej Szalek on 2018-05-14.
 */

public class Pun {

    private Integer id;
    private String category;
    private String password;
    private Integer game_time;

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

    public void setGameTime(Integer game_time){
        this.game_time = game_time;
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

    public Integer getGameTime(){
        return game_time;
    }
}
