package com.juborajsarker.passwordmanager.fragments.cloud;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.activity.RegisterActivity;
import com.juborajsarker.passwordmanager.adapters.CardAdapter;
import com.juborajsarker.passwordmanager.adapters.RestoreAdapter;
import com.juborajsarker.passwordmanager.adapters.RestoreCardAdapter;
import com.juborajsarker.passwordmanager.database.CardDatabase;
import com.juborajsarker.passwordmanager.database.DBHelper;
import com.juborajsarker.passwordmanager.model.CardModel;
import com.juborajsarker.passwordmanager.model.FirebaseCardModel;
import com.juborajsarker.passwordmanager.model.FirebaseModel;
import com.juborajsarker.passwordmanager.model.GridSpacingItemDecoration;
import com.juborajsarker.passwordmanager.model.ModelPassword;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class RestoreFragment extends Fragment {

    View view;

    ProgressDialog progressDialog;

    LinearLayout restoreLayout;
    Spinner categorySpinner;
    RecyclerView recyclerView;
    CardView restoreCV;
    TextView instructionTV;



    private List<ModelPassword> passwordList;
    private DBHelper dbHelper;


    RestoreAdapter restoreAdapter;
    RestoreCardAdapter restoreCardAdapter;


    CardAdapter cardAdapter;
    List<CardModel> cardModelList;
    CardDatabase cardDatabase;

    private String TABLE_NAME = "";


    public SharedPreferences sharedPreferences;
    boolean onlineRegister;
    String emailPref, userPref;

    DatabaseReference databaseReference;

    int index;


    public RestoreFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_restore, container, false);

        sharedPreferences = getActivity().getSharedPreferences("registerStatus", MODE_PRIVATE);
        onlineRegister = sharedPreferences.getBoolean("onlineRegisterStatus", false);
        emailPref = sharedPreferences.getString("email", "");
        userPref = sharedPreferences.getString("uid", "");

        init();


        return view;
    }

    private void init() {

        restoreLayout = (LinearLayout) view.findViewById(R.id.restore_LAYOUT);
        categorySpinner = (Spinner) view.findViewById(R.id.category_SPINNER);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        restoreCV = (CardView) view.findViewById(R.id.restore_cardView);
        instructionTV = (TextView) view.findViewById(R.id.instruction_TV);


        if (!onlineRegister || emailPref.equals("") || userPref.equals("")) {

            restoreLayout.setVisibility(View.GONE);
            instructionTV.setVisibility(View.VISIBLE);

        } else {

            restoreLayout.setVisibility(View.VISIBLE);
            instructionTV.setVisibility(View.GONE);
        }


        instructionTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                builder.setTitle("Login or Register first")
                        .setMessage("\nYou did not logged in as online user. May be you did not register yet. If you " +
                                "did not register you may register first. Or you can simply login if you already register. " +
                                "\nDo you want to go login or register page?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(getContext(), RegisterActivity.class));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        });



        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){

                    index = 0;
                    recyclerView.setAdapter(null);



                }else if (position == 1){

                    index = 1;
                    databaseReference = FirebaseDatabase.getInstance().getReference("User/" + userPref
                            + "/Account Data/BANK");



                    prepareForFireBase("BANK");



                }else if (position == 2){

                    index = 2;
                    databaseReference = FirebaseDatabase.getInstance().getReference("User/" + userPref
                            + "/Account Data/CARD");

                    prepareForFireBaseCard("CARD");

                }else if (position == 3){

                    index = 3;
                    databaseReference = FirebaseDatabase.getInstance().getReference("User/" + userPref
                            + "/Account Data/CRYPTOCURRENCY");

                    prepareForFireBase("CRYPTOCURRENCY");

                }else if (position == 4){

                    index = 4;
                    databaseReference = FirebaseDatabase.getInstance().getReference("User/" + userPref
                            + "/Account Data/EMAIL");


                    prepareForFireBase("EMAIL");

                }else if (position == 5){

                    index = 5;
                    databaseReference = FirebaseDatabase.getInstance().getReference("User/" + userPref
                            + "/Account Data/SOCIAL");

                    prepareForFireBase("SOCIAL");

                }else if (position == 6){

                    index = 6;
                    databaseReference = FirebaseDatabase.getInstance().getReference("User/" + userPref
                            + "/Account Data/OTHERS");


                    prepareForFireBase("OTHERS");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        passwordList = new ArrayList<>();
        cardModelList = new ArrayList<>();



    }


    @Override
    public void onResume() {
        super.onResume();




    }


    public void prepareForFireBase (final String type){


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Searching data\nPlease wait.....");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                passwordList.clear();



                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    FirebaseModel firebaseModel = dataSnapshot1.getValue(FirebaseModel.class);

                    ModelPassword modelPassword = new ModelPassword(firebaseModel.getID(),
                            firebaseModel.getTitle(),
                            firebaseModel.getPassword(),
                            firebaseModel.getWebsite(),
                            firebaseModel.getHeader().charAt(0),
                            firebaseModel.getType(),
                            firebaseModel.getEmail());

                    passwordList.add(modelPassword);
                }

                restoreAdapter = new RestoreAdapter(getContext(), passwordList, recyclerView, type);
                dbHelper= new DBHelper(getContext(), "bankTable");




                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 0, true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(restoreAdapter);
                restoreAdapter.notifyDataSetChanged();

                progressDialog.dismiss();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }




    public void prepareForFireBaseCard (final String type){


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Searching data\nPlease wait.....");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                cardModelList.clear();



                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    FirebaseCardModel firebaseCardModel = dataSnapshot1.getValue(FirebaseCardModel.class);

                   CardModel cardMode = new CardModel(firebaseCardModel.getId(),
                           firebaseCardModel.getBankName(),
                           firebaseCardModel.getNameOnCard(),
                           firebaseCardModel.getCardNumber(),
                           firebaseCardModel.getPin(),
                           firebaseCardModel.getCcv(),
                           firebaseCardModel.getValidityMonth(),
                           firebaseCardModel.getValidityYear(),
                           firebaseCardModel.getHeader().charAt(0),
                           firebaseCardModel.getType());

                    cardModelList.add(cardMode);
                }

                restoreCardAdapter = new RestoreCardAdapter(getContext(), cardModelList, recyclerView);

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 0, true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(restoreCardAdapter);
                restoreCardAdapter.notifyDataSetChanged();

                progressDialog.dismiss();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
