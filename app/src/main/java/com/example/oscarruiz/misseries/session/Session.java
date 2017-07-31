package com.example.oscarruiz.misseries.session;

import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.models.User;

/**
 * Created by Oscar Ruiz on 29/06/2017.
 */

public class Session {

    /**
     * User session singleton
     */
    private static Session session;

    /**
     * User session
     */
    private User user;

    /**
     * Selected serie
     */
    private ResponseSerie serie;

    /**
     * Method to create singleton
     * @return
     */
    public static Session getInstance() {
        //check if session is created
        if (session == null) {
            //create session
            session = new Session();
        }
        //return sessions
        return session;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResponseSerie getSerie() {
        return serie;
    }

    public void setSerie(ResponseSerie serie) {
        this.serie = serie;
    }
}
