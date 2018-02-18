package com.juborajsarker.passwordmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.juborajsarker.passwordmanager.R;

public class CardDetailsActivity extends AppCompatActivity {

    TextView cardNumberTV, nameOnCardTV, cardPINTV, cardCCVTV, cardValidTV, cardBankNameTV;
    ImageView copyCardNumberIV, copyNameIV, copyCardPinIV, copyCardCcvIV, cardValidIV, copyBankNameIV;
    Button deleteBTN, editBTN;

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


        init();
        setAction();


        cardNumberTV.setText(cardNumber);
        nameOnCardTV.setText(nameOnCard);
        cardPINTV.setText(pin);
        cardCCVTV.setText(ccv);
        cardValidTV.setText(month + " / " + year + " (mm/yy)");
        cardBankNameTV.setText(bankName);






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

        deleteBTN = (Button) findViewById(R.id.view_delete_BTN);
        editBTN = (Button) findViewById(R.id.view_edit_BTN);


    }


    private void setAction() {

        copyCardNumberIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        copyNameIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        copyCardPinIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        copyCardCcvIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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


            }
        });




        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });



        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });





    }
}
