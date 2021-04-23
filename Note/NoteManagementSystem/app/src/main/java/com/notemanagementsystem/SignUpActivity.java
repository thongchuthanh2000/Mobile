package com.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtEmailSignUp, edtPasswordSignUp, edtConfirm ;
    private Button btnSignUp, btnSwitchToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        addControls();
        addEvents();

    }

    public void showToast(String string){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
    }

    private void addEvents() {

        btnSignUp.setOnClickListener(v -> {

            User user = new User();
            user.setEmail(edtEmailSignUp.getText().toString());
            user.setPassword(edtPasswordSignUp.getText().toString());

            if(!(validateInputSignIn(user))) {

                showToast("Please fill in all the information!");

            }
            else if(!(regexInput(user))) {

                showToast("Please enter the correct email format and the password must be at least 6 characters in length !");

            }
            else if(!(edtConfirm.getText().toString().equals(edtPasswordSignUp.getText().toString()))) {

                showToast("Password confirmation is incorrect !");

            }
            else {

                AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
                UserDAO userDAO = appDatabase.userDAO();

                User userCheck = userDAO.checkExistUser(edtEmailSignUp.getText().toString());

                if (userCheck == null) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            userDAO.insert(user);

                            runOnUiThread(() -> {
                                showToast("Success!");
                            });
                        }
                    }).start();

                }
                else{

                    showToast( "Use already exists!");

                }
            }
        });

        btnSwitchToLogin.setOnClickListener(v -> {

            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);

        });

    }

    private void addControls() {

        edtEmailSignUp = findViewById(R.id.edt_email_sign_up);
        edtPasswordSignUp = findViewById(R.id.edt_password_sign_up);
        edtConfirm = findViewById(R.id.edt_password_sign_up_confirm);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnSwitchToLogin = findViewById(R.id.btn_switch_to_login);

    }

    private Boolean validateInputSignIn(User user){

        if(user.getEmail().isEmpty()||
                user.getPassword().isEmpty()||
                edtConfirm.getText().toString().isEmpty()){
            return false;
        }

        return true;

    }

    public Boolean regexInput(User user){

        if(user.getEmail().matches("^(.+)@(.+)$") && user.getPassword().matches(".{6,}")){
            return true;
        }

        return false;
    }
}