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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.juborajsarker.passwordmanager.R;

public class ProfileActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;

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


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen1));

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

        String encrypt ;
        int value = 0;
        String sub;

        for (int i=0; i<email.length(); i++){

            if (email.charAt(i)=='@'){

                value = i;
            }
        }

        encrypt= email.substring(0, value);
        sub = email.substring(value,email.length());
        char finalEncrypt[] = encrypt.toCharArray();

        for (int i=1; i< encrypt.length() - 1; i++){

            finalEncrypt[i] = '*';

        }

        finalEncrypt[0] = encrypt.charAt(0);
        finalEncrypt[encrypt.length()-1] = encrypt.charAt(encrypt.length()-1);
        emailTV.setText(String.valueOf(finalEncrypt) + sub);
    }

    private void setAction() {

        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AdRequest adRequest = new AdRequest.Builder().addTestDevice("93448558CC721EBAD8FAAE5DA52596D3").build();
                mInterstitialAd.loadAd(adRequest);



                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });


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


    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


}
