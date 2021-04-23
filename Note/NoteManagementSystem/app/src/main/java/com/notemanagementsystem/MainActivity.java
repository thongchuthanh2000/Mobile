package com.notemanagementsystem;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.User;
import com.notemanagementsystem.fragment.CategoryFragment;
import com.notemanagementsystem.fragment.ChangePasswordFragment;
import com.notemanagementsystem.fragment.EditProfileFragment;
import com.notemanagementsystem.fragment.HomeFragment;
import com.notemanagementsystem.fragment.NoteFragment;
import com.notemanagementsystem.fragment.PriorityFragment;
import com.notemanagementsystem.fragment.StatusFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class  MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    //private AppBarConfiguration mAppBarConfiguration;

//    private static final int fragment_home = 1;
//    private static final int fragment_category = 2;
//    private static final int fragment_priority = 3;
//    private static final int fragment_status = 4;
//    private static final int fragment_note = 5;
//    private static final int fragment_edit_profile = 6;
//    private static final int fragment_change_password = 7;
//
//    private int currentFragment = fragment_home;

//    private void setEmail(String gmail) {
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        View headerView = navigationView.getHeaderView(0);
//        TextView txtEmail = (TextView) headerView.findViewById(R.id.textEmail);
//        txtEmail.setText(gmail);
//    }
//    SessionManager sessionManager = new SessionManager(getApplicationContext());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());

        TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
        tv_appbar_tittle.setText("Home");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_home){
//            if(fragment_home != currentFragment){
                replaceFragment(new HomeFragment());
//                currentFragment = fragment_home;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Dashboard Form");
//            }
        }
        else if(id == R.id.nav_category){
//            if(fragment_category != currentFragment){
                replaceFragment(new CategoryFragment());
//                currentFragment = fragment_category;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Category Form");
//            }
        }
        else if(id == R.id.nav_priority){
//            if(fragment_priority != currentFragment){
                replaceFragment(new PriorityFragment());
//                currentFragment = fragment_priority;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Priority Form");
//            }
        }
        else if(id == R.id.nav_status){
//            if(fragment_status != currentFragment){
                replaceFragment(new StatusFragment());
//                currentFragment = fragment_status;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Status Form");
//            }
        }
        else  if(id == R.id.nav_note){
//            if(fragment_note != currentFragment){
                replaceFragment(new NoteFragment());
//                currentFragment = fragment_note;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Note Form");

                FloatingActionButton fab_add_note = findViewById(R.id.fab_add_note);

//            }
        }
        else  if(id == R.id.nav_editProfile){
//            if(fragment_edit_profile != currentFragment){
                replaceFragment(new EditProfileFragment());
//                currentFragment = fragment_edit_profile;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Edit Profile");
//            }
        }
        else  if(id == R.id.nav_changePassword){
//            if(fragment_change_password != currentFragment){
                replaceFragment(new ChangePasswordFragment());
//                currentFragment = fragment_change_password;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Change Password");
//            }
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment){
//        setEmail(sessionManager.getEmail());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

}