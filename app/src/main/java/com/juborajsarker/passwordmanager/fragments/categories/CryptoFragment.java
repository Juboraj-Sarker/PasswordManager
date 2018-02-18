package com.juborajsarker.passwordmanager.fragments.categories;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.adapters.CustomAdapter;
import com.juborajsarker.passwordmanager.database.DBHelper;
import com.juborajsarker.passwordmanager.model.GridSpacingItemDecoration;
import com.juborajsarker.passwordmanager.model.ModelPassword;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class CryptoFragment extends Fragment {

    View view;

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<ModelPassword> passwordList;
    private DBHelper dbHelper;

    private String TABLE_NAME = "cryptoTable";

    private FloatingActionButton fab;


    String title, passwords, website, email;
    String TYPE = "CRYPTOCURRENCY";
    String prefKey = "cryptoId";

    AlertDialog alertDialog;

    int idValues = 0;
    int counter = 0;
    public SharedPreferences sharedPreferences;


    public CryptoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_crypto, container, false);


        mustExecute();


        return view;
    }

    private void mustExecute() {


        sharedPreferences = getActivity().getSharedPreferences("emailValue", MODE_PRIVATE);

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

                            prepare();

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
    }

}
