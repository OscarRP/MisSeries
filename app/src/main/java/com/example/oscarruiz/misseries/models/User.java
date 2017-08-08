package com.example.oscarruiz.misseries.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Ruiz on 29/06/2017.
 */

public class User {

    /**
     * var that indicates users nick
     */
    private String nick;

    /**
     * var that indicates users email
     */
    private String email;

    /**
     * var that indicates users password
     */
    private String password;

    /**
     * var that inidcates series user is seen
     */
    private ArrayList<Serie> series;


    public User(String nick, String email, String password) {
        this.nick = nick;
        this.email = email;
        this.password = password;
    }

    public User(String email, String nick) {
        this.email = email;
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Serie> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<Serie> series) {
        this.series = series;
    }
}
