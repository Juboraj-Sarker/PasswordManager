package com.juborajsarker.passwordmanager.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.activity.MainActivity;

import static android.content.Context.MODE_PRIVATE;


public class SignupFragment extends Fragment {

    InterstitialAd mInterstitialAd;

    View view;

    EditText emailET, passwordET, confPassET;
    Button signUpBTN;

    String email, password;
    String uid;

    private SharedPreferences sharedPreferences;
    boolean onlineRegister = false;
    FirebaseAuth firebaseAuth;



    public SignupFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_signup, container, false);


        sharedPreferences = getActivity().getSharedPreferences("registerStatus", MODE_PRIVATE);
        onlineRegister = sharedPreferences.getBoolean("onlineRegisterStatus", false);

        firebaseAuth = FirebaseAuth.getInstance();

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen1));

        init();
        setAction();






        return view;
    }



    private void init() {

        emailET = (EditText) view.findViewById(R.id.emailET);
        passwordET = (EditText) view.findViewById(R.id.passwordET);
        confPassET = (EditText) view.findViewById(R.id.confPassET);

        signUpBTN = (Button) view.findViewById(R.id.signUpBTN);
    }


    private void setAction() {

        signUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!isEmpty()){

                    if (emailValidation() && passwordValidation()){

                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Please wait .....");
                        progressDialog.show();



                        email = emailET.getText().toString();
                        password = passwordET.getText().toString();

                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()){


                                            AdRequest adRequest = new AdRequest.Builder().addTestDevice("93448558CC721EBAD8FAAE5DA52596D3").build();
                                            mInterstitialAd.loadAd(adRequest);



                                            mInterstitialAd.setAdListener(new AdListener() {
                                                public void onAdLoaded() {
                                                    showInterstitial();
                                                }
                                            });


                                            Toast.makeText(getContext(), "Successfully created user", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                            intent.putExtra("fragmentName", "home");

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                            if (user != null){

                                                uid = user.getUid();
                                            }

                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean("onlineRegisterStatus", true);
                                            editor.putString("email", email);
                                            editor.putString("uid", uid);
                                            editor.commit();

                                            startActivity(intent);
                                            getActivity().finish();

                                        }else {

                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }

                                    }
                                });

                    }else {

                        if (!emailValidation()){

                            emailET.setError("This is not valid email type !!!");

                        }

                        if (!passwordValidation()){

                            confPassET.setError("Password not matched !!!");
                        }
                    }



                }else {

                    if (emailET.getText().toString().equals("")){

                        emailET.setError("This field i required !!!");
                    }

                    if (passwordET.getText().toString().equals("")){

                        passwordET.setError("This field i required !!!");
                    }

                    if (confPassET.getText().toString().equals("")){

                        confPassET.setError("This field i required !!!");
                    }
                }
            }
        });
    }


    private boolean isEmpty() {

        if (emailET.getText().toString().equals("")
                || passwordET.getText().toString().equals("")
                || confPassET.getText().toString().equals("")){

            return true;
        }

        else {

            return false;
        }
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

    private boolean passwordValidation() {

        if (passwordET.getText().toString().equals(confPassET.getText().toString())){

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
