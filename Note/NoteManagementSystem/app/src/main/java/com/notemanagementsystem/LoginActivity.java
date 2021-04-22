package com.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.User;

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



    public void addControls(){
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnExit = findViewById(R.id.btn_exit);
        cbRememberMe = findViewById(R.id.cb_remember_me);
        fabAddUser = findViewById(R.id.fab_add_user);
    }

    public void addEvent(){
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        edtEmail.setText(sessionManager.getUserName());
        btnSignIn.setOnClickListener(v -> {
            final String email = edtEmail.getText().toString().trim();
            final String password = edtPassword.getText().toString().trim();

            if(validateInput_LogIn(email,password)){

                new Thread(() -> {
                    User user = AppDatabase.getAppDatabase(getApplicationContext())
                            .userDAO()
                            .checkUser(email,password);

                    if(user==null){
                        runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(),"Invalid Credentials!",Toast.LENGTH_SHORT).show();
                        });
                    }
                    else {

                        sessionManager.setUserId(user.getId());
                        sessionManager.setLogin(true);


                        if (cbRememberMe.isChecked()==true){
                            sessionManager.setUserName(email);
                        }
                        else {
                            sessionManager.setUserName("");

                        }

                        sessionManager.setEmail(email);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).start();
            }
            else {
                Toast.makeText(getApplicationContext(),"Fill all fields!",Toast.LENGTH_SHORT).show();
            }
        });




        btnExit.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });

        fabAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    public Boolean validateInput_LogIn ( String email,  String pass){
        if( email.isEmpty() || pass.isEmpty()){
            return false;
        }
        return true;
    }

}