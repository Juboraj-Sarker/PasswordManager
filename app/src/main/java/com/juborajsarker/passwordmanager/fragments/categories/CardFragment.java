package com.juborajsarker.passwordmanager.fragments.categories;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.adapters.CardAdapter;
import com.juborajsarker.passwordmanager.database.CardDatabase;
import com.juborajsarker.passwordmanager.model.CardModel;
import com.juborajsarker.passwordmanager.model.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class CardFragment extends Fragment {

    View view;

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

    String bankName, nameOnCard, cardNumber, pin, ccv, month, year;


    public CardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      view =  inflater.inflate(R.layout.fragment_card, container, false);


        mustExecute();

        return view;
    }

    private void mustExecute() {

        sharedPreferences = getActivity().getSharedPreferences("cryptoValue", MODE_PRIVATE);

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

                        counter ++;
                        if (counter % 2 == 0){

                            cardPinET.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            pinVisibilityIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility));


                        }else {

                            cardPinET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            pinVisibilityIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off));
                        }
                    }
                });



                ccvVisibilityIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ccCounter ++;
                        if (ccCounter % 2 == 0){

                            cardCcvET.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            ccvVisibilityIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility));


                        }else {

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

                            prepare();

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
    }

}
