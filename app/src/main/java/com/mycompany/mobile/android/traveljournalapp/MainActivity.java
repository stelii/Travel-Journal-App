package com.mycompany.mobile.android.traveljournalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mycompany.mobile.android.traveljournalapp.database.Trip;
import com.mycompany.mobile.android.traveljournalapp.database.TripViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TripViewModel viewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        View header = navigationView.getHeaderView(0);
        TextView userNameItem = header.findViewById(R.id.nav_view_header_profileName);
        TextView userEmailItem = header.findViewById(R.id.nav_view_header_profileEmail);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            String userName = currentUser.getDisplayName();
            String userEmail = currentUser.getEmail();

            userNameItem.setText(userName);
            userEmailItem.setText(userEmail);
        }

        viewModel =
                new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getApplication())).get(TripViewModel.class);

        setUpToolBar();
        launchHomeFragment();

        handleNavigationItems();
    }

    private void launchHomeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment = new HomeFragment();
        transaction.replace(R.id.main_activity_frame_layout, fragment)
                .addToBackStack("home_fragment");
        transaction.commit();
    }

    private void setUpToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggleButton = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggleButton);
        toggleButton.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void handleNavigationItems(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (item.getItemId()){
                            case R.id.nav_view_home_item :
                                Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_activity_frame_layout);
                                if(f instanceof HomeFragment){
                                    drawerLayout.closeDrawer(Gravity.LEFT);
                                    break;
                                }
                                getSupportFragmentManager()
                                        .popBackStack("home_fragment",
                                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                launchHomeFragment();
                                drawerLayout.closeDrawer(Gravity.LEFT);

                            default : return ;

                        }
                    }
                },250);

                drawerLayout.closeDrawer(Gravity.LEFT);
                return true ;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_btn :
                viewModel.deleteAll();
                return true;
            default : return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) drawerLayout.closeDrawer(navigationView);
        else if (getSupportFragmentManager().getBackStackEntryCount() == 1) finish();
        else super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("add_edit_fragment");
        if(fragment != null) fragment.onActivityResult(requestCode,resultCode,data);
    }
}
