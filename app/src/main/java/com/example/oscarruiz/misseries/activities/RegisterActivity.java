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
import com.example.oscarruiz.misseries.controllers.DataController;
import com.example.oscarruiz.misseries.models.User;
import com.example.oscarruiz.misseries.session.Session;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.oscarruiz.misseries.utils.Constants;

public class RegisterActivity extends AppCompatActivity {

    /**
     * google login layout
     */
    private RelativeLayout googleLoginLayout;

    /**
     * Forgot password button
     */
    private Button forgotPassword;

    /**
     * Google Api client
     */
    private GoogleApiClient googleApiClient;

    /**
     * Loading layout
     */
    private RelativeLayout loading;

    /**
     * Data controller
     */
    private DataController dataController;

    /**
     * Nick edit text
     */
    private EditText nickET;

    /**
     * Email edit text
     */
    private EditText emailET;

    /**
     * password edit text
     */
    private EditText passwordET;

    /**
     * Register button
     */
    private Button registerButton;

    /**
     * Registered button
     */
    private Button registeredButton;

    /**
     * Firebase Auth instance
     */
    private FirebaseAuth firebaseAuth;

    /**
     * User instance
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hide Action Bar
        getSupportActionBar().hide();

        //init Google sign in options
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //Google Api client
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        //set data controller
        dataController = new DataController();

        //firebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();

        getViews();
        setListeners();
    }

    /**
     * Method to get views
     */
    private void getViews() {
        nickET = (EditText)findViewById(R.id.nick);
        emailET = (EditText)findViewById(R.id.email);
        passwordET = (EditText)findViewById(R.id.password);
        registerButton = (Button)findViewById(R.id.register_button);
        registeredButton = (Button)findViewById(R.id.registered_button);
        loading = (RelativeLayout)findViewById(R.id.loading_layout);
        googleLoginLayout = (RelativeLayout)findViewById(R.id.google_login);
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkFields()) {
                    //Show loading layout
                    loading.setVisibility(View.VISIBLE);
                    //Create user
                    firebaseAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString()).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                //Save user in session
                                user = new User(nickET.getText().toString(), emailET.getText().toString(), passwordET.getText().toString());
                                Session.getInstance().setUser(user);

                                //Save user in database
                                dataController.saveUser(user);

                                register();

                            } else {
                                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        registeredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change activity to login
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        googleLoginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create intent to sign in
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, Constants.GOOGLE_SIGN_IN);
            }
        });
    }

    /**
     * Method to check if all fields are correctly filled
     */
    private boolean checkFields() {
        if (nickET.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.nick_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailET.getText().toString().isEmpty()){
            Toast.makeText(this, getString(R.string.email_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordET.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount googleAccount = result.getSignInAccount();
                user = new User(googleAccount.getEmail(), googleAccount.getDisplayName());

                //save user in session
                Session.getInstance().setUser(user);

                register();
            } else if (!result.isSuccess()) {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Method to register user
     */
    private void register() {
        //save user is registered in preferences
        boolean userLogged = true;
        SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences(Constants.USER_LOGGED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.USER_LOGGED, userLogged);
        editor.putString(Constants.UID, firebaseAuth.getCurrentUser().getUid());

        editor.commit();

        //Change activity to Home Activity
        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));

        //Close Activity
        finish();
    }
}
