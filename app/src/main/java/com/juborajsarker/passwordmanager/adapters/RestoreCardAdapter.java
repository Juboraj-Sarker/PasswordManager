package com.juborajsarker.passwordmanager.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.activity.CardDetailsActivity;
import com.juborajsarker.passwordmanager.model.CardModel;

import java.util.List;

/**
 * Created by jubor on 2/21/2018.
 */

public class RestoreCardAdapter extends RecyclerView.Adapter<RestoreCardAdapter.MyViewHolder> {


    private Context context;
    private List<CardModel> cardModelList;
    RecyclerView recyclerView;

    public SharedPreferences sharedPreferences;
    String type;




    public class MyViewHolder extends RecyclerView.ViewHolder{


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


    public RestoreCardAdapter(Context context, List<CardModel> cardModelList, RecyclerView recyclerView) {
        this.context = context;
        this.cardModelList = cardModelList;
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
        inflater.inflate(R.menu.restore_card_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popupMenu.show();

    }


    public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

        int position;

        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()){

                case R.id.card_action_copy_card_number:{

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

                }case R.id.card_action_copy_card_pin:{

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

                }case R.id.card_action_copy_card_ccv:{


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

                }case R.id.card_action_view:{


                    CardModel cardModel = cardModelList.get(position);

                    Intent intent = new Intent(context, CardDetailsActivity.class);
                    intent.putExtra("cardNumber", cardModel.getCardNumber());
                    intent.putExtra("nameOnCard", cardModel.getNameOnCard());
                    intent.putExtra("pin", cardModel.getPin());
                    intent.putExtra("ccv", cardModel.getCcv());
                    intent.putExtra("month", cardModel.getValidityMonth());
                    intent.putExtra("year", cardModel.getValidityYear());
                    intent.putExtra("bankName", cardModel.getBankName());
                    intent.putExtra("flag", 1);

                    RestoreCardAdapter adapter = new RestoreCardAdapter(context, cardModelList, recyclerView);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    context.startActivity(intent);

                    break;

                }
            }

            return false;
        }
    }
}
