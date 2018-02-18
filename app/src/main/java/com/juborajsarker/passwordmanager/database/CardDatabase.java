package com.juborajsarker.passwordmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.juborajsarker.passwordmanager.model.CardModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubor on 2/16/2018.
 */

public class CardDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cardManager";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_NAME = "cardTable" ;

    // Contacts Table Columns names
    private static final String COLUMN_1 = "ID";
    private static final String COLUMN_2 = "BANK_NAME";
    private static final String COLUMN_3 = "NAME_ON_CARD";
    private static final String COLUMN_4 = "CARD_NUMBER";
    private static final String COLUMN_5 = "PIN";
    private static final String COLUMN_6 = "CCV";
    private static final String COLUMN_7 = "MONTH";
    private static final String COLUMN_8 = "YEAR";
    private static final String COLUMN_9 = "HEADER";
    private static final String COLUMN_10 = "TYPE";

    public CardDatabase(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_TABLE_CARD = "CREATE TABLE "
                + TABLE_NAME + "("
                + COLUMN_1 + " INTEGER PRIMARY KEY,"
                + COLUMN_2 + " TEXT,"
                + COLUMN_3 + " TEXT,"
                + COLUMN_4 + " TEXT,"
                + COLUMN_5 + " TEXT,"
                + COLUMN_6 + " TEXT,"
                + COLUMN_7 + " TEXT,"
                + COLUMN_8 + " TEXT,"
                + COLUMN_9 + " TEXT,"
                + COLUMN_10 + " TEXT" + ")";

        db.execSQL(CREATE_TABLE_CARD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public void insertData(CardModel cardModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_1, String.valueOf(cardModel.getID()));
        values.put(COLUMN_2, cardModel.getBankName());
        values.put(COLUMN_3, cardModel.getNameOnCard());
        values.put(COLUMN_4, cardModel.getCardNumber());
        values.put(COLUMN_5, cardModel.getPin());
        values.put(COLUMN_6, cardModel.getCcv());
        values.put(COLUMN_7, cardModel.getValidityMonth());
        values.put(COLUMN_8, cardModel.getValidityYear());
        values.put(COLUMN_9, String.valueOf(cardModel.getHeader()));
        values.put(COLUMN_10, cardModel.getType());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public List<CardModel> getAllData(){

        List<CardModel> cardModelList = new ArrayList<CardModel>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){


            do {

                CardModel cardModel = new CardModel();

                cardModel.setID(Integer.parseInt(cursor.getString(0)));
                cardModel.setBankName(cursor.getString(1));
                cardModel.setNameOnCard(cursor.getString(2));
                cardModel.setCardNumber(cursor.getString(3));
                cardModel.setPin(cursor.getString(4));
                cardModel.setCcv(cursor.getString(5));
                cardModel.setValidityMonth(cursor.getString(6));
                cardModel.setValidityYear(cursor.getString(7));
                cardModel.setHeader(cursor.getString(8).charAt(0));
                cardModel.setType(cursor.getString(9));

                cardModelList.add(cardModel);

            }while (cursor.moveToNext());
        }



        return cardModelList;
    }


    public int updateCard(CardModel cardModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_1, String.valueOf(cardModel.getID()));
        values.put(COLUMN_2, cardModel.getBankName());
        values.put(COLUMN_3, cardModel.getNameOnCard());
        values.put(COLUMN_4, cardModel.getCardNumber());
        values.put(COLUMN_5, cardModel.getPin());
        values.put(COLUMN_6, cardModel.getCcv());
        values.put(COLUMN_7, cardModel.getValidityMonth());
        values.put(COLUMN_8, cardModel.getValidityYear());
        values.put(COLUMN_9, String.valueOf(cardModel.getHeader()));
        values.put(COLUMN_10, cardModel.getType());


       return db.update(TABLE_NAME, values, COLUMN_1 + " = ?",
                new String[] { String.valueOf(cardModel.getID()) } );

    }


    public void deleteCard(CardModel cardModel){


        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_1 + " = ?",
                new String[] { String.valueOf(cardModel.getID() ) });


    }
}
