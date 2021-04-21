package com.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.User;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnSignIn, btnExit;
    CheckBox cbRememberMe;
    FloatingActionButton fab_add_user;
//    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        addControls();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = edtEmail.getText().toString();
                final String password = edtPassword.getText().toString();

                if(validateInput_LogIn()){

                    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
                    UserDAO userDAO = appDatabase.userDAO();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            User user = userDAO.checkUser(email,password);

                            if(user==null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"Invalid Credentials!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {

                                sessionManager.setUserId(user.getId());
                                sessionManager.setLogin(true);

//                                if(cbRememberMe.isChecked()){
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    editor.putString("username",user.email);
//                                    editor.putString("pass",user.password);
//
//                                    editor.putBoolean("checked",true);
//                                    editor.commit();
//                                }

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                                //to get session - use "session.getUserId();"

                            }
                        }
                    }).start();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Fill all fields!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        fab_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addControls(){

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnExit = findViewById(R.id.btnExit);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        fab_add_user = findViewById(R.id.fab_add_user);

//        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
//        edtEmail.setText(sharedPreferences.getString("email",""));
//        edtPassword.setText(sharedPreferences.getString("pass",""));
//        cbRememberMe.setChecked(sharedPreferences.getBoolean("checked",false));

    }

    public void addEvent(){


    }

    public Boolean validateInput_LogIn (){
        String email = edtEmail.getText().toString();
        String pass = edtPassword.getText().toString();

        if( email.isEmpty() || pass.isEmpty()){
            return false;
        }
        return true;
    }

}