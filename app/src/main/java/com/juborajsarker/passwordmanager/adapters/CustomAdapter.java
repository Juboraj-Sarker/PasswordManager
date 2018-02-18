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
import com.juborajsarker.passwordmanager.activity.ViewActivity;
import com.juborajsarker.passwordmanager.activity.WebviewActivity;
import com.juborajsarker.passwordmanager.database.DBHelper;
import com.juborajsarker.passwordmanager.model.ModelPassword;

import java.util.List;

/**
 * Created by jubor on 2/12/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private List<ModelPassword> passwordList;
    DBHelper dbHelper;
    RecyclerView recyclerView;

    public SharedPreferences sharedPreferences;
    int idValues;
    String title, passwords, website, type, email;
    CustomAdapter adapter;
    ModelPassword modelPassword;

    String TABLE_NAME;

    int counter = 0;

    AlertDialog alertDialog;







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


    public CustomAdapter(Context context, List<ModelPassword> bankList, DBHelper dbHelper,
                         RecyclerView recyclerView, String TABLE_NAME) {
        this.context = context;
        this.passwordList = bankList;
        this.dbHelper = dbHelper;
        this.recyclerView = recyclerView;
        this.TABLE_NAME = TABLE_NAME;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

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

                passwordList = dbHelper.getAllData(TABLE_NAME);
                ModelPassword modelPassword = passwordList.get(position);

                Intent intent = new Intent(context, ViewActivity.class);

                intent.putExtra("positionValue", position);
                intent.putExtra("title", modelPassword.getTitle());
                intent.putExtra("website", modelPassword.getWebsite());
                intent.putExtra("password", modelPassword.getPassword());
                intent.putExtra("type", modelPassword.getType());
                intent.putExtra("TABLE_NAME", TABLE_NAME);
                intent.putExtra("email", modelPassword.getEmail());


                CustomAdapter adapter = new CustomAdapter(context, passwordList, dbHelper, recyclerView, TABLE_NAME);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                context.startActivity(intent);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                showPopupMenu(holder.cardView, position);

               return true;
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


                case R.id.card_action_copy_password:{



                    passwordList = dbHelper.getAllData(TABLE_NAME);
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
                }



                case R.id.card_action_copy_email:{



                    passwordList = dbHelper.getAllData(TABLE_NAME);
                    ModelPassword modelPassword = passwordList.get(position);

                    String email = modelPassword.getEmail();

                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    if (email.equals("")){

                        Toast.makeText(context, "Error !!! No password found", Toast.LENGTH_SHORT).show();

                    }else {

                        ClipData clipData = ClipData.newPlainText("email", email);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(context, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }

                case R.id.card_action_view:{


                    passwordList = dbHelper.getAllData(TABLE_NAME);
                    ModelPassword modelPassword = passwordList.get(position);

                    Intent intent = new Intent(context, ViewActivity.class);

                    intent.putExtra("positionValue", position);
                    intent.putExtra("title", modelPassword.getTitle());
                    intent.putExtra("website", modelPassword.getWebsite());
                    intent.putExtra("password", modelPassword.getPassword());
                    intent.putExtra("type", modelPassword.getType());
                    intent.putExtra("TABLE_NAME", TABLE_NAME);
                    intent.putExtra("email", modelPassword.getEmail());


                    CustomAdapter adapter = new CustomAdapter(context, passwordList, dbHelper, recyclerView, TABLE_NAME);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    context.startActivity(intent);


                    break;

                }


                case R.id.card_action_visit_site:{

                    dbHelper = new DBHelper(context, TABLE_NAME);
                    passwordList = dbHelper.getAllData(TABLE_NAME);
                    modelPassword = passwordList.get(position);
                    String website = modelPassword.getWebsite();

                    loadURL(website);

                    break;
                }

                case R.id.card_action_update:{

                    dbHelper = new DBHelper(context, TABLE_NAME);
                    passwordList = dbHelper.getAllData(TABLE_NAME);
                    modelPassword = passwordList.get(position);
                    idValues = modelPassword.getId();


                    showDialog();


                    break;
                }

                case R.id.card_action_delete:{


                    dbHelper = new DBHelper(context, TABLE_NAME);
                    passwordList = dbHelper.getAllData(TABLE_NAME);
                    ModelPassword modelPassword = passwordList.get(position);
                    dbHelper.deletePassword( modelPassword, TABLE_NAME);

                    passwordList.remove(position);
                    CustomAdapter adapter = new CustomAdapter(context, passwordList, dbHelper, recyclerView, TABLE_NAME);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


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


    public void showDialog(){



        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.layout_custom_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        final TextView dialogTitle = (TextView) dialogView.findViewById(R.id.dialog_title);
        dialogTitle.setText("Update "+ modelPassword.getType() +" Account");

        final EditText titleValue = (EditText) dialogView.findViewById(R.id.dialog_bank_name);
        final EditText passwordValue = (EditText) dialogView.findViewById(R.id.dialog_bank_password);
        final EditText websiteValue = (EditText) dialogView.findViewById(R.id.dialog_bank_website);
        final EditText emailValue = (EditText) dialogView.findViewById(R.id.dialog_bank_email);

        final Button okBTN = (Button) dialogView.findViewById(R.id.dialog_button_ok);
        final Button cancelBTN = (Button) dialogView.findViewById(R.id.dialog_button_cancel);

        titleValue.setText(modelPassword.getTitle());
        passwordValue.setText(modelPassword.getPassword());
        websiteValue.setText(modelPassword.getWebsite());
        emailValue.setText(modelPassword.getEmail());
        type = modelPassword.getType();
        email = modelPassword.getEmail();


        final ImageView visibilityIV = (ImageView) dialogView.findViewById(R.id.dialog_visibility_IV);

        visibilityIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter ++;
                if (counter % 2 == 0){

                    passwordValue.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    visibilityIV.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_visibility));


                }else {

                    passwordValue.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    visibilityIV.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_visibility_off));
                }
            }
        });


        okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                title = titleValue.getText().toString();
                passwords = passwordValue.getText().toString();
                website = websiteValue.getText().toString();
                email = emailValue.getText().toString();

                if (title.equals("")
                        || passwords.equals("")){

                    Toast.makeText(context, "Please input valid", Toast.LENGTH_SHORT).show();
                }

                else {

                    if (website.equals("")){

                        website = "null";
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



        ModelPassword password = new ModelPassword(idValues, title, passwords, website, title.toUpperCase().charAt(0), type, email);
        dbHelper.updatePassword(password, TABLE_NAME);
        adapter = new CustomAdapter(context, passwordList, dbHelper, recyclerView, TABLE_NAME);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        passwordList = dbHelper.getAllData(TABLE_NAME);
        adapter = new CustomAdapter(context, passwordList, dbHelper, recyclerView, TABLE_NAME);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


      //  SharedPreferences.Editor editor = sharedPreferences.edit();
      //  editor.putInt("id", idValues);
      //  editor.apply();
        adapter.notifyDataSetChanged();

        alertDialog.dismiss();



    }









}
