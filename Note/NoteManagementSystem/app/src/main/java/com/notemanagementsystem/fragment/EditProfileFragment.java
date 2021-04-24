package com.notemanagementsystem.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.R;
import com.notemanagementsystem.utils.SessionManager;
import com.notemanagementsystem.entity.User;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        //get all views
        edtFirstName = view.findViewById(R.id.edt_first_name);
        edtLastName = view.findViewById(R.id.edt_last_name);
        edtEmail = view.findViewById(R.id.edt_email);
        btnChangeProfile = view.findViewById(R.id.btn_change_profile);
        btnSwitchToHome = view.findViewById(R.id.btn_switch_to_home);

        //set event for views
        btnSwitchToHome.setOnClickListener(this);
        btnChangeProfile.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        //set even for btnSwitchToHone -- switch to home fragment
        if(v.getId() == R.id.btn_switch_to_home){
            //switch to home fragment
            replaceFragment(new HomeFragment());
        }
        //set event for btn_change_profile
        if(v.getId() == R.id.btn_change_profile){
            //declare a session
            SessionManager sessionManager = new SessionManager(getContext());

            //get information from views
            String firstName = edtFirstName.getText().toString();
            String lastName = edtLastName.getText().toString();
            String email = edtEmail.getText().toString();

            //Create a new user based on the id obtained from the session
            User user = AppDatabase.getAppDatabase(v.getContext())
                    .userDAO()
                    .getUserById(sessionManager.getUserId());

            //if the user does not enter the complete information
            if(firstName.isEmpty() || lastName.isEmpty()||email.isEmpty()){
                showToast("fill all filed");
            }else {
                //set a new setEmail setFirstName setLastName for the user
                user.setEmail(edtEmail.getText().toString().trim());
                user.setFirstName(edtFirstName.getText().toString().trim());
                user.setLastName(edtLastName.getText().toString().trim());

                //update data into the database
                AppDatabase.getAppDatabase(v.getContext())
                        .userDAO().update(user);

                showToast("OK");
            }
        }
    }

    //switch to another fragment
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    //show toast
    private void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();
    }
}