package com.juborajsarker.passwordmanager.fragments.categories;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.activity.RegisterActivity;
import com.juborajsarker.passwordmanager.adapters.CardAdapter;
import com.juborajsarker.passwordmanager.database.CardDatabase;
import com.juborajsarker.passwordmanager.java_class.UserCountry;
import com.juborajsarker.passwordmanager.model.CardModel;
import com.juborajsarker.passwordmanager.model.FirebaseCardModel;
import com.juborajsarker.passwordmanager.model.GridSpacingItemDecoration;
import com.juborajsarker.passwordmanager.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class CardFragment extends Fragment {

    View view;

    TextView backupTV;

    RecyclerView recyclerView;
    FloatingActionButton fab;
    CardAdapter adapter;
    List<CardModel> cardModelList;
    CardDatabase cardDatabase;

    String type = "CARD";
    String prefKey = "cardId";

    AlertDialog alertDialog;

    int idValues = 0;
    int counter = 0;
    int ccCounter = 0;

    public SharedPreferences sharedPreferences;
    boolean onlineRegister;
    String emailPref, userPref;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;

    String bankName, nameOnCard, cardNumber, pin, ccv, month, year;


    public CardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_card, container, false);


        sharedPreferences = getActivity().getSharedPreferences("registerStatus", MODE_PRIVATE);
        onlineRegister = sharedPreferences.getBoolean("onlineRegisterStatus", false);
        emailPref = sharedPreferences.getString("email", "");
        userPref = sharedPreferences.getString("uid", "");


        mustExecute();

        return view;
    }

    private void mustExecute() {

        sharedPreferences = getActivity().getSharedPreferences("cryptoValue", MODE_PRIVATE);

        backupTV = (TextView) view.findViewById(R.id.backupTV);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View dialogView = layoutInflater.inflate(R.layout.layout_custom_dialog_card, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);

                final TextView dialogTitle = (TextView) dialogView.findViewById(R.id.dialog_title);
                dialogTitle.setText("Add new CARD account");

                final EditText cardNumberET = (EditText) dialogView.findViewById(R.id.dialog_card_number);
                final EditText cardNameOnET = (EditText) dialogView.findViewById(R.id.dialog_card_name);
                final EditText cardPinET = (EditText) dialogView.findViewById(R.id.dialog_card_pin);
                final EditText cardCcvET = (EditText) dialogView.findViewById(R.id.dialog_card_ccv);
                final EditText cardMonthET = (EditText) dialogView.findViewById(R.id.dialog_card_valid_month);
                final EditText cardYearET = (EditText) dialogView.findViewById(R.id.dialog_card_valid_year);
                final EditText cardBankNameET = (EditText) dialogView.findViewById(R.id.dialog_card_bank_name);

                final Button okBTN = (Button) dialogView.findViewById(R.id.dialog_button_ok);
                final Button cancelBTN = (Button) dialogView.findViewById(R.id.dialog_button_cancel);


                final ImageView pinVisibilityIV = (ImageView) dialogView.findViewById(R.id.dialog_pin_visibility_IV);
                final ImageView ccvVisibilityIV = (ImageView) dialogView.findViewById(R.id.dialog_ccv_visibility_IV);

                pinVisibilityIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        counter++;
                        if (counter % 2 == 0) {

                            cardPinET.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            pinVisibilityIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility));


                        } else {

                            cardPinET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            pinVisibilityIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off));
                        }
                    }
                });


                ccvVisibilityIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ccCounter++;
                        if (ccCounter % 2 == 0) {

                            cardCcvET.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            ccvVisibilityIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility));


                        } else {

                            cardCcvET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            ccvVisibilityIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off));
                        }
                    }
                });


                okBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        cardNumber = cardNumberET.getText().toString();
                        nameOnCard = cardNameOnET.getText().toString();
                        pin = cardPinET.getText().toString();
                        ccv = cardCcvET.getText().toString();
                        year = cardYearET.getText().toString();
                        month = cardMonthET.getText().toString();
                        bankName = cardBankNameET.getText().toString();

                        if (cardNumber.equals("")
                                || nameOnCard.equals("")
                                || pin.equals("")
                                || ccv.equals("")
                                || year.equals("")
                                || month.equals("")){

                            Toast.makeText(getContext(), "Please input valid", Toast.LENGTH_SHORT).show();
                        }

                        else {

                            if (bankName.equals("")){

                                bankName = "null";
                            }

                            if (cardNumber.length()<16 ||
                                    pin.length()<4 ||
                                    ccv.length()<3 ||
                                    year.length()<4 ||
                                    month.length()<2)

                            {

                                Toast.makeText(getContext(), "Invalid Input. Please check details", Toast.LENGTH_SHORT).show();



                                if (cardNumber.length()<16){

                                    cardNumberET.setError("Card Number must be 16 digit");
                                }

                                if (pin.length()<4){

                                    cardPinET.setError("PIN Number must be 4 digit");
                                }

                                if (ccv.length()<3){

                                    cardPinET.setError("CCV Number must be 3 digit");
                                }

                                if (year.length()<4){

                                    cardYearET.setError("YEAR must be 4 digit");
                                }

                                if (month.length()<2){

                                    cardMonthET.setError("MONTH must be 2 digit");
                                }

                                if (Integer.parseInt(year) > 2030){

                                    cardYearET.setError("Enter a valid YEAR");
                                }

                                if (Integer.parseInt(month) > 12){

                                    cardMonthET.setError("Enter a valid MONTH\nCannot greater than 12");
                                }

                                if (nameOnCard.charAt(0) == ' '){

                                    cardNameOnET.setError("Name cannot start with space");
                                }

                                if (bankName.charAt(0) == ' '){

                                    cardBankNameET.setError("Bank Name cannot start with space");
                                }


                            }else {


                                if (Integer.parseInt(year) > 2030 ||
                                        Integer.parseInt(month) > 12 ||
                                        nameOnCard.charAt(0) == ' '||
                                        bankName.charAt(0) == ' '){

                                    if (Integer.parseInt(year) > 2030){

                                        cardYearET.setError("Enter a valid YEAR");
                                    }

                                    if (Integer.parseInt(month) > 12){

                                        cardMonthET.setError("Enter a valid MONTH\nCannot greater than 12");
                                    }

                                    if (nameOnCard.charAt(0) == ' '){

                                        cardNameOnET.setError("Name cannot start with space");
                                    }

                                    if (bankName.charAt(0) == ' '){

                                        cardBankNameET.setError("Bank Name cannot start with space");
                                    }

                                }else {

                                    prepare();
                                }




                            }


                        }

                    }
                });


                cancelBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();

                    }
                });

                builder.setCancelable(false);


                alertDialog = builder.create();
                alertDialog.show();


            }


        });


        cardDatabase = new CardDatabase(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        cardModelList = new ArrayList<>();
        adapter = new CardAdapter(getContext(), cardModelList, cardDatabase, recyclerView);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    private void prepare() {


        idValues = sharedPreferences.getInt(prefKey, 0);
        idValues++;

        CardModel cardModel = new CardModel(idValues, bankName, nameOnCard,
                cardNumber, pin, ccv, month, year, bankName.toUpperCase().charAt(0), type);

        cardDatabase.insertData(cardModel);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        cardModelList = cardDatabase.getAllData();
        adapter = new CardAdapter(getContext(), cardModelList, cardDatabase, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(prefKey, idValues);
        editor.apply();
        adapter.notifyDataSetChanged();

        alertDialog.dismiss();

        setBackupTV();


    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public void onResume() {
        super.onResume();

        cardDatabase = new CardDatabase(getContext());
        cardModelList = cardDatabase.getAllData();
        adapter = new CardAdapter(getContext(), cardModelList, cardDatabase, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        setBackupTV();
    }


    public void setBackupTV() {

        if (cardModelList.size() > 0) {

            backupTV.setVisibility(View.VISIBLE);

            backupTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        prepareForFirebase();

                    } catch (Exception e) {

                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {

            backupTV.setVisibility(View.GONE);
        }

    }


    public void prepareForFirebase() {


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait ......");
        progressDialog.show();

        if (onlineRegister &&
                !emailPref.equals("")
                && !userPref.equals("")) {


            cardModelList = cardDatabase.getAllData();

            for (int i = 0; i < cardModelList.size(); i++) {


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


                int keyValue = 0;
                int temp = 0;

                for (int j=0; j<cardModel.getCardNumber().length(); j++){

                    temp = cardModel.getCardNumber().charAt(j);
                    keyValue = keyValue + temp;
                }


                for (int j=0; j<cardModel.getNameOnCard().length(); j++){

                    temp = cardModel.getNameOnCard().charAt(j);
                    keyValue = keyValue + temp;
                }

                for (int j=0; j<cardModel.getPin().length(); j++){

                    temp = cardModel.getPin().charAt(j);
                    keyValue = keyValue + temp;
                }


                for (int j=0; j<cardModel.getCcv().length(); j++){

                    temp = cardModel.getCcv().charAt(j);
                    keyValue = keyValue + temp;
                }

                for (int j=0; j<cardModel.getValidityMonth().length(); j++){

                    temp = cardModel.getValidityMonth().charAt(j);
                    keyValue = keyValue + temp;
                }


                for (int j=0; j<cardModel.getValidityYear().length(); j++){

                    temp = cardModel.getValidityYear().charAt(j);
                    keyValue = keyValue + temp;
                }

                for (int j=0; j<cardModel.getBankName().length(); j++){

                    temp = cardModel.getBankName().charAt(j);
                    keyValue = keyValue + temp;
                }





                String key = String.valueOf(keyValue);

                databaseReference.child(key).setValue(firebaseCardModel);


                databaseReference3 = FirebaseDatabase.getInstance().getReference("Backup/" + UserCountry.getUserCountry(getContext())
                        + "/" + userPref + "/" + firebaseCardModel.getType());
                databaseReference3.child(key).setValue(firebaseCardModel);

            }


            databaseReference2 = FirebaseDatabase.getInstance().getReference("User/" + userPref
                    + "/Other Data");


            String manufacturer = Build.MANUFACTURER;
            String brand = Build.BRAND;
            String modelName = Build.MODEL;
            String deviceID = Build.SERIAL;


            int api_level = Build.VERSION.SDK_INT;
            String os_version = Build.VERSION.RELEASE;
            String versionName = getVersionName(api_level);


            UserInfo userInfo = new UserInfo(emailPref, userPref, manufacturer, brand, modelName, deviceID,
                    os_version, api_level, versionName);

            databaseReference2.setValue(userInfo);

            Toast.makeText(getContext(), "Successfully added on cloud !!!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();


        } else {

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


    public String getVersionName(int os_version) {


        if (os_version == Build.VERSION_CODES.JELLY_BEAN || os_version == Build.VERSION_CODES.JELLY_BEAN_MR1 || os_version == Build.VERSION_CODES.JELLY_BEAN_MR2) {

            return "JELLY BEAN";


        } else if (os_version == Build.VERSION_CODES.KITKAT || os_version == Build.VERSION_CODES.KITKAT_WATCH) {

            return "KITKAT";


        } else if (os_version == Build.VERSION_CODES.LOLLIPOP || os_version == Build.VERSION_CODES.LOLLIPOP_MR1) {

            return "LOLLIPOP";


        } else if (os_version == Build.VERSION_CODES.M) {

            return "MARSHMALLOW";


        } else if (os_version == Build.VERSION_CODES.N || os_version == Build.VERSION_CODES.N_MR1) {

            return "NOUGAT";


        } else if (os_version == Build.VERSION_CODES.O) {

            return "OREO";
        } else {

            return "UNKNOWN";
        }
    }

}
