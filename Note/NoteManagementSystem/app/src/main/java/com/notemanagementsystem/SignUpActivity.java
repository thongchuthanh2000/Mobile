package com.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.User;
import com.notemanagementsystem.utils.RegularExpressions;

/*
 *SignUpActivity
 *@author  Quyet Sinh
 * @version 1.0
 * @since   2021-04-24
 */
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

    //show toast
    public void showToast(String string){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
    }

    //set event for views
    private void addEvents() {

        btnSignUp.setOnClickListener(v -> {

            //create a new user and set its information according to the information that the user entered
            User user = new User();
            user.setEmail(edtEmailSignUp.getText().toString());
            user.setPassword(edtPasswordSignUp.getText().toString());
            user.setLastName("");
            user.setFirstName("");

            //catch the error of input
            if(!(validateInputSignIn(user))) {

                edtEmailSignUp.requestFocus();
                showToast("Please fill in all the information!");

            } else if(!(RegularExpressions.regexEmail(user.getEmail()))) {

                edtEmailSignUp.requestFocus();
                showToast("Please enter the correct email format!");

            } else if(!(RegularExpressions.regexPassword(user.getPassword()))){

                edtPasswordSignUp.requestFocus();
                showToast("The password must be at least 6 characters in length !");

            } else if(!(edtConfirm.getText().toString().equals(edtPasswordSignUp.getText().toString()))) {

                edtConfirm.requestFocus();
                showToast("Password confirmation is incorrect !");

            } else {

                //declare database
                AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
                UserDAO userDAO = appDatabase.userDAO();

                //create a userCheck based on the email the user entered
                User userCheck = userDAO.checkExistUser(edtEmailSignUp.getText().toString());

                //If you cannot find a user with the same email as the one entered by the user
                if (userCheck == null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            //save the new user to the database
                            userDAO.insert(user);

                            edtEmailSignUp.setText("");
                            edtPasswordSignUp.setText("");
                            edtConfirm.setText("");

                            runOnUiThread(() -> {

                                //announced that it was successful
                                showToast("Success!");
                            });
                        }
                    }).start();
                } else{

                    edtEmailSignUp.requestFocus();
                    showToast( "Use already exists!");
                }
            }
        });

        btnSwitchToLogin.setOnClickListener(v -> {

            //switch to the login interface
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    ////get all view in activity
    private void addControls() {

        edtEmailSignUp = findViewById(R.id.edt_email_sign_up);
        edtPasswordSignUp = findViewById(R.id.edt_password_sign_up);
        edtConfirm = findViewById(R.id.edt_password_sign_up_confirm);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnSwitchToLogin = findViewById(R.id.btn_switch_to_login);
    }

    /*Check if the input information is complete
    returns true if complete and vice versa */
    private Boolean validateInputSignIn(User user){

        if(user.getEmail().isEmpty()||
                user.getPassword().isEmpty()||
                edtConfirm.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
}