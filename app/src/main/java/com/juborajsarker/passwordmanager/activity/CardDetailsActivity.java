package com.juborajsarker.passwordmanager.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.database.CardDatabase;
import com.juborajsarker.passwordmanager.model.CardModel;

import java.util.ArrayList;
import java.util.List;

public class CardDetailsActivity extends AppCompatActivity {

    TextView cardNumberTV, nameOnCardTV, cardPINTV, cardCCVTV, cardValidTV, cardBankNameTV;
    ImageView copyCardNumberIV, copyNameIV, copyCardPinIV, copyCardCcvIV, cardValidIV, copyBankNameIV;
    Button deleteBTN, editBTN;
    LinearLayout buttonLayout;
    int flag, position;

    List<CardModel> cardModelList ;
    CardDatabase cardDatabase;
    CardModel cardModel;

    String cardNumber, nameOnCard, pin, ccv, year, month, bankName;

    String type = "CARD";
    String prefKey = "cardId";

    AlertDialog alertDialog;

    int idValues = 0;
    int counter = 0;
    int ccCounter = 0;

    public SharedPreferences sharedPreferences;
    boolean onlineRegister;
    String emailPref, userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }



        Intent intent = getIntent();

        String cardNumber = intent.getStringExtra("cardNumber");
        String nameOnCard = intent.getStringExtra("nameOnCard");
        String pin = intent.getStringExtra("pin");
        String ccv = intent.getStringExtra("ccv");
        String month = intent.getStringExtra("month");
        String year = intent.getStringExtra("year");
        String bankName = intent.getStringExtra("bankName");
        flag = intent.getIntExtra("flag", 0);
        position = intent.getIntExtra("position", 0);


        init();
        setAction();


        cardNumberTV.setText(cardNumber);
        nameOnCardTV.setText(nameOnCard);
        cardPINTV.setText(pin);
        cardCCVTV.setText(ccv);
        cardValidTV.setText(month + " / " + year + " (mm/yy)");
        cardBankNameTV.setText(bankName);



        cardModelList = new ArrayList<CardModel>();
        cardDatabase = new CardDatabase(this);



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

        cardNumberTV = (TextView) findViewById(R.id.details_card_number);
        nameOnCardTV = (TextView) findViewById(R.id.details_name_on_card);
        cardPINTV = (TextView) findViewById(R.id.details_card_pin);
        cardCCVTV = (TextView) findViewById(R.id.details_card_ccv);
        cardValidTV = (TextView) findViewById(R.id.details_card_valid);
        cardBankNameTV = (TextView) findViewById(R.id.details_bank_name);

        copyCardNumberIV = (ImageView) findViewById(R.id.details_copy_card_number_IV);
        copyNameIV = (ImageView) findViewById(R.id.details_copy_name_IV);
        copyCardPinIV = (ImageView) findViewById(R.id.details_copy_pin_IV);
        copyCardCcvIV = (ImageView) findViewById(R.id.details_copy_ccv_IV);
        cardValidIV = (ImageView) findViewById(R.id.details_card_valid_IV);
        copyBankNameIV = (ImageView) findViewById(R.id.details_copy_bank_name_IV);

        buttonLayout = (LinearLayout) findViewById(R.id.button_LAYOUT);

        if (flag == 1){

            buttonLayout.setVisibility(View.GONE);
        }

        deleteBTN = (Button) findViewById(R.id.view_delete_BTN);
        editBTN = (Button) findViewById(R.id.view_edit_BTN);


    }


    private void setAction() {

        copyCardNumberIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String cardNumber = cardNumberTV.getText().toString();

                if (cardNumber.equals("")){

                    Toast.makeText(CardDetailsActivity.this, "Error!!!\nNothing to copy.", Toast.LENGTH_SHORT).show();

                }else {

                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("cardNumber", cardNumber);
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(CardDetailsActivity.this, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        copyNameIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardNumber = nameOnCardTV.getText().toString();

                if (cardNumber.equals("")){

                    Toast.makeText(CardDetailsActivity.this, "Error!!!\nNothing to copy.", Toast.LENGTH_SHORT).show();

                }else {

                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("name", cardNumber);
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(CardDetailsActivity.this, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });



        copyCardPinIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardNumber = cardPINTV.getText().toString();

                if (cardNumber.equals("")){

                    Toast.makeText(CardDetailsActivity.this, "Error!!!\nNothing to copy.", Toast.LENGTH_SHORT).show();

                }else {

                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("cardPin", cardNumber);
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(CardDetailsActivity.this, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });



        copyCardCcvIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String cardNumber = cardCCVTV.getText().toString();

                if (cardNumber.equals("")){

                    Toast.makeText(CardDetailsActivity.this, "Error!!!\nNothing to copy.", Toast.LENGTH_SHORT).show();

                }else {

                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("cardCCV", cardNumber);
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(CardDetailsActivity.this, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                }


            }
        });



        cardValidIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });




        copyBankNameIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String cardNumber = cardBankNameTV.getText().toString();

                if (cardNumber.equals("")){

                    Toast.makeText(CardDetailsActivity.this, "Error!!!\nNothing to copy.", Toast.LENGTH_SHORT).show();

                }else {

                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("bankName", cardNumber);
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(CardDetailsActivity.this, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cardModelList = cardDatabase.getAllData();
                cardModel = cardModelList.get(position);
                showDialog();




            }
        });



        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                deleteData();

            }
        });





    }


    private void deleteData() {


        cardModelList = cardDatabase.getAllData();
        CardModel cardModel = cardModelList.get(position);
        cardDatabase.deleteCard(cardModel);

        Toast.makeText(this, "Deleted Successfully !!!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }


    public void showDialog(){


        LayoutInflater layoutInflater = LayoutInflater.from(CardDetailsActivity.this);
        View dialogView = layoutInflater.inflate(R.layout.layout_custom_dialog_card, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(CardDetailsActivity.this);
        builder.setView(dialogView);

        final TextView dialogTitle = (TextView) dialogView.findViewById(R.id.dialog_title);
        dialogTitle.setText("Update CARD account");

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



        cardNumberET.setText(cardModel.getCardNumber());
        cardNameOnET.setText(cardModel.getNameOnCard());
        cardPinET.setText(cardModel.getPin());
        cardCcvET.setText(cardModel.getCcv());
        cardMonthET.setText(cardModel.getValidityMonth());
        cardYearET.setText(cardModel.getValidityYear());
        cardBankNameET.setText(cardModel.getBankName());




        pinVisibilityIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter++;
                if (counter % 2 == 0) {

                    cardPinET.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pinVisibilityIV.setImageDrawable(ContextCompat.getDrawable(CardDetailsActivity.this, R.drawable.ic_visibility));


                } else {

                    cardPinET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pinVisibilityIV.setImageDrawable(ContextCompat.getDrawable(CardDetailsActivity.this, R.drawable.ic_visibility_off));
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
                    ccvVisibilityIV.setImageDrawable(ContextCompat.getDrawable(CardDetailsActivity.this, R.drawable.ic_visibility));


                } else {

                    cardCcvET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ccvVisibilityIV.setImageDrawable(ContextCompat.getDrawable(CardDetailsActivity.this, R.drawable.ic_visibility_off));
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
                idValues = cardModel.getId();





                if (cardNumber.equals("")
                        || nameOnCard.equals("")
                        || pin.equals("")
                        || ccv.equals("")
                        || year.equals("")
                        || month.equals("")) {

                    Toast.makeText(CardDetailsActivity.this, "Please input valid", Toast.LENGTH_SHORT).show();
                } else {

                    if (bankName.equals("")) {

                        bankName = "null";
                    }

                    prepare();

                    cardNumberTV.setText(cardNumber);
                    nameOnCardTV.setText(nameOnCard);
                    cardPINTV.setText(pin);
                    cardCCVTV.setText(ccv);
                    cardValidTV.setText(month + " / " + year + " (mm/yy)" );
                    cardBankNameTV.setText(bankName);

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


    private void prepare() {



        CardModel cardModel = new CardModel(idValues, bankName, nameOnCard,
                cardNumber, pin, ccv, month, year, bankName.toUpperCase().charAt(0), type);
        cardDatabase.updateCard(cardModel);
        alertDialog.dismiss();







    }

    }

