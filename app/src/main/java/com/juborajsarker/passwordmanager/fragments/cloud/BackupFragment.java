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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.activity.RegisterActivity;
import com.juborajsarker.passwordmanager.adapters.CardAdapter;
import com.juborajsarker.passwordmanager.adapters.CustomAdapter;
import com.juborajsarker.passwordmanager.database.CardDatabase;
import com.juborajsarker.passwordmanager.database.DBHelper;
import com.juborajsarker.passwordmanager.model.CardModel;
import com.juborajsarker.passwordmanager.model.FirebaseCardModel;
import com.juborajsarker.passwordmanager.model.FirebaseModel;
import com.juborajsarker.passwordmanager.model.GridSpacingItemDecoration;
import com.juborajsarker.passwordmanager.model.ModelPassword;
import com.juborajsarker.passwordmanager.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class BackupFragment extends Fragment {

    CardView backupCV;
    Spinner backupSP;
    RecyclerView recyclerView;


    private CustomAdapter adapter;
    private List<ModelPassword> passwordList;
    private DBHelper dbHelper;


    CardAdapter cardAdapter;
    List<CardModel> cardModelList;
    CardDatabase cardDatabase;

    private String TABLE_NAME = "";


    public SharedPreferences sharedPreferences;
    boolean onlineRegister;
    String emailPref, userPref;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    int index;

    View view;


    public BackupFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_backup, container, false);


        sharedPreferences = getActivity().getSharedPreferences("registerStatus", MODE_PRIVATE);
        onlineRegister = sharedPreferences.getBoolean("onlineRegisterStatus", false);
        emailPref = sharedPreferences.getString("email","");
        userPref = sharedPreferences.getString("uid","");

        init();




        return view;
    }

    private void init() {

        backupCV = (CardView) view.findViewById(R.id.backup_cardView);
        backupSP = (Spinner) view.findViewById(R.id.backupSpinner);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        backupSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){

                    backupCV.setVisibility(View.GONE);
                    recyclerView.setAdapter(null);
                    index = 0;

                }else if (position == 1){

                    TABLE_NAME = "bankTable";
                    showDataOnRecyclerView(TABLE_NAME);
                    setBackupCvVisibility();
                    index = 1;





                }else if (position == 2){

                    cardDatabase = new CardDatabase(getContext());
                    cardModelList = new ArrayList<>();
                    cardModelList = cardDatabase.getAllData();
                    cardAdapter = new CardAdapter(getContext(), cardModelList, cardDatabase, recyclerView);


                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 0, true));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(cardAdapter);
                    cardAdapter.notifyDataSetChanged();

                    setBackupCvVisibilityForCard();

                    index = 2;

                }else if (position == 3){

                    TABLE_NAME = "cryptoTable";
                    showDataOnRecyclerView(TABLE_NAME);
                    setBackupCvVisibility();

                    index = 3;

                }else if (position == 4){

                    TABLE_NAME = "emailTable";
                    showDataOnRecyclerView(TABLE_NAME);
                    setBackupCvVisibility();

                    index = 4;

                }else if (position == 5){

                    TABLE_NAME = "socialTable";
                    showDataOnRecyclerView(TABLE_NAME);
                    setBackupCvVisibility();

                    index = 5;

                }else if (position == 6){

                    TABLE_NAME = "otherTable";
                    showDataOnRecyclerView(TABLE_NAME);
                    setBackupCvVisibility();

                    index = 6;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        backupCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index = backupSP.getSelectedItemPosition();
                switch (index){

                    case 0:{


                        break;

                    }case 1:{

                        prepareForFirebase("bankTable");

                        break;

                    }case 2:{

                        prepareForFirebaseCard();
                        break;

                    }case 3:{

                        prepareForFirebase("cryptoTable");
                        break;

                    }case 4:{

                        prepareForFirebase("emailTable");
                        break;

                    }case 5:{

                        prepareForFirebase("socialTable");
                        break;

                    }case 6:{

                        prepareForFirebase("otherTable");
                        break;

                    }
                }
            }
        });
    }



    public void showDataOnRecyclerView(String tableName){


        dbHelper = new DBHelper(getContext(), tableName);
        dbHelper.setTABLE_NAME(tableName);
        passwordList = new ArrayList<>();
        passwordList = dbHelper.getAllData(tableName);
        adapter = new CustomAdapter(getContext(), passwordList, dbHelper, recyclerView, tableName);



        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 0, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void setBackupCvVisibility(){


        if (passwordList.size()>0){

            backupCV.setVisibility(View.VISIBLE);

        }else {

            backupCV.setVisibility(View.GONE);

        }
    }


    public void setBackupCvVisibilityForCard(){


        if (cardModelList.size()>0){

            backupCV.setVisibility(View.VISIBLE);



        }else {

            backupCV.setVisibility(View.GONE);

        }
    }


    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences = getActivity().getSharedPreferences("registerStatus", MODE_PRIVATE);
        onlineRegister = sharedPreferences.getBoolean("onlineRegisterStatus", false);
        emailPref = sharedPreferences.getString("email","");
        userPref = sharedPreferences.getString("uid","");

    }


    public String getVersionName(int os_version){


        if (os_version == Build.VERSION_CODES.JELLY_BEAN || os_version == Build.VERSION_CODES.JELLY_BEAN_MR1 || os_version == Build.VERSION_CODES.JELLY_BEAN_MR2){

            return "JELLY BEAN";


        }else if (os_version == Build.VERSION_CODES.KITKAT || os_version == Build.VERSION_CODES.KITKAT_WATCH){

            return "KITKAT";


        }else if (os_version == Build.VERSION_CODES.LOLLIPOP || os_version == Build.VERSION_CODES.LOLLIPOP_MR1){

            return "LOLLIPOP";


        }else if (os_version == Build.VERSION_CODES.M ){

            return "MARSHMALLOW";


        }else if (os_version == Build.VERSION_CODES.N || os_version == Build.VERSION_CODES.N_MR1){

            return "NOUGAT";


        }else if (os_version == Build.VERSION_CODES.O ){

            return "OREO";
        }

        else {

            return "UNKNOWN";
        }
    }









    public void prepareForFirebase(String tableName){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait ......");
        progressDialog.show();

        if (onlineRegister &&
                ! emailPref.equals("")
                && ! userPref.equals(""))
        {

            dbHelper = new DBHelper(getContext(), tableName);
            passwordList = dbHelper.getAllData(tableName);

            for (int i = 0; i < passwordList.size(); i++){


                ModelPassword modelPasswords = passwordList.get(i);
                databaseReference = FirebaseDatabase.getInstance().getReference("User/" + userPref
                        + "/Account Data/" + modelPasswords.getType());

                FirebaseModel model = new FirebaseModel(modelPasswords.getId(),
                        modelPasswords.getTitle(),
                        modelPasswords.getPassword(),
                        modelPasswords.getWebsite(),
                        String.valueOf(modelPasswords.getHeader()),
                        modelPasswords.getType(),
                        modelPasswords.getEmail());

                
                String key = databaseReference.push().getKey();
                databaseReference.child(key).setValue(model);

            }


            databaseReference2 = FirebaseDatabase.getInstance().getReference("User/" + userPref
                    + "/Other Data");


            String manufacturer = Build.MANUFACTURER;
            String brand = Build.BRAND;
            String modelName = Build.MODEL;
            String deviceID = Build.SERIAL;


            int api_level = Build.VERSION.SDK_INT;
            String  os_version  = Build.VERSION.RELEASE;
            String versionName = getVersionName(api_level);



            UserInfo userInfo = new UserInfo(emailPref, userPref, manufacturer, brand, modelName, deviceID,
                    os_version, api_level, versionName);

            databaseReference2.setValue(userInfo);

            Toast.makeText(getContext(), "Successfully added on cloud !!!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();


        }else {

            progressDialog.dismiss();
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

    }





    public void prepareForFirebaseCard(){

        cardModelList = cardDatabase.getAllData();

        for (int i=0; i< cardModelList.size(); i++){


            CardModel cardModel = cardModelList.get(i);

            databaseReference = FirebaseDatabase.getInstance().getReference("User/" + userPref
                    + "/Account Data/" + cardModel.getType());

            FirebaseCardModel firebaseCardModel = new FirebaseCardModel(cardModel.getId(),
                    cardModel.getBankName(),
                    cardModel.getNameOnCard(),
                    cardModel.getCardNumber(),
                    cardModel.getPin(),
                    cardModel.getCcv(),
                    cardModel.getValidityMonth(),
                    cardModel.getValidityYear(),
                    String.valueOf(cardModel.getHeader()),
                    cardModel.getType());

            String key = databaseReference.push().getKey();
            databaseReference.child(key).setValue(firebaseCardModel);

        }


        databaseReference2 = FirebaseDatabase.getInstance().getReference("User/" + userPref
                + "/Other Data");


        String manufacturer = Build.MANUFACTURER;
        String brand = Build.BRAND;
        String modelName = Build.MODEL;
        String deviceID = Build.SERIAL;


        int api_level = Build.VERSION.SDK_INT;
        String  os_version  = Build.VERSION.RELEASE;
        String versionName = getVersionName(api_level);



        UserInfo userInfo = new UserInfo(emailPref, userPref, manufacturer, brand, modelName, deviceID,
                os_version, api_level, versionName);

        databaseReference2.setValue(userInfo);

        Toast.makeText(getContext(), "Successfully added on cloud !!!", Toast.LENGTH_SHORT).show();


    }
}
