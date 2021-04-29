package com.notemanagementsystem.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.MainActivity;
import com.notemanagementsystem.R;
import com.notemanagementsystem.utils.SessionManager;
import com.notemanagementsystem.entity.User;

import static android.content.Intent.getIntent;


public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public EditText edtCurrentPassword, edtNewPassword, edtNewPasswordConfirm;
    public Button btnChangePassword, btnSwitchToHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        //get all views
        edtCurrentPassword = view.findViewById(R.id.edt_current_password);
        edtNewPassword = view.findViewById(R.id.edt_new_password);
        edtNewPasswordConfirm = view.findViewById(R.id.edt_new_password_confirm);
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnSwitchToHome = view.findViewById(R.id.btn_switch_to_home);

        //set event for views
        btnSwitchToHome.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        //set even for btnSwitchToHone -- switch to home fragment
        if(v.getId() == R.id.btn_switch_to_home){
            //restart MainActivity
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        }

        //set event for btnChangePassword
        if(v.getId() == R.id.btn_change_password){

            //declare a session
            SessionManager sessionManager = new SessionManager(getContext());

            //get information from views
            String currentPassword = edtCurrentPassword.getText().toString();
            String newPassword = edtNewPassword.getText().toString();
            String newPasswordConfirm = edtNewPasswordConfirm.getText().toString();

            //Create a new user based on the id obtained from the session
            User user = AppDatabase.getAppDatabase(v.getContext())
                        .userDAO()
                        .getUserById(sessionManager.getUserId());

            //if the user does not enter the complete information
            if(currentPassword.isEmpty() || newPassword.isEmpty()||newPasswordConfirm.isEmpty()){

                showToast("Please fill in all the information!");

            }else if(!(currentPassword.equals(user.getPassword()))){ //if the current password is not correct

                showToast("Current password is incorrect!");

            }else if(newPassword.equals(user.getPassword())){ //if the new password matches the current password

                showToast("New password must be different from the current password!");

            }else if(!(newPassword.equals(newPasswordConfirm))) { //if password confirmation is incorrect

                showToast("Password confirmation is incorrect!");

            }else if(!(regexInput(newPassword))) { ////if the user enters the wrong format of the information

                showToast("Password must be at least 6 characters!");

            }else {

                //set a new password for the user
                user.setPassword(edtNewPassword.getText().toString().trim());

                //update data into the database
                AppDatabase.getAppDatabase(v.getContext())
                        .userDAO().update(user);

                showToast("Update successful!");

            }
        }
    }

    //show toast
    public void showToast(String string){

        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();
    }

    /*checks the format of the input
    if true returns true and vice versa*/
    public Boolean regexInput(String string){

        if(string.matches(".{6,}")){

            return true;
        }
        return false;
    }
}