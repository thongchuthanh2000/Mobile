package com.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.User;

public class SignUpActivity extends AppCompatActivity {

    EditText edtEmail_SignUp, edtPassword_SignUp, edtConfirm ;
    Button btnSignUp, btnSwitchToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail_SignUp = findViewById(R.id.edtEmail_SignUp);
        edtPassword_SignUp = findViewById(R.id.edtPassword_SignUp);
        edtConfirm = findViewById(R.id.edtConfirm);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSwitchToLogin = findViewById(R.id.btnSwitch_To_Login);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setEmail(edtEmail_SignUp.getText().toString());
                user.setPassWord(edtPassword_SignUp.getText().toString());

                if(validateInput_SignIn(user)){

                    if(regexInput(user)){

                        if(edtConfirm.getText().toString().equals(edtPassword_SignUp.getText().toString())) {

                            AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
                            UserDAO userDAO = appDatabase.userDAO();

                            User userCheck = userDAO.checkUser(edtEmail_SignUp.getText().toString(), edtPassword_SignUp.getText().toString());

                            if (userCheck == null) {

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        userDAO.insert(user);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                }).start();

                            }
                            else{

                                Toast.makeText(getApplicationContext(), "Use already exists!", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {

                            Toast.makeText(getApplicationContext(),"Password confirmation is incorrect !",Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Please enter the correct email format and the password must be at least 6 characters in length !",Toast.LENGTH_SHORT).show();

                    }
                }
                else{

                    Toast.makeText(getApplicationContext(),"Please fill in all fields!",Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnSwitchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    private Boolean validateInput_SignIn(User user){

        if(user.getEmail().isEmpty()||
                user.getPassWord().isEmpty()||
                edtConfirm.getText().toString().isEmpty()){
            return false;
        }
        return true;

    }

    public Boolean regexInput(User user){

        if(user.email.matches("^(.+)@(.+)$") && user.password.matches(".{6,}")){

            return true;

        }

        return false;
    }
}