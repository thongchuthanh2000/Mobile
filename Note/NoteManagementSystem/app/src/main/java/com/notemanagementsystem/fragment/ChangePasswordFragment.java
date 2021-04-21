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
import com.notemanagementsystem.SessionManager;
import com.notemanagementsystem.entity.User;


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

        edtCurrentPassword = view.findViewById(R.id.edt_current_password);
        edtNewPassword = view.findViewById(R.id.edt_new_password);
        edtNewPassword = view.findViewById(R.id.edt_new_password_confirm);
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnSwitchToHome = view.findViewById(R.id.btn_switch_to_home);

        btnSwitchToHome.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_switch_to_home){
            replaceFragment(new HomeFragment());
        }

        if(v.getId() == R.id.btn_change_password){

            SessionManager sessionManager = new SessionManager(getContext());

            String currentPassword = edtCurrentPassword.getText().toString();
            String newPassword = edtNewPassword.getText().toString();
            String newPasswordConfirm = edtNewPasswordConfirm.getText().toString();


            User user = AppDatabase.getAppDatabase(v.getContext())
                        .userDAO()
                        .getUserById(sessionManager.getUserId());

            if(currentPassword.isEmpty() || newPassword.isEmpty()||newPasswordConfirm.isEmpty()){
                ShowToast("fill all filed");
            }else if(!(currentPassword.equals(user.getPassword()))){
                ShowToast("current is incorrect");
            }else if(newPassword.equals(user.getPassword())){
                ShowToast("new password must");
            }else if(!(newPassword.equals(newPasswordConfirm))) {
                ShowToast("confirm");
            }else if(regexInput(newPassword)) {
                ShowToast("6 ki tu");
            }else {
                user.setPassword(edtNewPassword.getText().toString().trim());

                AppDatabase.getAppDatabase(v.getContext())
                        .userDAO().update(user);

                Toast.makeText(v.getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    public void ShowToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();
    }

    public Boolean regexInput(String string){

        if(string.matches(".{6,}")){
            return true;
        }
        return false;
    }
}