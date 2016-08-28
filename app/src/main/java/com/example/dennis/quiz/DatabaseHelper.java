package com.example.dennis.quiz;

import android.content.ContentValues;
import android.content.Context;
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
    public static  final String COL_3 = "Question";
    public static  final String COL_4 = "RightAnswer";
    public static  final String COL_5 = "FalseAnswer1";
    public static  final String COL_6 = "FalseAnswer2";
    public static  final String COL_7 = "FalseAnswer3";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Category TEXT, Question TEXT, RightAnswer TEXT, FalseAnswer1 TEXT," +
                " FalseAnswer2 TEXT, FalseAnswer3 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String question, String RA, String FA1, String FA2, String FA3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, question);
        contentValues.put(COL_3, RA);
        contentValues.put(COL_4, FA1);
        contentValues.put(COL_5, FA2);
        contentValues.put(COL_6, FA3);
        long result = db.insert(TABLE_NAME,null,contentValues); //insert returns -1 if fails
        if (result == -1)
            return false;
        else
            return true;
    }
}
