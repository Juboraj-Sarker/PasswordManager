package com.juborajsarker.passwordmanager.fragments.categories;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.activity.RegisterActivity;
import com.juborajsarker.passwordmanager.adapters.CustomAdapter;
import com.juborajsarker.passwordmanager.database.DBHelper;
import com.juborajsarker.passwordmanager.java_class.UserCountry;
import com.juborajsarker.passwordmanager.model.FirebaseModel;
import com.juborajsarker.passwordmanager.model.GridSpacingItemDecoration;
import com.juborajsarker.passwordmanager.model.ModelPassword;
import com.juborajsarker.passwordmanager.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class OthersFragment extends Fragment {

    View view;
    TextView backupTV;




    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<ModelPassword> passwordList;
    private DBHelper dbHelper;

    private String TABLE_NAME = "otherTable";

    private FloatingActionButton fab;


    String title, passwords, website, email;
    String TYPE = "OTHERS";
    String prefKey = "othersId";

    AlertDialog alertDialog;

    int idValues = 0;
    int counter = 0;

    public SharedPreferences sharedPreferences;
    boolean onlineRegister;
    String emailPref, userPref;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;


    public OthersFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_others, container, false);

        sharedPreferences = getActivity().getSharedPreferences("registerStatus", MODE_PRIVATE);
        onlineRegister = sharedPreferences.getBoolean("onlineRegisterStatus", false);
        emailPref = sharedPreferences.getString("email","");
        userPref = sharedPreferences.getString("uid","");


        mustExecute();

        return view;
    }


    private void mustExecute() {


        sharedPreferences = getActivity().getSharedPreferences("emailValue", MODE_PRIVATE);

        backupTV = (TextView) view.findViewById(R.id.backupTV);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                final View dialogView = layoutInflater.inflate(R.layout.layout_custom_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);

                final TextView dialogTitle = (TextView) dialogView.findViewById(R.id.dialog_title);
                dialogTitle.setText("Add New " + TYPE +" Account");

                final EditText titleValue = (EditText) dialogView.findViewById(R.id.dialog_bank_name);
                final EditText passwordValue = (EditText) dialogView.findViewById(R.id.dialog_bank_password);
                final EditText websiteValue = (EditText) dialogView.findViewById(R.id.dialog_bank_website);
                final EditText emailValue = (EditText) dialogView.findViewById(R.id.dialog_bank_email);

                final ImageView visibilityIV = (ImageView) dialogView.findViewById(R.id.dialog_visibility_IV);

                final Button okBTN = (Button) dialogView.findViewById(R.id.dialog_button_ok);
                final Button cancelBTN = (Button) dialogView.findViewById(R.id.dialog_button_cancel);

                titleValue.setHint("Ex: CPanel");
                websiteValue.setHint("Ex: www.yourcpanel.com");

                visibilityIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        counter++;
                        if (counter % 2 == 0) {

                            passwordValue.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            visibilityIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility));


                        } else {

                            passwordValue.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            visibilityIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off));
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
                                || passwords.equals("")
                                || email.equals("")) {

                            Toast.makeText(getContext(), "Please input valid", Toast.LENGTH_SHORT).show();
                        } else {

                            if (website.equals("")) {

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


                cancelBTN.setOnClickListener
                        (new View.OnClickListener() {
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
        });


        dbHelper = new DBHelper(getContext(), TABLE_NAME);
        dbHelper.setTABLE_NAME(TABLE_NAME);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        passwordList = new ArrayList<>();
        adapter = new CustomAdapter(getContext(), passwordList, dbHelper, recyclerView, TABLE_NAME);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    private void prepare() {


        idValues = sharedPreferences.getInt(prefKey, 0);
        idValues++;

        ModelPassword password = new ModelPassword(idValues, title, passwords, website, title.toUpperCase().charAt(0),
                TYPE, email);
        dbHelper.insertData(password, TABLE_NAME);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        passwordList = dbHelper.getAllData(TABLE_NAME);
        adapter = new CustomAdapter(getContext(), passwordList, dbHelper, recyclerView, TABLE_NAME);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(prefKey, idValues);
        editor.apply();
        adapter.notifyDataSetChanged();

        alertDialog.dismiss();

        setBackupTV();


    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public void onResume() {
        super.onResume();

        dbHelper = new DBHelper(getContext(), TABLE_NAME);
        passwordList = dbHelper.getAllData(TABLE_NAME);
        adapter = new CustomAdapter(getContext(), passwordList, dbHelper, recyclerView, TABLE_NAME);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        setBackupTV();
    }


    public void setBackupTV(){

        if (passwordList.size() > 0){

            backupTV.setVisibility(View.VISIBLE);

            backupTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        prepareForFirebase();

                    }catch (Exception e){

                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else {

            backupTV.setVisibility(View.GONE);
        }

    }


    public void prepareForFirebase(){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait ......");
        progressDialog.show();

        if (onlineRegister &&
                ! emailPref.equals("")
                && ! userPref.equals(""))
        {

            passwordList = dbHelper.getAllData(TABLE_NAME);

            for (int i = 0; i < passwordList.size(); i++){


                ModelPassword modelPasswords = passwordList.get(i);
                databaseReference = FirebaseDatabase.getInstance().getReference("User/" + userPref
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

                for (int j=0; j<model.getTitle().length(); j++){

                    temp = model.getTitle().charAt(j);
                    keyValue = keyValue + temp;
                }


                for (int j=0; j<model.getEmail().length(); j++){

                    temp = model.getEmail().charAt(j);
                    keyValue = keyValue + temp;
                }


                for (int j=0; j<model.getPassword().length(); j++){

                    temp = model.getPassword().charAt(j);
                    keyValue = keyValue + temp;
                }

                for (int j=0; j<model.getWebsite().length(); j++){

                    temp = model.getWebsite().charAt(j);
                    keyValue = keyValue + temp;
                }



                String key = String.valueOf(keyValue);
                databaseReference.child(key).setValue(model);

                databaseReference3 = FirebaseDatabase.getInstance().getReference("Backup/" + UserCountry.getUserCountry(getContext())
                        + "/" + userPref + "/" + modelPasswords.getType());
                databaseReference3.child(key).setValue(model);

            }


            databaseReference2 = FirebaseDatabase.getInstance().getReference("User/" + userPref
                    + "/Other Data");


            String manufacturer = Build.MANUFACTURER;
            String brand = Build.BRAND;
            String modelName = Build.MODEL;
            String deviceID = Build.SERIAL;


            int api_level = Build.VERSION.SDK_INT;
            String  os_version  = Build.VERSION.RELEASE;
            String versionName = getVersionName(api_level);



            UserInfo userInfo = new UserInfo(emailPref, userPref, manufacturer, brand, modelName, deviceID,
                    os_version, api_level, versionName);

            databaseReference2.setValue(userInfo);

            Toast.makeText(getContext(), "Successfully added on cloud !!!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();


        }else {

            progressDialog.dismiss();
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog);
            } else {
                builder = new AlertDialog.Builder(getContext());
            }
            builder.setTitle("Login or Register first")
                    .setMessage("\nYou did not logged in as online user. May be you did not register yet. If you " +
                            "did not register you may register first. Or you can simply login if you already register. " +
                            "\nDo you want to go login or register page?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(getContext(), RegisterActivity.class));
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }

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
