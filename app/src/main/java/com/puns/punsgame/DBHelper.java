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
    private static final String KEY_GAME_TIME = "game_time";

    //String create table
    private static final String CREATE_PUNS_TABLE =
            "CREATE TABLE " + TABLE_PUNS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_PUNS_CATEGORY + " TEXT,"
                    + KEY_PUNS_PASSWORD + " TEXT,"
                    + KEY_GAME_TIME + " INTEGER" + ")";

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

    public void addNewPun(Pun category, Pun password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PUNS_CATEGORY, category.getCategory());
        values.put(KEY_PUNS_PASSWORD, password.getPassword());
        values.put(KEY_GAME_TIME, 1);

        db.insert(TABLE_PUNS, null, values);
        db.close();
    }
    public int deleteAllData(){
        SQLiteDatabase db = getWritableDatabase();
        int count = db.delete(TABLE_PUNS,null, null);
        db.close();
        return count;
    }

    public int deletePun(String category,  String password){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_PUNS_CATEGORY + " = ? and "
                + KEY_PUNS_PASSWORD + " = ? ";
        String[] whereArgs = {category, password};

        int count = db.delete(TABLE_PUNS, whereClause, whereArgs);
        db.close();
        return count;
    }
    public void updateGameTime(String id, Pun gameTime){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_ID + " = ? ";
        String[] whereArgs = {id};
        ContentValues values = new ContentValues();
        values.put(KEY_GAME_TIME, gameTime.getGameTime());
        db.update(TABLE_PUNS, values, whereClause, whereArgs);
        db.close();
    }
    public void updatePun(String category, String password, Pun punCategory, Pun punPassword){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_PUNS_CATEGORY + " = ? and "
                + KEY_PUNS_PASSWORD + " = ? ";
        String[] whereArgs = {category, password};

        ContentValues values = new ContentValues();
        values.put(KEY_PUNS_CATEGORY, punCategory.getCategory());
        values.put(KEY_PUNS_PASSWORD, punPassword.getPassword());
        db.update(TABLE_PUNS, values, whereClause, whereArgs);
        db.close();
    }

    public Pun getPunsDataSql(String id){
        Pun pun  = new Pun();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = " SELECT * FROM " + TABLE_PUNS + " WHERE "
                + KEY_ID + "='" + id + "'";

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                pun = new Pun();
                pun.setCategory(cursor.getString(1));
                pun.setPassword(cursor.getString(2));
            }while(cursor.moveToNext());
        }
        return pun;
    }

    public Pun getGameTimeSql(String id){
        Pun pun = new Pun();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = " SELECT * FROM " + TABLE_PUNS + " WHERE "
                + KEY_ID + "='" + id + "'";

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                pun = new Pun();
                pun.setGameTime(cursor.getInt(3));
            }while (cursor.moveToNext());
        }
        return pun;
    }
    public List<Pun> getAllSqlData(){
        List<Pun> punList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PUNS;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Pun pun = new Pun();
                pun.setId(cursor.getInt(0));
                pun.setCategory(cursor.getString(1));
                pun.setPassword(cursor.getString(2));
                pun.setGameTime(cursor.getInt(3));

                punList.add(pun);
            }while (cursor.moveToNext());
        }
        return punList;
    }

    public List<Pun> getAllCategory(){

        List<Pun> punCategoryList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PUNS;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Pun pun = new Pun();
                pun.setCategory(cursor.getString(1));
                if(!punCategoryList.equals(pun)){
                    punCategoryList.add(pun);
                }

            }while ((cursor.moveToNext()));
        }
        return punCategoryList;
    }

    public List<Pun> getAllPunsFromCategory(String category){

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
