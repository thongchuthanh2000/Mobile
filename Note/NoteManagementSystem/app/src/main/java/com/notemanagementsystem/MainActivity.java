package com.notemanagementsystem;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.notemanagementsystem.constant.SystemConstant;
import com.notemanagementsystem.dao.PriorityDAO;
import com.notemanagementsystem.entity.Priority;
import com.notemanagementsystem.entity.Status;
import com.notemanagementsystem.fragment.AbstractFragment;
import com.notemanagementsystem.fragment.CategoryFragment;
import com.notemanagementsystem.fragment.ChangePasswordFragment;
import com.notemanagementsystem.fragment.EditProfileFragment;
import com.notemanagementsystem.fragment.HomeFragment;
import com.notemanagementsystem.fragment.NoteFragment;
import com.notemanagementsystem.fragment.PriorityFragment;
import com.notemanagementsystem.fragment.StatusFragment;
import com.notemanagementsystem.utils.EmailUtil;
import com.notemanagementsystem.utils.SessionManager;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
/*
 *MainActivity
 *@author  Quyet Sinh
 * @version 1.0
 * @since   2021-04-24
 */
public class  MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView tv_appbar_tittle;
    /*
    Get headerView -> Set text email
    @Param : Null
    @Return: null
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_appbar_tittle = findViewById(R.id.tv_appbar_tittle);

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
        tv_appbar_tittle.setText("Dashboard Form");

        EmailUtil.setEmail(this.getWindow().getDecorView());
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

    /*
    Open form based on item
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        //Open fragment - Set text app bar
        if(id == R.id.nav_home){
                replaceFragment(new HomeFragment());
                tv_appbar_tittle.setText("Dashboard Form");
        }
        else if(id == R.id.nav_category){
                replaceFragment(new CategoryFragment());
                tv_appbar_tittle.setText("Category Form");
        }
        else if(id == R.id.nav_priority){
            replaceFragment(new PriorityFragment());
            tv_appbar_tittle.setText("Priority Form");
        }
        else if(id == R.id.nav_status){
            replaceFragment(new StatusFragment());
            tv_appbar_tittle.setText("Status Form");
        }
        else  if(id == R.id.nav_note){
                replaceFragment(new NoteFragment());
                tv_appbar_tittle.setText("Note Form");
        }
        else  if(id == R.id.nav_editProfile){
                replaceFragment(new EditProfileFragment());
                tv_appbar_tittle.setText("Edit Profile");
        }
        else  if(id == R.id.nav_changePassword){
                replaceFragment(new ChangePasswordFragment());
                tv_appbar_tittle.setText("Change Password");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    Reload fragment
     */
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

}