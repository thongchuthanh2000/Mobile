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

            AppDatabase appDatabase = AppDatabase.getAppDatabase(v.getContext());
            UserDAO userDAO = appDatabase.userDAO();
            User user = userDAO.getUserById(0);

        }

    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}