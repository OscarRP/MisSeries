package com.example.oscarruiz.misseries.controllers;

import com.example.oscarruiz.misseries.models.User;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Oscar Ruiz on 05/07/2017.
 */

public class DataController {

    /**
     * Firebase Database instance
     */
    private FirebaseDatabase database;

    /**
     * Firebase auth instance
     */
    private FirebaseAuth firebaseAuth;

    /**
     * Method to save user in firebase
     */
    public void saveUser(User user) {

        //firebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();
        //firebase database instance
        database = FirebaseDatabase.getInstance();

        //Save user in database
        String uid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference ref = database.getReference().child("users").child(uid);
        ref.setValue(user);
    }
}
