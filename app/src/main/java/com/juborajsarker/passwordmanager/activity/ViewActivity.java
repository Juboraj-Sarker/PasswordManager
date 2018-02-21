package com.juborajsarker.passwordmanager.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
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
import com.juborajsarker.passwordmanager.database.DBHelper;
import com.juborajsarker.passwordmanager.model.ModelPassword;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    TextView titleTV, websiteTV, passwordTV, emailTV;
    ImageView searchIV, browseIV, copyPasswordIV, copyEmailIV;
    Button deleteBTN, editBTN;
    LinearLayout buttonLayout;
    String TABLE_NAME;
    int flag;


    AlertDialog alertDialog;


    String title, website, password, type, email;
    int positionValue = 0;
    int idValues = 0;
    int counter = 0;

    List<ModelPassword> passwordList;
    DBHelper dbHelper;
    ModelPassword modelPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        website = intent.getStringExtra("website");
        password = intent.getStringExtra("password");
        positionValue = intent.getIntExtra("positionValue", -1);
        type = intent.getStringExtra("type");
        TABLE_NAME = intent.getStringExtra("TABLE_NAME");
        email = intent.getStringExtra("email");
        flag = intent.getIntExtra("flag", 0);





        passwordList = new ArrayList<ModelPassword>();
        dbHelper = new DBHelper(this, TABLE_NAME);









        init();
        setAction();


        titleTV.setText(title);
        websiteTV.setText(website);
        passwordTV.setText(password);
        emailTV.setText(email);
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


        titleTV = (TextView) findViewById(R.id.view_title_TV);
        websiteTV = (TextView) findViewById(R.id.view_website_TV);
        passwordTV = (TextView) findViewById(R.id.view_password_TV);
        emailTV = (TextView) findViewById(R.id.view_email_TV);

        searchIV = (ImageView) findViewById(R.id.view_search_IV);
        browseIV = (ImageView) findViewById(R.id.view_browse_IV);
        copyPasswordIV = (ImageView) findViewById(R.id.view_copy_IV);
        copyEmailIV = (ImageView) findViewById(R.id.view_copy_email_IV);

        deleteBTN = (Button) findViewById(R.id.view_delete_BTN);
        editBTN = (Button) findViewById(R.id.view_edit_BTN);

        buttonLayout = (LinearLayout) findViewById(R.id.button_LAYOUT);

        if (flag == 1){

            buttonLayout.setVisibility(View.GONE);
        }




    }


    private void setAction() {


        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                deleteData();



            }
        });


        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateDate();

            }
        });


        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String url ;

                if (website.length()<4){

                    Toast.makeText(ViewActivity.this, "Please enter a valid website", Toast.LENGTH_SHORT).show();

                }else {

                    if (website.substring(0,3).equals("www")){

                        url = "http://"+ website;

                    }else if (website.substring(0,4).equals("http")){

                        url = website;

                    }else {

                        url = "http://www."+ website;

                    }




                    Intent intent = new Intent(ViewActivity.this, WebviewActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }


            }
        });



        browseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String url ;

                if (website.length()<4){

                    Toast.makeText(ViewActivity.this, "Please enter a valid website", Toast.LENGTH_SHORT).show();

                }else {

                    if (website.substring(0,3).equals("www")){

                        url = "http://"+ website;

                    }else if (website.substring(0,4).equals("http")){

                        url = website;

                    }else {

                        url = "http://www."+ website;

                    }


                    Intent intent = new Intent(ViewActivity.this, WebviewActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }

            }
        });


        copyEmailIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                if (emailTV.getText().toString().equals("")){

                    Toast.makeText(ViewActivity.this, "Nothing to copy !!!", Toast.LENGTH_SHORT).show();
                }else {

                    email = emailTV.getText().toString();

                }
                ClipData clipData = ClipData.newPlainText("email", email);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(ViewActivity.this, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();

            }
        });


        copyPasswordIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if (passwordTV.getText().toString().equals("")){

                    Toast.makeText(ViewActivity.this, "Nothing to copy !!!", Toast.LENGTH_SHORT).show();
                }else {

                    password = passwordTV.getText().toString();

                }
                ClipData clipData = ClipData.newPlainText("pass", password);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(ViewActivity.this, "Copied to clipboard !!!", Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void deleteData() {


        passwordList = dbHelper.getAllData(TABLE_NAME);
        ModelPassword modelPassword = passwordList.get(positionValue);
        dbHelper.deletePassword(modelPassword, TABLE_NAME);

        Toast.makeText(this, "Deleted Successfully !!!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }


    private void updateDate() {


        passwordList = dbHelper.getAllData(TABLE_NAME);
        modelPassword = passwordList.get(positionValue);
        showDialog();



    }









    public void showDialog(){



        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogView = layoutInflater.inflate(R.layout.layout_custom_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        final TextView dialogTitle = (TextView) dialogView.findViewById(R.id.dialog_title);
        dialogTitle.setText("Update "+ type +" Account");

        final EditText titleValue = (EditText) dialogView.findViewById(R.id.dialog_bank_name);
        final EditText passwordValue = (EditText) dialogView.findViewById(R.id.dialog_bank_password);
        final EditText websiteValue = (EditText) dialogView.findViewById(R.id.dialog_bank_website);
        final EditText emailValue = (EditText) dialogView.findViewById(R.id.dialog_bank_email);

        final ImageView visibilityIV = (ImageView) dialogView.findViewById(R.id.dialog_visibility_IV);

        final Button okBTN = (Button) dialogView.findViewById(R.id.dialog_button_ok);
        final Button cancelBTN = (Button) dialogView.findViewById(R.id.dialog_button_cancel);

        titleValue.setText(modelPassword.getTitle());
        passwordValue.setText(modelPassword.getPassword());
        websiteValue.setText(modelPassword.getWebsite());
        idValues = modelPassword.getId();
        emailValue.setText(modelPassword.getEmail());



        visibilityIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter ++;
                if (counter % 2 == 0){

                    passwordValue.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    visibilityIV.setImageDrawable(ContextCompat.getDrawable(ViewActivity.this, R.drawable.ic_visibility));


                }else {

                    passwordValue.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    visibilityIV.setImageDrawable(ContextCompat.getDrawable(ViewActivity.this, R.drawable.ic_visibility_off));
                }
            }
        });

        okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                title = titleValue.getText().toString();
                password = passwordValue.getText().toString();
                website = websiteValue.getText().toString();
                email = emailValue.getText().toString();


                if (title.equals("")
                        || password.equals("")
                        || email.equals("")){

                    Toast.makeText(ViewActivity.this, "Please input valid", Toast.LENGTH_SHORT).show();
                }

                else {

                    if (website.equals("")){

                        website = "null";
                    }

                    prepare();

                    titleTV.setText(title);
                    passwordTV.setText(password);
                    websiteTV.setText(website);
                    emailTV.setText(email);


                }

            }
        });


        cancelBTN.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             alertDialog.dismiss();
                                         }
                                     }


        );

        builder.setCancelable(false);


        alertDialog = builder.create();
        alertDialog.show();


    }




    private void prepare() {



        ModelPassword passwords = new ModelPassword(idValues, title, password, website, title.toUpperCase().charAt(0), type, email);
        dbHelper.updatePassword(passwords, TABLE_NAME);
        alertDialog.dismiss();



    }

}
