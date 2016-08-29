package com.example.dennis.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dennis on 25.08.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static  final String DATABASE_NAME = "Quiz.db";
    public static  final String TABLE_NAME = "quiz_table";
    public static  final String COL_1 = "ID";
    public static  final String COL_2 = "Category";
    public static  final String COL_3 = "Difficulty";
    public static  final String COL_4 = "Question";
    public static  final String COL_5 = "RightAnswer";
    public static  final String COL_6 = "FalseAnswer1";
    public static  final String COL_7 = "FalseAnswer2";
    public static  final String COL_8 = "FalseAnswer3";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Category TEXT, Difficulty TEXT, Question TEXT, RightAnswer TEXT, FalseAnswer1 TEXT," +
                " FalseAnswer2 TEXT, FalseAnswer3 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String category, String difficulty, String question, String RA, String FA1, String FA2, String FA3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, category);
        contentValues.put(COL_3, difficulty);
        contentValues.put(COL_4, question);
        contentValues.put(COL_5, RA);
        contentValues.put(COL_6, FA1);
        contentValues.put(COL_7, FA2);
        contentValues.put(COL_8, FA3);
        long result = db.insert(TABLE_NAME,null,contentValues); //insert returns -1 if fails
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(String category,String diff){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Question,RightAnswer,FalseAnswer1,FalseAnswer2,FalseAnswer3" +
                " from "+ TABLE_NAME+" WHERE (Category =  \""+category+"\") AND (Difficulty=\""+diff+"\")",null);
        return res;
    }
}
