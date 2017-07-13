package com.example.oscarruiz.misseries.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.dialogs.Dialogs;
import com.example.oscarruiz.misseries.models.User;
import com.example.oscarruiz.misseries.session.Session;
import com.example.oscarruiz.misseries.utils.Animations;
import com.example.oscarruiz.misseries.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    /**
     * Logo image view
     */
    private ImageView logo;

    /**
     * Posters layout
     */
    private LinearLayout postersLayout;

    /**
     * Animations
     */
    private Animations animations;

    /**
     * Image view breaking bad poster
     */
    private ImageView breakingBad;

    /**
     * Image view dexter poster
     */
    private ImageView dexter;

    /**
     * Image view game of trhones poster
     */
    private ImageView got;

    /**
     * Image view walking dead poster
     */
    private ImageView walking;

    /**
     * Firebase Auth instance
     */
    private FirebaseAuth firebaseAuth;

    /**
     * Firebase DataBase
     */
    private FirebaseDatabase database;

    /**
     * User instance
     */
    private User user;

    /**
     * Handler to controll time in splash activity
     */
    private Handler handler;

    /**
     * Controls time in splash activity
     */
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Hide Action Bar
        getSupportActionBar().hide();

        //Check internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        Dialogs dialogs = new Dialogs(SplashActivity.this);
        if (netInfo == null || !netInfo.isConnected()) {
            AlertDialog dialog = dialogs.showConnectionErrorDialog(SplashActivity.this);
            dialog.show();
        } else {
            //Firebase Auth instance
            firebaseAuth = FirebaseAuth.getInstance();
            //firebase database instance
            database = FirebaseDatabase.getInstance();

            getViews();

            startAnim();

            startHandler();
        }
    }

    /**
     * Method to get views
     */
    private void getViews() {
        breakingBad = (ImageView)findViewById(R.id.breaking_bad);
        dexter = (ImageView)findViewById(R.id.dexter);
        got = (ImageView)findViewById(R.id.game_of_trhones);
        walking = (ImageView)findViewById(R.id.walking_dead);
        postersLayout = (LinearLayout)findViewById(R.id.posters_layout);
        logo = (ImageView)findViewById(R.id.logo);
    }

    private void startAnim() {
        animations = new Animations();
        animations.splashAnimations(postersLayout, logo, dexter, breakingBad, got, walking, SplashActivity.this);
    }

    private void startHandler() {

        //time init
        time = 0;

        //Handler creation
        handler = new Handler();

        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                if (time >= Constants.SPLASH_TIME) {
                    //remove callbacks
                    handler.removeCallbacks(this);

                    //check user is logged
                    //retrieve info from sharedPreferences
                    SharedPreferences sharedPreferences = SplashActivity.this.getSharedPreferences(Constants.USER_LOGGED, Context.MODE_PRIVATE);
                    boolean userLoged = sharedPreferences.getBoolean(Constants.USER_LOGGED, false);
                    String uid = sharedPreferences.getString(Constants.UID, "");

                    if (userLoged) {
                        //save user in session
                        //load user from database
                        uid = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference ref = database.getReference().child("users").child(uid);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                //convert DataSnapshot to Hashmap
                                HashMap<String, JSONObject> jsonSnapshot = (HashMap<String, JSONObject>) dataSnapshot.getValue();

                                //convert Hashmap to Json String
                                String jsonString = new Gson().toJson(jsonSnapshot);

                                //convert jsonString to user
                                Gson gson = new Gson();
                                user = gson.fromJson(jsonString, User.class);

                                //save user in session
                                Session session = Session.getInstance();
                                session.setUser(user);

                                //go to home activity
                                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                //finish activity
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    } else {
                        //go to register activity
                        startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                        //finish activity
                        finish();
                    }

                } else {
                    //increments time
                    time = time + Constants.INCREMENTAL_TIME;
                    handler.postDelayed(this, Constants.INCREMENTAL_TIME);
                }
            }
        }, Constants.INCREMENTAL_TIME);
    }
}
