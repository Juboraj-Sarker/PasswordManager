package com.juborajsarker.passwordmanager.adapters;

import android.content.Context;
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
import com.juborajsarker.passwordmanager.java_class.BankPassword;

import java.util.List;

/**
 * Created by jubor on 2/12/2018.
 */

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.MyViewHolder> {

    private Context context;
    private List<BankPassword> bankList;






    public class MyViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;

        public TextView titleTV;
        public TextView subTitleTV;
        public TextView headerTV;
        public ImageView optionIV;



        public MyViewHolder(View view) {

            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);

            titleTV = (TextView) view.findViewById(R.id.cv_title_TV);
            subTitleTV = (TextView) view.findViewById(R.id.cv_subTitle_TV);
            headerTV = (TextView) view.findViewById(R.id.cv_header_TV);
            optionIV = (ImageView) view.findViewById(R.id.cv_option_IV);


        }
    }


    public BankAdapter(Context context, List<BankPassword> bankList) {
        this.context = context;
        this.bankList = bankList;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        BankPassword bank = bankList.get(position);
        holder.titleTV.setText(bank.getTitle());
        holder.subTitleTV.setText(bank.getWebsite());
        holder.headerTV.setText(String.valueOf(bank.getHeader()));

        holder.optionIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showPopupMenu(holder.optionIV, position);
            }
        });


    }

    @Override
    public int getItemCount() {


        return bankList.size();
    }


   public void showPopupMenu(View view, int position){


       PopupMenu popupMenu = new PopupMenu(context, view);
       MenuInflater inflater = popupMenu.getMenuInflater();
       inflater.inflate(R.menu.card_menu, popupMenu.getMenu());
       popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
       popupMenu.show();

    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{


        int position;

        public MyMenuItemClickListener(int position) {

            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()){

                case R.id.card_action_details:{

                    Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();

                }

                case R.id.card_action_update:{


                }

                case R.id.card_action_delete:{


                }

            }

            return false;
        }
    }
}
