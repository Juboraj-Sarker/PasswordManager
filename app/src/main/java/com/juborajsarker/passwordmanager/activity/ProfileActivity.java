package com.juborajsarker.passwordmanager.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.juborajsarker.passwordmanager.R;

public class ProfileActivity extends AppCompatActivity {

    TextView emailTV;
    Button logoutBTN;



    private SharedPreferences sharedPreferences;
    boolean onlineRegister = false;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        sharedPreferences = getSharedPreferences("registerStatus", MODE_PRIVATE);
        onlineRegister = sharedPreferences.getBoolean("onlineRegisterStatus", true);
        email = sharedPreferences.getString("email", "");


        init();
        setAction();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:{

                super.onBackPressed();

            }default:{

                return super.onOptionsItemSelected(item);
            }



        }

    }





    private void init() {

        emailTV = (TextView) findViewById(R.id.emailTV);
        logoutBTN = (Button) findViewById(R.id.logoutBTN);
        emailTV.setText(email);
    }

    private void setAction() {

        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("onlineRegisterStatus", false);
                editor.putString("email", "");
                editor.commit();

                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.putExtra("fragmentName", "home");
                startActivity(intent);

            }
        });
    }


}
