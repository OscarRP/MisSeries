package com.example.oscarruiz.misseries.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.models.User;
import com.example.oscarruiz.misseries.session.Session;
import com.example.oscarruiz.misseries.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    /**
     * Loading layout
     */
    private RelativeLayout loading;

    /**
     * User Uid
     */
    private String uid;

    /**
     * Login button
     */
    private Button loginButton;

    /**
     * Not registered button
     */
    private Button notRegisteredButton;

    /**
     * Email edit text
     */
    private EditText emailET;

    /**
     * password edit text
     */
    private EditText passwordET;

    /**
     * Firebase Auth instance
     */
    private FirebaseAuth firebaseAuth;

    /**
     * Firebase Listener
     */
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    /**
     * Firebase DataBase
     */
    private FirebaseDatabase database;

    /**
     * User instance
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hide Action Bar
        getSupportActionBar().hide();

        //Firebase Auth instance
        firebaseAuth = FirebaseAuth.getInstance();
        //firebase database instance
        database = FirebaseDatabase.getInstance();

        getViews();
        setListeners();
    }

    /**
     * Method to get views
     */
    private void getViews() {
        passwordET = (EditText)findViewById(R.id.password);
        emailET = (EditText)findViewById(R.id.email);
        loginButton = (Button)findViewById(R.id.login_button);
        notRegisteredButton = (Button)findViewById(R.id.not_registered_button);
        loading = (RelativeLayout)findViewById(R.id.loading_layout);
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields()) {
                    //Show loading layout
                    loading.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
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
                                        Session.getInstance().setUser(user);

                                        //Se guarda en sharedPreferences que el usuario ya ha entrado
                                        boolean userLogged = true;
                                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Constants.USER_LOGGED, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean(Constants.USER_LOGGED, userLogged);
                                        editor.putString(Constants.UID, firebaseAuth.getCurrentUser().getUid());
                                        editor.commit();

                                        //change activity to home
                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                                        //close activity
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                            } else {
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        notRegisteredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change activity to register activity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    /**
     * Method to check if all fields are correctly filled
     */
    private boolean checkFields() {
        if (emailET.getText().toString().isEmpty()){
            Toast.makeText(this, getString(R.string.email_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordET.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
