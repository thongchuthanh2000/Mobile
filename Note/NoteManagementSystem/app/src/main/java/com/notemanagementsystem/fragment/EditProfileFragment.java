package com.notemanagementsystem.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.utils.Utils;
import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.R;
import com.notemanagementsystem.utils.SessionManager;
import com.notemanagementsystem.entity.User;

import java.sql.Struct;
import java.util.function.Consumer;
import java.util.function.Predicate;

/*
 *StatusFragment
 *@author  Chu Thanh
 * @version 1.0
 * @since   2021-04-24
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener {

    public EditProfileFragment() {
        // Required empty public constructor
    }

    private EditText edtFirstName, edtLastName, edtEmail;
    private Button btnChangeProfile, btnSwitchToHome;
    private User user;
    private Integer userId;

    Consumer<EditText> setFocus = (editText -> editText.requestFocus());
    Predicate<String> checkEmailExists = e -> {
        return  AppDatabase.getAppDatabase(getContext())
                .userDAO()
                .checkEmailExist(e, userId);
    };

    Predicate<CharSequence> isValidEmail = target -> {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        addControls(view);
        getUser(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void restartActivity() {
        //restart MainActivity
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    private void addControls(View view) {
        //get all views
        edtFirstName = view.findViewById(R.id.edt_first_name);
        edtLastName = view.findViewById(R.id.edt_last_name);
        edtEmail = view.findViewById(R.id.edt_email);
        btnChangeProfile = view.findViewById(R.id.btn_change_profile);
        btnSwitchToHome = view.findViewById(R.id.btn_switch_to_home);

        //set event for views
        btnSwitchToHome.setOnClickListener(this);
        btnChangeProfile.setOnClickListener(this);
    }

    private void getUser(View view) { //declare a session
        userId = SessionManager.getInstance().getUserId();

        //Create a new user based on the id obtained from the session
        user = AppDatabase.getAppDatabase(view.getContext())
                .userDAO()
                .getUserById(userId);

        edtEmail.setText(user.getEmail());
        edtFirstName.setText(user.getFirstName());
        edtLastName.setText(user.getLastName());
    }


    @Override
    public void onClick(View v) {
        //set even for btnSwitchToHone -- switch to home fragment
        if (v.getId() == R.id.btn_switch_to_home) {
            restartActivity();
        }

        //set event for btn_change_profile
        if (v.getId() == R.id.btn_change_profile) {

            //get information from views
            String firstName = edtFirstName.getText().toString().trim();
            String lastName = edtLastName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();


            //if the user does not enter the complete information
            if (TextUtils.isEmpty(firstName)) {
                setFocus.accept(edtFirstName);
                showToast("fill firstname");
            } else if (TextUtils.isEmpty(lastName)) {
                setFocus.accept(edtLastName);
                showToast("fill lastname");

            } else if (TextUtils.isEmpty(email)) {
                setFocus.accept(edtEmail);
                showToast("fill email");
            }
            if (!isValidEmail.test(email)) {
                setFocus.accept(edtEmail);
                showToast("error email");
            } else
            if (checkEmailExists.test(email)) {
                setFocus.accept(edtEmail);
                showToast("Email Exists");
            } else {
                //set a new setEmail setFirstName setLastName for the user
                user.setEmail(email);
                user.setFirstName(firstName);
                user.setLastName(lastName);

                //update data into the database
                AppDatabase.getAppDatabase(v.getContext())
                        .userDAO().update(user);

                showToast("Success");
            }
        }
    }

    //show toast
    private void showToast(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }


}