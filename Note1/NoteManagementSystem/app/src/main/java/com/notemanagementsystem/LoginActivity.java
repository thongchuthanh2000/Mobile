package com.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.User;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnSignIn, btnExit;
    CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getView();

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

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

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
    }

    public void getView(){

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnExit = findViewById(R.id.btnExit);
        cbRememberMe = findViewById(R.id.cbRememberMe);

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