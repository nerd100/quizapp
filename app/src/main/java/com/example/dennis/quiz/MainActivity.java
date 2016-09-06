package com.example.dennis.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    Button addbtn, startbtn1, startbtn2, statisticbtn;

    DatabaseHelper getDatabase;
    //SQLiteDatabase db = adb.openDatabase();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDatabase = new DatabaseHelper(this);
        getDatabase.openDatabase();

        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);

        startbtn1 = (Button) findViewById(R.id.btn_start1);
        startbtn2 = (Button) findViewById(R.id.btn_start2);
        statisticbtn = (Button) findViewById(R.id.btn_statistic);
        addbtn = (Button) findViewById(R.id.addquestion);

        startbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor = shared_preferences.edit();
                shared_preferences_editor.putString("Number","1");
                shared_preferences_editor.commit();
                startActivity(new Intent(MainActivity.this, Start.class));

            }
        });

        startbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor = shared_preferences.edit();
                shared_preferences_editor.putString("Number","0");
                shared_preferences_editor.commit();
                startActivity(new Intent(MainActivity.this, Choose.class));
            }
        });

        statisticbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Statistics.class));
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddQuestion.class));
            }
        });

    }
}