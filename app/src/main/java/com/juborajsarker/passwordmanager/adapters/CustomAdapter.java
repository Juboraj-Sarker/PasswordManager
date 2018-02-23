package com.juborajsarker.passwordmanager.adapters;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.activity.RegisterActivity;
import com.juborajsarker.passwordmanager.activity.ViewActivity;
import com.juborajsarker.passwordmanager.activity.WebviewActivity;
import com.juborajsarker.passwordmanager.database.DBHelper;
import com.juborajsarker.passwordmanager.model.FirebaseModel;
import com.juborajsarker.passwordmanager.model.ModelPassword;
import com.juborajsarker.passwordmanager.model.UserInfo;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jubor on 2/12/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private List<ModelPassword> passwordList;
    DBHelper dbHelper;
    RecyclerView recyclerView;

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    String uid, prefEmail;
    boolean onlineRegister = false;
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

                        Toast.makeText(context, "Error !!! No email found", Toast.LENGTH_SHORT).show();

                    }else {

                        ClipData clipData = ClipData.newPlainText("email", email);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(context, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }

                case R.id.card_action_backup:{





                    sharedPreferences = context.getSharedPreferences("registerStatus", MODE_PRIVATE);
                    onlineRegister = sharedPreferences.getBoolean("onlineRegisterStatus", false);
                    prefEmail = sharedPreferences.getString("email","");
                    uid = sharedPreferences.getString("uid","");

                    if ( !onlineRegister || prefEmail.equals("") || uid.equals("") ){

                        Toast.makeText(context, "You are not logged in as registered user. Please login first.",
                                Toast.LENGTH_SHORT).show();


                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog);
                        } else {
                            builder = new AlertDialog.Builder(context);
                        }
                        builder.setTitle("Login or Register first")
                                .setMessage("\nYou did not logged in as online user. May be you did not register yet. If you " +
                                        "did not register you may register first. Or you can simply login if you already register. " +
                                        "\nDo you want to go login or register page?")
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        context.startActivity(new Intent(context, RegisterActivity.class));
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .show();

                    }else {


                        try {

                            prepareForFirebase(position);

                        }catch (Exception e){

                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

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



                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("DELETE file?")
                            .setMessage("Are you sure you want to proceed with the deletion?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    deleteData(position);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();





                    break;


                }

            }

            return false;
        }
    }

    private void deleteData(int position) {

        dbHelper = new DBHelper(context, TABLE_NAME);
        passwordList = dbHelper.getAllData(TABLE_NAME);
        ModelPassword modelPassword = passwordList.get(position);
        dbHelper.deletePassword( modelPassword, TABLE_NAME);

        passwordList.remove(position);
        CustomAdapter adapter = new CustomAdapter(context, passwordList, dbHelper, recyclerView, TABLE_NAME);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

                    if (title.charAt(0) == ' ' || email.charAt(0) == ' ' || website.charAt(0) ==' '){

                        if (title.charAt(0) == ' '){

                            titleValue.setError("Title cannot start with space");
                        }

                        if (email.charAt(0) == ' '){

                            emailValue.setError("Email cannot start with space");
                        }

                        if (website.charAt(0) == ' '){

                            websiteValue.setError("Website cannot start with space");
                        }

                    }else {

                        if (!emailValidation(email)){

                            emailValue.setError("This is not a valid email format.\nPlease check your input");

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






    private void prepareForFirebase(int position) {


        passwordList = dbHelper.getAllData(TABLE_NAME);
        ModelPassword modelPasswords = passwordList.get(position);
        databaseReference = FirebaseDatabase.getInstance().getReference("User/" + uid
                + "/Account Data/" + modelPasswords.getType());

        FirebaseModel model = new FirebaseModel(modelPasswords.getId(),
                modelPasswords.getTitle(),
                modelPasswords.getPassword(),
                modelPasswords.getWebsite(),
                String.valueOf(modelPasswords.getHeader()),
                modelPasswords.getType(),
                modelPasswords.getEmail());


        int keyValue = 0;
        int temp = 0;

        for (int i=0; i<model.getTitle().length(); i++){

            temp = model.getTitle().charAt(i);
            keyValue = keyValue + temp;
        }


        for (int i=0; i<model.getEmail().length(); i++){

            temp = model.getEmail().charAt(i);
            keyValue = keyValue + temp;
        }


        for (int i=0; i<model.getPassword().length(); i++){

            temp = model.getPassword().charAt(i);
            keyValue = keyValue + temp;
        }

        for (int i=0; i<model.getWebsite().length(); i++){

            temp = model.getWebsite().charAt(i);
            keyValue = keyValue + temp;
        }



        String key = String.valueOf(keyValue);
        databaseReference.child(key).setValue(model);

        databaseReference2 = FirebaseDatabase.getInstance().getReference("User/" + uid
                + "/Other Data");


        String manufacturer = Build.MANUFACTURER;
        String brand = Build.BRAND;
        String modelName = Build.MODEL;
        String deviceID = Build.SERIAL;


        int api_level = Build.VERSION.SDK_INT;
        String  os_version  = Build.VERSION.RELEASE;
        String versionName = getVersionName(api_level);



        UserInfo userInfo = new UserInfo(prefEmail, uid, manufacturer, brand, modelName, deviceID,
                os_version, api_level, versionName);

        databaseReference2.setValue(userInfo);

        Toast.makeText(context, "Successfully added on cloud !!!", Toast.LENGTH_SHORT).show();
    }





    private boolean emailValidation(String email) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String inputEmail = email.trim();

        if (inputEmail.matches(emailPattern)){

            return true;

        }else {

            return false;
        }


    }

}
