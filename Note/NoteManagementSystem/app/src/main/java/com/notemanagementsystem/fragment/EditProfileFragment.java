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


public class EditProfileFragment extends Fragment implements View.OnClickListener {

    public EditProfileFragment() {
    }

    private EditText edtFirstName, edtLastName, edtEmail;
    private Button btnChangeProfile, btnSwitchToHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        edtFirstName = view.findViewById(R.id.edt_first_name);
        edtLastName = view.findViewById(R.id.edt_last_name);
        edtEmail = view.findViewById(R.id.edt_email);
        btnChangeProfile = view.findViewById(R.id.btn_change_profile);
        btnSwitchToHome = view.findViewById(R.id.btn_switch_to_home);

        btnSwitchToHome.setOnClickListener(this);
        btnChangeProfile.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_switch_to_home){
            replaceFragment(new HomeFragment());
        }

        if(v.getId() == R.id.btn_change_profile){

            SessionManager sessionManager = new SessionManager(getContext());

            String firstName = edtFirstName.getText().toString();
            String lastName = edtLastName.getText().toString();
            String email = edtEmail.getText().toString();


            User user = AppDatabase.getAppDatabase(v.getContext())
                    .userDAO()
                    .getUserById(sessionManager.getUserId());

            if(firstName.isEmpty() || lastName.isEmpty()||email.isEmpty()){
                showToast("fill all filed");
            }else {
                user.setEmail(edtEmail.getText().toString().trim());
                user.setFirstName(edtFirstName.getText().toString().trim());
                user.setLastName(edtLastName.getText().toString().trim());

                AppDatabase.getAppDatabase(v.getContext())
                        .userDAO().update(user);

                Toast.makeText(v.getContext(), "OK", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    private void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();
    }
}