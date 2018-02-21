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
import com.juborajsarker.passwordmanager.activity.ViewActivity;
import com.juborajsarker.passwordmanager.activity.WebviewActivity;
import com.juborajsarker.passwordmanager.model.ModelPassword;

import java.util.List;

/**
 * Created by jubor on 2/21/2018.
 */

public class RestoreAdapter extends RecyclerView.Adapter<RestoreAdapter.MyViewHolder>  {


    private Context context;
    private List<ModelPassword> passwordList;
    RecyclerView recyclerView;


    public SharedPreferences sharedPreferences;
    String type;

    ModelPassword modelPassword;


    public class MyViewHolder extends RecyclerView.ViewHolder{


        public CardView cardView;

        public TextView cvTitleTV;
        public TextView cvEmailTV;
        public TextView cvWebsiteTV;
        public TextView cvHeaderTV;

        public ImageView optionIV;



        public MyViewHolder(View view) {
            super(view);


            cardView = (CardView) view.findViewById(R.id.card_view);

            cvTitleTV = (TextView) view.findViewById(R.id.cv_title_TV);
            cvEmailTV = (TextView) view.findViewById(R.id.cv_email_TV);
            cvWebsiteTV = (TextView) view.findViewById(R.id.cv_website_TV);
            cvHeaderTV = (TextView) view.findViewById(R.id.cv_header_TV);

            optionIV = (ImageView) view.findViewById(R.id.cv_option_IV);

        }
    }


    public RestoreAdapter(Context context, List<ModelPassword> passwordList, RecyclerView recyclerView, String type) {
        this.context = context;
        this.passwordList = passwordList;
        this.recyclerView = recyclerView;
        this.type = type;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restore_custom_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        ModelPassword bank = passwordList.get(position);
        holder.cvTitleTV.setText(bank.getTitle());
        holder.cvEmailTV.setText(bank.getEmail());
        holder.cvWebsiteTV.setText(bank.getWebsite());
        holder.cvHeaderTV.setText(String.valueOf(bank.getHeader()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
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
        return passwordList.size();
    }



    public void showPopupMenu(View view, int position){


        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.restore_menu, popupMenu.getMenu());
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


                case R.id.card_action_copy_password:{

                    ModelPassword modelPassword = passwordList.get(position);
                    String password = modelPassword.getPassword();

                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    if (password.equals("")){

                        Toast.makeText(context, "Error !!! No password found", Toast.LENGTH_SHORT).show();

                    }else {

                        ClipData clipData = ClipData.newPlainText("password", password);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(context, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                    }



                    break;

                }case R.id.card_action_copy_email:{

                    ModelPassword modelPassword = passwordList.get(position);
                    String email = modelPassword.getEmail();

                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    if (email.equals("")){

                        Toast.makeText(context, "Error !!! No Email found", Toast.LENGTH_SHORT).show();

                    }else {

                        ClipData clipData = ClipData.newPlainText("email", email);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(context, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                    }



                    break;

                }case R.id.card_action_view:{


                    ModelPassword modelPassword = passwordList.get(position);

                    Intent intent = new Intent(context, ViewActivity.class);

                    intent.putExtra("positionValue", position);
                    intent.putExtra("title", modelPassword.getTitle());
                    intent.putExtra("website", modelPassword.getWebsite());
                    intent.putExtra("password", modelPassword.getPassword());
                    intent.putExtra("type", modelPassword.getType());
                    intent.putExtra("email", modelPassword.getEmail());
                    intent.putExtra("flag",1);


                    RestoreAdapter adapter = new RestoreAdapter(context, passwordList, recyclerView, type);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    context.startActivity(intent);

                    break;

                }case R.id.card_action_visit_site:{

                    modelPassword = passwordList.get(position);
                    String website = modelPassword.getWebsite();

                    loadURL(website);

                    break;

                }
            }

            return false;
        }
    }

    private void loadURL(String website) {

        String url ;

        if (website.length()<4){

            Toast.makeText(context, "Please enter a valid website", Toast.LENGTH_SHORT).show();

        }else {

            if (website.substring(0,3).equals("www")){

                url = "http://"+ website;

            }else if (website.substring(0,4).equals("http")){

                url = website;

            }else {

                url = "http://www."+ website;

            }




            Intent intent = new Intent(context, WebviewActivity.class);
            intent.putExtra("url", url);
            context.startActivity(intent);
        }
    }

}
