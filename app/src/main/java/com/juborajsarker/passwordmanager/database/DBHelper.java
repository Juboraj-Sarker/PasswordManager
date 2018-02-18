package com.juborajsarker.passwordmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.juborajsarker.passwordmanager.model.ModelPassword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubor on 2/13/2018.
 */

public class DBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "passwordManager";
    private static final int DATABASE_VERSION = 1;


    String TABLE_NAME ;

    // Contacts Table Columns names
    private static final String COLUMN_1 = "ID";
    private static final String COLUMN_2 = "TITLE";
    private static final String COLUMN_3 = "PASSWORD";
    private static final String COLUMN_4 = "WEBSITE";
    private static final String COLUMN_5 = "HEADER";
    private static final String COLUMN_6 = "TYPE";
    private static final String COLUMN_7 = "EMAIL";


    private static final String BANK_TABLE = "bankTable";
    private static final String CRYPTO_TABLE = "cryptoTable";
    private static final String EMAIL_TABLE = "emailTable";
    private static final String OTHER_TABLE = "otherTable";
    private static final String SERVER_TABLE = "serverTable";
    private static final String SOCIAL_TABLE = "socialTable";


    public DBHelper(Context context, String TABLE_NAME){


        super( context, DATABASE_NAME, null, DATABASE_VERSION);
        this.TABLE_NAME = TABLE_NAME;


    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_BANK = "CREATE TABLE "
                + BANK_TABLE + "("
                + COLUMN_1 + " INTEGER PRIMARY KEY,"
                + COLUMN_2 + " TEXT,"
                + COLUMN_3 + " TEXT,"
                + COLUMN_4 + " TEXT,"
                + COLUMN_5 + " TEXT,"
                + COLUMN_6 + " TEXT,"
                + COLUMN_7 + " TEXT" + ")";


        String CREATE_TABLE_CRYPTO = "CREATE TABLE "
                + CRYPTO_TABLE + "("
                + COLUMN_1 + " INTEGER PRIMARY KEY,"
                + COLUMN_2 + " TEXT,"
                + COLUMN_3 + " TEXT,"
                + COLUMN_4 + " TEXT,"
                + COLUMN_5 + " TEXT,"
                + COLUMN_6 + " TEXT,"
                + COLUMN_7 + " TEXT" + ")";


        String CREATE_TABLE_EMAIL = "CREATE TABLE "
                + EMAIL_TABLE + "("
                + COLUMN_1 + " INTEGER PRIMARY KEY,"
                + COLUMN_2 + " TEXT,"
                + COLUMN_3 + " TEXT,"
                + COLUMN_4 + " TEXT,"
                + COLUMN_5 + " TEXT,"
                + COLUMN_6 + " TEXT,"
                + COLUMN_7 + " TEXT" + ")";


        String CREATE_TABLE_OTHERS = "CREATE TABLE "
                + OTHER_TABLE + "("
                + COLUMN_1 + " INTEGER PRIMARY KEY,"
                + COLUMN_2 + " TEXT,"
                + COLUMN_3 + " TEXT,"
                + COLUMN_4 + " TEXT,"
                + COLUMN_5 + " TEXT,"
                + COLUMN_6 + " TEXT,"
                + COLUMN_7 + " TEXT" + ")";


        String CREATE_TABLE_SERVER = "CREATE TABLE "
                + SERVER_TABLE + "("
                + COLUMN_1 + " INTEGER PRIMARY KEY,"
                + COLUMN_2 + " TEXT,"
                + COLUMN_3 + " TEXT,"
                + COLUMN_4 + " TEXT,"
                + COLUMN_5 + " TEXT,"
                + COLUMN_6 + " TEXT,"
                + COLUMN_7 + " TEXT" + ")";

        String CREATE_TABLE_SOCIAL = "CREATE TABLE "
                + SOCIAL_TABLE + "("
                + COLUMN_1 + " INTEGER PRIMARY KEY,"
                + COLUMN_2 + " TEXT,"
                + COLUMN_3 + " TEXT,"
                + COLUMN_4 + " TEXT,"
                + COLUMN_5 + " TEXT,"
                + COLUMN_6 + " TEXT,"
                + COLUMN_7 + " TEXT" + ")";





        db.execSQL(CREATE_TABLE_BANK);
        db.execSQL(CREATE_TABLE_CRYPTO);
        db.execSQL(CREATE_TABLE_EMAIL);
        db.execSQL(CREATE_TABLE_OTHERS);
        db.execSQL(CREATE_TABLE_SERVER);
        db.execSQL(CREATE_TABLE_SOCIAL);









    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public void insertData(ModelPassword modelPassword, String tableName){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_1, String.valueOf(modelPassword.getId()));
        values.put(COLUMN_2, modelPassword.getTitle());
        values.put(COLUMN_3, modelPassword.getPassword());
        values.put(COLUMN_4, modelPassword.getWebsite());
        values.put(COLUMN_5, String.valueOf(modelPassword.getHeader()));
        values.put(COLUMN_6, modelPassword.getType());
        values.put(COLUMN_7, modelPassword.getEmail());

        db.insert(tableName, null, values);
        db.close();


    }


    public ModelPassword getSinglePasswordObject (int id, String tableName){


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName,
                new String[] {
                        COLUMN_1,
                        COLUMN_2,
                        COLUMN_3,},
                COLUMN_1 + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ModelPassword modelPassword = new ModelPassword(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4).charAt(0),
                cursor.getString(5), cursor.getString(6));

        return modelPassword;

    }


    public List<ModelPassword> getAllData (String tableName){

        List<ModelPassword> passwordList = new ArrayList<ModelPassword>();

        String selectQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){

            do {

                ModelPassword modelPassword = new ModelPassword();
                modelPassword.setId(Integer.parseInt(cursor.getString(0)));
                modelPassword.setTitle(cursor.getString(1));
                modelPassword.setPassword(cursor.getString(2));
                modelPassword.setWebsite(cursor.getString(3));
                modelPassword.setHeader(cursor.getString(4).charAt(0));
                modelPassword.setType(cursor.getString(5));
                modelPassword.setEmail(cursor.getString(6));

                passwordList.add(modelPassword);

            }while (cursor.moveToNext());
        }


        return passwordList;
    }


    public int updatePassword(ModelPassword modelPassword, String tableName){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1, String.valueOf(modelPassword.getId()));
        values.put(COLUMN_2, modelPassword.getTitle());
        values.put(COLUMN_3, modelPassword.getPassword());
        values.put(COLUMN_4, modelPassword.getWebsite());
        values.put(COLUMN_5, String.valueOf(modelPassword.getHeader()));
        values.put(COLUMN_6, modelPassword.getType());
        values.put(COLUMN_7, modelPassword.getEmail());

        return db.update(tableName, values, COLUMN_1 + " = ?",
                new String[] { String.valueOf(modelPassword.getId()) });

    }



    public void deletePassword(ModelPassword modelPassword, String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tableName, COLUMN_1 + " = ?",
                new String[] { String.valueOf(modelPassword.getId()) });

        db.close();
    }
}
