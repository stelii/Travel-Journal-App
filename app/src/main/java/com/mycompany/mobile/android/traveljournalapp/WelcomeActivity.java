package com.mycompany.mobile.android.traveljournalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final ImageView appLogo = findViewById(R.id.welcome_activity_app_logo);
        final TextView appName = findViewById(R.id.welcome_activity_app_name);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseAuth.getCurrentUser() == null){
                    appLogo.setVisibility(View.GONE);
                    appName.setVisibility(View.GONE);
                    launchRegisterFragment();

                }else{
                    Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },1200);
    }

    private void launchRegisterFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_welcome_frame_layout,new RegisterFragment())
                .commit();
    }
}
