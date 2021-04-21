package com.notemanagementsystem.fragment;

import android.content.Intent;
import android.os.Bundle;

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
import com.notemanagementsystem.SessionManager;
import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.User;


public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public EditText edtCurrent_Password, edtNew_Password, edtPassword_Again;
    public Button btnChange_Password, btnSwitch_To_Home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        edtCurrent_Password = view.findViewById(R.id.edtCurrent_Password);
        edtNew_Password = view.findViewById(R.id.edtNew_Password);
        edtPassword_Again = view.findViewById(R.id.edtPassword_Again);
        btnChange_Password = view.findViewById(R.id.btnChange_Password);
        btnSwitch_To_Home = view.findViewById(R.id.btnSwitch_To_Home);

        btnSwitch_To_Home.setOnClickListener(this);
        btnChange_Password.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSwitch_To_Home){
            replaceFragment(new HomeFragment());
//            TextView tv_appbar_tittle = v.findViewById(R.id.tv_appbar_tittle);
//            tv_appbar_tittle.setText("Home");
        }

        if(v.getId() == R.id.btnChange_Password){

            SessionManager sessionManager = new SessionManager(getContext());

            String currentPassword = edtCurrent_Password.getText().toString();
            String newPassword = edtNew_Password.getText().toString();
            String againPassword = edtPassword_Again.getText().toString();

            AppDatabase appDatabase = AppDatabase.getAppDatabase(v.getContext());
            UserDAO userDAO = appDatabase.userDAO();
            User user = userDAO.getUserById(sessionManager.getUserId());

            if(currentPassword.isEmpty() || newPassword.isEmpty()||againPassword.isEmpty()){

                toast("fill all filed");

            }else if(!(currentPassword.equals(user.getPassword()))){

                toast("current is incorrect");

            }else if(newPassword.equals(user.getPassword())){

                toast("new password must");

            }else if(!(newPassword.equals(againPassword))) {

                toast("confirm");

            }else if(regexInput(newPassword)) {

                toast("6 ki tu");

            }else {

                user.setPassword(edtNew_Password.getText().toString());

                userDAO.updateUser(user);

                Toast.makeText(v.getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();

//            sessionManager.setLogin(false);

            }

        }

    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    public void toast(String string){

        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();

    }

    public Boolean regexInput(String string){

        if(string.matches(".{6,}")){

            return true;

        }

        return false;
    }
}