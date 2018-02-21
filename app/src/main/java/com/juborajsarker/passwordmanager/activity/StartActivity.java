package com.juborajsarker.passwordmanager.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.juborajsarker.passwordmanager.R;

public class StartActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;

    TextView messageTV;
    Button okBTN;
    EditText inputTV;

    String password1, password2;
    int count, counter = 0;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        Boolean passwordSet = sharedPreferences.getBoolean("passwordSet", false);


        if (passwordSet){

            startActivity(new Intent(StartActivity.this, LockscreenActivity.class));
            finish();
        }


        init();
    }



    private void init() {

        inputTV = findViewById(R.id.tv_input);
        messageTV = findViewById(R.id.tv_label_message);

        okBTN = findViewById(R.id.okBtn);
        okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (inputTV.getText().toString().length() <4){

                    inputTV.setError("Please select a valid password");
                    counter = inputTV.getText().toString().length();

                }else if (inputTV.getText().toString().length() == 4){

                    count ++;
                    messageTV.setText("Retype master password");
                    counter = 0;


                    if (count == 1){

                        password1 = inputTV.getText().toString();
                        inputTV.setText("");

                    }

                    if (count == 2){

                        password2 = inputTV.getText().toString();

                        if (password1.equals(password2)){

                            Toast.makeText(StartActivity.this, "Success !!!", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("masterPassword", password1);
                            editor.putBoolean("passwordSet", true);
                            editor.commit();

                            Intent intent = new Intent(StartActivity.this, MainActivity.class);
                            intent.putExtra("fragmentName", "home");
                            startActivity(intent);
                            finish();
                        }

                        else if (password1 != password2){


                            count --;
                            inputTV.setError("Model Password not matched ");
                        }
                    }

                }
            }
        });
    }

    public void numberPressed(View view) {

        switch (view.getId()){

            case R.id.tv_0:{

                inputTV.append("0");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;
                break;
            }

            case R.id.tv_1:{

                inputTV.append("1");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }

            case R.id.tv_2:{

                inputTV.append("2");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_3:{

                inputTV.append("3");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }



            case R.id.tv_4:{

                inputTV.append("4");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }



            case R.id.tv_5:{

                inputTV.append("5");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_6:{

                inputTV.append("6");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_7:{

                inputTV.append("7");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_8:{

                inputTV.append("8");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.tv_9:{

                inputTV.append("9");

                if (counter < 4){

                    vibrator.vibrate(100);
                }
                counter++;

                break;
            }


            case R.id.iv_backspace:{


                if (inputTV.getText().toString().length()>0){

                    inputTV.setText(inputTV.getText().toString().substring(0, inputTV.getText().toString().length()-1));
                    counter = inputTV.getText().toString().length();
                }

                if (inputTV.getText().toString().equals("")){

                    counter = 0;
                }


                break;
            }


            case R.id.iv_close:{



                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("Thanks for using my app")
                        .setMessage("\nAre you sure you want to really exit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                AppExit();
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


    }

    private void AppExit() {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }









}
