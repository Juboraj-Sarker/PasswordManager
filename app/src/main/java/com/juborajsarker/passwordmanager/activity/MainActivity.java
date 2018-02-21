package com.juborajsarker.passwordmanager.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.fragments.categories.BankFragment;
import com.juborajsarker.passwordmanager.fragments.categories.CardFragment;
import com.juborajsarker.passwordmanager.fragments.categories.CryptoFragment;
import com.juborajsarker.passwordmanager.fragments.categories.EmailFragment;
import com.juborajsarker.passwordmanager.fragments.categories.HomeFragment;
import com.juborajsarker.passwordmanager.fragments.categories.OthersFragment;
import com.juborajsarker.passwordmanager.fragments.categories.SocialFragment;
import com.juborajsarker.passwordmanager.fragments.cloud.BackupFragment;
import com.juborajsarker.passwordmanager.fragments.cloud.RestoreFragment;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    TextView myTV;
    View header;

    String fragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        fragmentName = intent.getStringExtra("fragmentName");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);


        myTV = (TextView) header.findViewById(R.id.myTV);
        myTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Clicked !!!", Toast.LENGTH_SHORT).show();
            }
        });



        setInitialFragment();





    }

    private void setInitialFragment() {

        if (fragmentName.equals("home")){


            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new HomeFragment()).commit();
            setTitle("Password Manager");

        }else if (fragmentName.equals("bank")){


            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new BankFragment()).commit();
            setTitle("Bank Account");

        }else if (fragmentName.equals("crypto")){


            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new CryptoFragment()).commit();
            setTitle("Cryptocurrency");

        }else if (fragmentName.equals("card")){

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new CardFragment()).commit();
            setTitle("Debit / Credit card");

        }else if (fragmentName.equals("email")){

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new EmailFragment()).commit();
            setTitle("Email Account");

        }else if (fragmentName.equals("social")){

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new SocialFragment()).commit();
            setTitle("Social Media");

        }else if (fragmentName.equals("other")){

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new OthersFragment()).commit();
            setTitle("Others");

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_about) {


            return true;

        }else if (id == R.id.action_exit){

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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {


            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                if(fragment!=null) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }


            fragmentManager.beginTransaction().add(R.id.container, new HomeFragment()).commit();
            setTitle("Password Manager");


        }else if (id == R.id.nav_bank_account) {


            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                if(fragment!=null) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }


            fragmentManager.beginTransaction().add(R.id.container, new BankFragment()).commit();
            setTitle("Bank Account");



        }else if (id == R.id.nav_cryptocurrency) {


            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                if(fragment!=null) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }


            fragmentManager.beginTransaction().add(R.id.container, new CryptoFragment()).commit();
            setTitle("Cryptocurrency");



        }else if (id == R.id.nav_card) {


            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                if(fragment!=null) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }


            fragmentManager.beginTransaction().add(R.id.container, new CardFragment()).commit();
            setTitle("Debit / Credit Card");




        }else if (id == R.id.nav_email) {


            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                if(fragment!=null) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }


            fragmentManager.beginTransaction().add(R.id.container, new EmailFragment()).commit();
            setTitle("Email Account");



        }else if (id == R.id.nav_social_media) {


            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                if(fragment!=null) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }


            fragmentManager.beginTransaction().add(R.id.container, new SocialFragment()).commit();
            setTitle("Social Media");



        }else if (id == R.id.nav_others) {


            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                if(fragment!=null) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }


            fragmentManager.beginTransaction().add(R.id.container, new OthersFragment()).commit();
            setTitle("Others");



        } else if (id == R.id.nav_backup) {


            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                if(fragment!=null) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }


            fragmentManager.beginTransaction().add(R.id.container, new BackupFragment()).commit();
            setTitle("Backup to Cloud");



        }else if (id == R.id.nav_restore) {


            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                if(fragment!=null) {

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }


            fragmentManager.beginTransaction().add(R.id.container, new RestoreFragment()).commit();
            setTitle("Restore from Cloud");



        }else if (id == R.id.nav_rate) {


            rateApps();



        } else if (id == R.id.nav_share) {


            shareApp();



        }else if (id == R.id.nav_more_apps) {



            moreApps();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void moreApps() {


        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6155570899607409709&hl"));
        startActivity(intent);
    }

    private void shareApp() {


        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.createChooser(intent,"Password Manager");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        startActivity(Intent.createChooser(intent, "share Password Manager using:"));
    }


    private void logOut() {

        sharedPreferences = getSharedPreferences("loginStatus", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(MainActivity.this, LockscreenActivity.class));
    }


    public void AppExit() {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);



    }



    public void rateApps() {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }


    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }
}
