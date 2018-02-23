package com.juborajsarker.passwordmanager.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.juborajsarker.passwordmanager.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText emailET;
    Button verificationBTN;
    TextView resetTV;

    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen1));


        init();
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

        emailET = (EditText) findViewById(R.id.emailET);
        verificationBTN = (Button) findViewById(R.id.verificationBTN);
        resetTV = (TextView) findViewById(R.id.resetTV);

        verificationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




               if (emailET.getText().toString().equals("") || ! emailValidation()){

                   if (emailET.getText().toString().equals("")){

                       emailET.setError("Please enter a valid email !!!");
                   }

                   if (!emailValidation()){

                       emailET.setError("This is not valid email format\nPlease check your input");
                   }

               }else {


                   final ProgressDialog progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
                   progressDialog.setMessage("Please wait .....");
                   progressDialog.show();




                   FirebaseAuth auth = FirebaseAuth.getInstance();
                   String emailAddress = emailET.getText().toString();

                   auth.sendPasswordResetEmail(emailAddress)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()) {

                                       Toast.makeText(ForgetPasswordActivity.this,
                                               "Email send successfully !!!\nPlease check your email to reset password", Toast.LENGTH_SHORT).show();

                                       resetTV.setVisibility(View.VISIBLE);
                                       resetTV.setText("And verification email has been send to \n"+ emailET.getText().toString() + "\nPlease follow the instruction from email. Thank you" );
                                       emailET.setText("");

                                       AdRequest adRequest = new AdRequest.Builder().addTestDevice("93448558CC721EBAD8FAAE5DA52596D3").build();
                                       mInterstitialAd.loadAd(adRequest);



                                       mInterstitialAd.setAdListener(new AdListener() {
                                           public void onAdLoaded() {
                                               showInterstitial();
                                           }
                                       });

                                       progressDialog.dismiss();


                                   }else {

                                       emailET.setError(task.getException().getMessage());
                                       resetTV.setVisibility(View.GONE);
                                       progressDialog.dismiss();
                                   }
                               }
                           });
               }

            }
        });
    }

    private boolean emailValidation() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String inputEmail = emailET.getText().toString().trim();

        if (inputEmail.matches(emailPattern)){

            return true;
        }else {

            return false;
        }


    }



    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

}
