package com.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.entity.User;
import com.notemanagementsystem.utils.SessionManager;
/*
 *LoginActivity
 *@author  Quyet Sinh
 * @version 1.0
 * @since   2021-04-24
 */
public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnSignIn, btnExit;
    private CheckBox cbRememberMe;
    private FloatingActionButton fabAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addEvent();
    }

    //get all view in activity
    public void addControls(){

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnExit = findViewById(R.id.btn_exit);
        cbRememberMe = findViewById(R.id.cb_remember_me);
        fabAddUser = findViewById(R.id.fab_add_user);
    }

    //set event for views
    public void addEvent(){

        //pull user from session -- userSession
        User userSession = AppDatabase.getAppDatabase(getApplicationContext())
                .userDAO()
                .getById(SessionManager.getInstance().getUserId());

        if(userSession!=null && SessionManager.getInstance().getRememberMe()==true){

            //show the email and password of user in the previous session if any
            edtEmail.setText(userSession.getEmail());
            edtPassword.setText(userSession.getPassword());

            cbRememberMe.setChecked(true);
        }

        btnSignIn.setOnClickListener(v -> {

            final String email = edtEmail.getText().toString().trim();
            final String password = edtPassword.getText().toString().trim();

            //if the user has not entered the complete information
            if(!(validateInput_LogIn(email,password))) {

                Toast.makeText(getApplicationContext(), "Please fill in all the information !", Toast.LENGTH_SHORT).show();

            } else {

                new Thread(() -> {

                    //create a user according to the user information entered
                    User user = AppDatabase.getAppDatabase(getApplicationContext())
                            .userDAO()
                            .checkUser(email,password);

                    //if the user does not exist
                    if(user==null){
                        runOnUiThread(() -> {

                            Toast.makeText(getApplicationContext(),"Incorrect information!",Toast.LENGTH_SHORT).show();

                        });
                    } else {

                        //set information for the session
                        SessionManager.getInstance().setUserId(user.getId());

                        //if the user chooses to remember me
                        if (cbRememberMe.isChecked()==true){

                            //set the user's email into the session
                            SessionManager.getInstance().setRememberMe(true);

                        } else {

                            //set empty if not selected
                            SessionManager.getInstance().setRememberMe(false);

                        }

                        //switch to the main interface
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).start();
            }
        });

        btnExit.setOnClickListener(v -> {

            //exit the application
            finish();
            System.exit(0);
        });

        fabAddUser.setOnClickListener(v -> {

            //switch to the registration interface
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    /*Check if the input information is complete
    returns true if complete and vice versa  */
    public Boolean validateInput_LogIn ( String email,  String pass){

        if( email.isEmpty() || pass.isEmpty()){
            return false;
        }
        return true;
    }
}