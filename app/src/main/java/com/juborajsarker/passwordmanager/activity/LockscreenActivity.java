package com.juborajsarker.passwordmanager.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.juborajsarker.passwordmanager.R;

public class LockscreenActivity extends AppCompatActivity {


    public SharedPreferences sharedPreferences;


    Button okBTN;
    TextView inputTV;
    Vibrator vibrator;
    int counter = 0;
    String masterPassword;

    InterstitialAd mInterstitialAd;

    boolean doubleBackToExitPressedOnce = false;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lockscreen);




        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sharedPreferences = this.getSharedPreferences("settings", MODE_PRIVATE);
        masterPassword = sharedPreferences.getString("masterPassword", "");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen1));

        init();


    }

    private void init() {

        inputTV = findViewById(R.id.tv_input);
        okBTN = findViewById(R.id.okBtn);
        okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (inputTV.getText().toString().length() < 6 ){

                    inputTV.setError("Password must be 6 digit");


                }
                if (inputTV.getText().toString().length() == 6) {


                    String getPassword = inputTV.getText().toString();

                    if (getPassword.equals(masterPassword)){

                        progressDialog = new ProgressDialog(LockscreenActivity.this);
                        progressDialog.setMessage("Please wait while checking information ......");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        AdRequest adRequest = new AdRequest.Builder().addTestDevice("93448558CC721EBAD8FAAE5DA52596D3").build();
                        mInterstitialAd.loadAd(adRequest);



                        mInterstitialAd.setAdListener(new AdListener() {
                            public void onAdLoaded() {
                                showInterstitial();
                            }
                        });

                        Intent intent = new Intent(LockscreenActivity.this, MainActivity.class);
                        intent.putExtra("fragmentName", "home");
                        Toast.makeText(LockscreenActivity.this, "Success !!!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(intent);
                        finish();



                    }else {

                        inputTV.setError("Wrong password !!!");

                    }
                }

            }
        });
    }

    public void numberPressed(View view) {


        switch (view.getId()){

            case R.id.tv_0:{

                inputTV.append("0");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;
                break;
            }

            case R.id.tv_1:{

                inputTV.append("1");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }

            case R.id.tv_2:{

                inputTV.append("2");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_3:{

                inputTV.append("3");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }



            case R.id.tv_4:{

                inputTV.append("4");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }



            case R.id.tv_5:{

                inputTV.append("5");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_6:{

                inputTV.append("6");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_7:{

                inputTV.append("7");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_8:{

                inputTV.append("8");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_9:{

                inputTV.append("9");

                if (counter < 6){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.iv_backspace:{


                if (inputTV.getText().toString().length()>0){

                    inputTV.setText(inputTV.getText().toString().substring(0, inputTV.getText().toString().length()-1));
                    counter = inputTV.getText().toString().length();
                }

                if (inputTV.getText().toString().equals("")){

                    counter = 0;
                }


                break;
            }


            case R.id.iv_close:{



                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("Thanks for using my app")
                        .setMessage("\nAre you sure you want to really exit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                AppExit();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();


                break;
            }

        }


    }



    private void AppExit() {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
