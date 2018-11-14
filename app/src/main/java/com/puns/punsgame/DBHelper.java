package com.puns.punsgame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2018-05-14.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "puns_game";

    //Table: PUNS
    private static final String TABLE_PUNS = "puns";
    //Columns name
    private static final String KEY_ID = "id";
    private static final String KEY_PUNS_CATEGORY = "category";
    private static final String KEY_PUNS_PASSWORD = "password";

    //String create table
    private static final String CREATE_PUNS_TABLE =
            "CREATE TABLE" + TABLE_PUNS + "("
            + KEY_ID + "INTEGER PRIMARY KEY,"
            + KEY_PUNS_CATEGORY + "TEXT,"
            +KEY_PUNS_PASSWORD + "TEXT" + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PUNS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUNS);
    }

    public void addNewPun(Pun id, Pun category, Pun password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id.getId());
        values.put(KEY_PUNS_CATEGORY, category.getCategory());
        values.put(KEY_PUNS_PASSWORD, password.getPassword());

        db.insert(TABLE_PUNS, null, values);
        db.close();
    }

    public int deletePun(String id){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_ID + " = ? ";
        String[] whereArgs = {id};

        int count = db.delete(TABLE_PUNS, whereClause, whereArgs);
        db.close();
        return count;
    }
    public void updatePun(String id, Pun category, Pun password){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_ID + " = ? ";
        String[] whereArgs = {id};

        ContentValues values = new ContentValues();
        values.put(KEY_PUNS_CATEGORY, category.getCategory());
        values.put(KEY_PUNS_PASSWORD, password.getPassword());
        db.update(TABLE_PUNS, values, whereClause, whereArgs);
        db.close();
    }

    public List<Pun> getAllCategory(){

        List<Pun> punCategoryList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PUNS;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Pun category = new Pun();
                category.setCategory(cursor.getString(0));
                if(!punCategoryList.equals(category)){
                    punCategoryList.add(category);
                }

            }while ((cursor.moveToNext()));
        }
        return punCategoryList;
    }

    public List<Pun> getAllPunsFromCategoty(String category){

        List<Pun> punsList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PUNS + " WHERE "
                + KEY_PUNS_CATEGORY + "='" + category + "'";

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Pun pun = new Pun();
                pun.setId(cursor.getInt(0));
                pun.setPassword(cursor.getString(2));

                punsList.add(pun);
            }while (cursor.moveToNext());
        }
        return punsList;
    }
}
