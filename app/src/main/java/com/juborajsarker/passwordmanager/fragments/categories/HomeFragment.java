package com.juborajsarker.passwordmanager.fragments.categories;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.activity.MainActivity;
import com.juborajsarker.passwordmanager.activity.ProfileActivity;
import com.juborajsarker.passwordmanager.activity.RegisterActivity;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    View view;

    CardView bankCard, cryptoCard, debitCard, emailCard, socialCard, othersCard;
    CardView userCard;
    LinearLayout onlineUserLayout, offlineUserLayout;



    String email;

    boolean onlineRegister = false;
    public SharedPreferences sharedPreferences;





    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getActivity().getSharedPreferences("registerStatus", MODE_PRIVATE);
        onlineRegister = sharedPreferences.getBoolean("onlineRegisterStatus", false);
        email = sharedPreferences.getString("email","");



        init();
        setAction();


        if (!onlineRegister){

            offlineUserLayout.setVisibility(View.VISIBLE);
            onlineUserLayout.setVisibility(View.GONE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("onlineRegisterStatus", false);
            editor.commit();


        }else {

            offlineUserLayout.setVisibility(View.GONE);
            onlineUserLayout.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("onlineRegisterStatus", true);
            editor.commit();


        }


        return view;
    }

    private void init() {

        bankCard = (CardView) view.findViewById(R.id.card_1_bank);
        cryptoCard = (CardView) view.findViewById(R.id.card_2_crypto);
        debitCard = (CardView) view.findViewById(R.id.card_3_debitOrCredit);
        emailCard = (CardView) view.findViewById(R.id.card_4_email);
        socialCard = (CardView) view.findViewById(R.id.card_5_social);
        othersCard = (CardView) view.findViewById(R.id.card_6_others);

        userCard = (CardView) view.findViewById(R.id.userCard);



        onlineUserLayout = (LinearLayout) view.findViewById(R.id.online_user_LAYOUT);
        offlineUserLayout = (LinearLayout) view.findViewById(R.id.offline_user_LAYOUT);

        onlineUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        offlineUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), RegisterActivity.class));
            }
        });



    }

    private void setAction() {

        bankCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragmentName", "bank");
                startActivity(intent);
            }
        });


        cryptoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragmentName", "crypto");
                startActivity(intent);
            }
        });



        debitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragmentName", "card");
                startActivity(intent);
            }
        });



        emailCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragmentName", "email");
                startActivity(intent);
            }
        });



        socialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragmentName", "social");
                startActivity(intent);

            }
        });



        othersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("fragmentName", "other");
                startActivity(intent);
            }
        });
    }




}
