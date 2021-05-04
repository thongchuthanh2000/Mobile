package com.notemanagementsystem.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.MainActivity;
import com.notemanagementsystem.R;
import com.notemanagementsystem.entity.User;

public class EmailUtil {
    public static void setEmail(View view) {
        DrawerLayout drawerLayout =view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) drawerLayout.findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView txtEmail = (TextView) headerView.findViewById(R.id.textEmail);

        Integer userId = SessionManager.getInstance().getUserId();
        //Create a new user based on the id obtained from the session
        User user = AppDatabase.getAppDatabase(view.getContext())
                .userDAO()
                .getUserById(userId);

        txtEmail.setText(user.getEmail());
    }
}
