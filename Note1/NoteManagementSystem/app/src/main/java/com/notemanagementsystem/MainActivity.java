package com.notemanagementsystem;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.notemanagementsystem.fragment.CategoryFragment;
import com.notemanagementsystem.fragment.ChangePasswordFragment;
import com.notemanagementsystem.fragment.EditProfileFragment;
import com.notemanagementsystem.fragment.HomeFragment;
import com.notemanagementsystem.fragment.NoteFragment;
import com.notemanagementsystem.fragment.PriorityFragment;
import com.notemanagementsystem.fragment.StatusFragment;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;

    private static final int fragment_home = 1;
    private static final int fragment_category = 2;
    private static final int fragment_priority = 3;
    private static final int fragment_status = 4;
    private static final int fragment_note = 5;
    private static final int fragment_edit_profile = 6;
    private static final int fragment_change_password = 7;

    private int currentFragment = fragment_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_category, R.id.nav_priority, R.id.nav_note,
//                R.id.nav_status,R.id.nav_editProfile,R.id.nav_changePassword)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());

        TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
        tv_appbar_tittle.setText("Home");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

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
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }

        return  super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_home){
            if(fragment_home != currentFragment){
                replaceFragment(new HomeFragment());
                currentFragment = fragment_home;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Home");
            }
        }
        else if(id == R.id.nav_category){
            if(fragment_category != currentFragment){
                replaceFragment(new CategoryFragment());
                currentFragment = fragment_category;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Category");
            }
        }
        else if(id == R.id.nav_priority){
            if(fragment_priority != currentFragment){
                replaceFragment(new PriorityFragment());
                currentFragment = fragment_priority;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Priority");
            }
        }
        else if(id == R.id.nav_status){
            if(fragment_status != currentFragment){
                replaceFragment(new StatusFragment());
                currentFragment = fragment_status;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Status");
            }
        }
        else  if(id == R.id.nav_note){
            if(fragment_note != currentFragment){
                replaceFragment(new NoteFragment());
                currentFragment = fragment_note;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Note");

                FloatingActionButton fab_add_note = findViewById(R.id.fab_add_note);

            }
        }
        else  if(id == R.id.nav_editProfile){
            if(fragment_edit_profile != currentFragment){
                replaceFragment(new EditProfileFragment());
                currentFragment = fragment_edit_profile;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Edit Profile");
            }
        }
        else  if(id == R.id.nav_changePassword){
            if(fragment_change_password != currentFragment){
                replaceFragment(new ChangePasswordFragment());
                currentFragment = fragment_change_password;
                TextView tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);
                tv_appbar_tittle.setText("Change Password");
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

}