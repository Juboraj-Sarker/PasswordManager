package com.juborajsarker.passwordmanager.adapters;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.activity.CardDetailsActivity;
import com.juborajsarker.passwordmanager.database.CardDatabase;
import com.juborajsarker.passwordmanager.model.CardModel;

import java.util.List;

/**
 * Created by jubor on 2/16/2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {


    private Context context;
    private List<CardModel> cardModelList;
    CardDatabase cardDatabase;
    RecyclerView recyclerView;

    public SharedPreferences sharedPreferences;

    int idValues;

    String cardNumber, nameOnCard, pin, ccv, year, month, bankName, type;
    CardAdapter adapter;
    CardModel cardModel;

    int counter = 0;
    int ccCounter = 0;

    AlertDialog alertDialog;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;

        public TextView cvCardNumberTV;
        public TextView cvNameOnCardTV;
        public TextView cvBankNameTV;
        public TextView cvHeaderTV;
        public ImageView optionIV;


        public MyViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.custom_card_view);

            cvCardNumberTV = (TextView) view.findViewById(R.id.custom_card_number_TV);
            cvNameOnCardTV = (TextView) view.findViewById(R.id.custom_card_name_on_TV);
            cvBankNameTV = (TextView) view.findViewById(R.id.custom_card_bank_name_TV);
            cvHeaderTV = (TextView) view.findViewById(R.id.custom_card_header_TV);

            optionIV = (ImageView) view.findViewById(R.id.custom_card_option_IV);
        }
    }


    public CardAdapter(Context context, List<CardModel> cardModelList,
                       CardDatabase cardDatabase, RecyclerView recyclerView) {

        this.context = context;
        this.cardModelList = cardModelList;
        this.cardDatabase = cardDatabase;
        this.recyclerView = recyclerView;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_card_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final CardModel cardModel = cardModelList.get(position);

        String cardNumber = cardModel.getCardNumber();
        String nameOnCard = cardModel.getNameOnCard();
        String bankName = cardModel.getBankName();
        char header = cardModel.getHeader();

        holder.cvCardNumberTV.setText(cardNumber);
        holder.cvNameOnCardTV.setText(nameOnCard);
        holder.cvBankNameTV.setText(bankName);
        holder.cvHeaderTV.setText(String.valueOf(header));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardModelList = cardDatabase.getAllData();
                CardModel cardModel = cardModelList.get(position);

                Intent intent = new Intent(context, CardDetailsActivity.class);
                intent.putExtra("cardNumber", cardModel.getCardNumber());
                intent.putExtra("nameOnCard", cardModel.getNameOnCard());
                intent.putExtra("pin", cardModel.getPin());
                intent.putExtra("ccv", cardModel.getCcv());
                intent.putExtra("month", cardModel.getValidityMonth());
                intent.putExtra("year", cardModel.getValidityYear());
                intent.putExtra("bankName", cardModel.getBankName());

                CardAdapter adapter = new CardAdapter(context, cardModelList, cardDatabase, recyclerView);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                context.startActivity(intent);

            }
        });


        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                showPopupMenu(holder.cardView, position);

                return false;
            }
        });

        holder.optionIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupMenu(holder.optionIV, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cardModelList.size();
    }


    public void showPopupMenu(View view, int position) {


        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.card_custom_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new CardAdapter.MyMenuItemClickListener(position));
        popupMenu.show();

    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {


        int position;

        public MyMenuItemClickListener(int position) {

            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {


                case R.id.card_action_copy_card_number: {


                    cardModelList = cardDatabase.getAllData();

                    CardModel cardModel = cardModelList.get(position);

                    String cardNumber = cardModel.getCardNumber();

                    if (cardNumber.equals("")){

                        Toast.makeText(context, "Error !!! No card found !!!", Toast.LENGTH_SHORT).show();

                    }else {

                        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("cardNumber", cardNumber);
                        clipboardManager.setPrimaryClip(clipData);

                        Toast.makeText(context, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                    }


                    break;

                }


                case R.id.card_action_copy_card_pin: {


                    cardModelList = cardDatabase.getAllData();

                    CardModel cardModel = cardModelList.get(position);

                    String cardPin = cardModel.getPin();

                    if (cardPin.equals("")){

                        Toast.makeText(context, "Error !!! No card found !!!", Toast.LENGTH_SHORT).show();

                    }else {

                        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("cardNumber", cardPin);
                        clipboardManager.setPrimaryClip(clipData);

                        Toast.makeText(context, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                    }


                    break;



                }

                case R.id.card_action_copy_card_ccv: {


                    cardModelList = cardDatabase.getAllData();

                    CardModel cardModel = cardModelList.get(position);

                    String cardCCv = cardModel.getCcv();

                    if (cardCCv.equals("")){

                        Toast.makeText(context, "Error !!! No card found !!!", Toast.LENGTH_SHORT).show();

                    }else {

                        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("cardNumber", cardCCv);
                        clipboardManager.setPrimaryClip(clipData);

                        Toast.makeText(context, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                    }


                    break;

                }


                case R.id.card_action_view: {


                    cardModelList = cardDatabase.getAllData();
                    CardModel cardModel = cardModelList.get(position);

                    Intent intent = new Intent(context, CardDetailsActivity.class);
                    intent.putExtra("cardNumber", cardModel.getCardNumber());
                    intent.putExtra("nameOnCard", cardModel.getNameOnCard());
                    intent.putExtra("pin", cardModel.getPin());
                    intent.putExtra("ccv", cardModel.getCcv());
                    intent.putExtra("month", cardModel.getValidityMonth());
                    intent.putExtra("year", cardModel.getValidityYear());
                    intent.putExtra("bankName", cardModel.getBankName());

                    CardAdapter adapter = new CardAdapter(context, cardModelList, cardDatabase, recyclerView);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    context.startActivity(intent);


                    break;
                }

                case R.id.card_action_update: {


                    cardDatabase = new CardDatabase(context);
                    cardModelList = cardDatabase.getAllData();
                    cardModel = cardModelList.get(position);
                    idValues = cardModel.getID();

                    showDialog();


                    break;
                }

                case R.id.card_action_delete: {


                    cardDatabase = new CardDatabase(context);
                    cardModelList = cardDatabase.getAllData();
                    CardModel cardModel = cardModelList.get(position);
                    cardDatabase.deleteCard(cardModel);

                    cardModelList.remove(position);
                    CardAdapter adapter = new CardAdapter(context, cardModelList, cardDatabase, recyclerView);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    break;


                }

            }

            return false;
        }

    }



    public void showDialog(){



        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.layout_custom_dialog_card, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        final TextView dialogTitle = (TextView) dialogView.findViewById(R.id.dialog_title);
        dialogTitle.setText("Update "+ cardModel.getType() +" Account");

        final EditText cardNumberET = (EditText) dialogView.findViewById(R.id.dialog_card_number);
        final EditText cardNameOnET = (EditText) dialogView.findViewById(R.id.dialog_card_name);
        final EditText cardPinET = (EditText) dialogView.findViewById(R.id.dialog_card_pin);
        final EditText cardCcvET = (EditText) dialogView.findViewById(R.id.dialog_card_ccv);
        final EditText cardMonthET = (EditText) dialogView.findViewById(R.id.dialog_card_valid_month);
        final EditText cardYearET = (EditText) dialogView.findViewById(R.id.dialog_card_valid_year);
        final EditText cardBankNameET = (EditText) dialogView.findViewById(R.id.dialog_card_bank_name);

        final Button okBTN = (Button) dialogView.findViewById(R.id.dialog_button_ok);
        final Button cancelBTN = (Button) dialogView.findViewById(R.id.dialog_button_cancel);

        cardNumberET.setText(cardModel.getCardNumber());
        cardNameOnET.setText(cardModel.getNameOnCard());
        cardPinET.setText(cardModel.getPin());
        cardCcvET.setText(cardModel.getCcv());
        cardMonthET.setText(cardModel.getValidityMonth());
        cardYearET.setText(cardModel.getValidityYear());
        cardBankNameET.setText(cardModel.getBankName());


        final ImageView pinVisibilityIV = (ImageView) dialogView.findViewById(R.id.dialog_pin_visibility_IV);
        final ImageView ccvVisibilityIV = (ImageView) dialogView.findViewById(R.id.dialog_ccv_visibility_IV);

        pinVisibilityIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter ++;
                if (counter % 2 == 0){

                    cardPinET.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pinVisibilityIV.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_visibility));


                }else {

                    cardPinET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pinVisibilityIV.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_visibility_off));
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
                    ccvVisibilityIV.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_visibility));


                }else {

                    cardCcvET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ccvVisibilityIV.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_visibility_off));
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

                    Toast.makeText(context, "Please input valid", Toast.LENGTH_SHORT).show();
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




    private void prepare() {


        // sharedPreferences = context.getSharedPreferences("idValue", MODE_PRIVATE);

        // idValues = sharedPreferences.getInt("id", 0);
        //  idValues ++;




        CardModel cardModel = new CardModel(idValues, bankName, nameOnCard,
                cardNumber, pin, ccv, month, year, bankName.toUpperCase().charAt(0), type);

        cardDatabase.updateCard(cardModel);
        adapter = new CardAdapter(context, cardModelList, cardDatabase, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        cardModelList = cardDatabase.getAllData();
        adapter = new CardAdapter(context, cardModelList, cardDatabase, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //  SharedPreferences.Editor editor = sharedPreferences.edit();
        //  editor.putInt("id", idValues);
        //  editor.apply();
        adapter.notifyDataSetChanged();

        alertDialog.dismiss();



    }


}
