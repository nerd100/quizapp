package com.example.dennis.quiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    Button addbtn, startbtn1, startbtn2;

    DatabaseHelper getDatabase;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase = new DatabaseHelper(this);
        getDatabase.openDatabase();

        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);

        startbtn1 = (Button) findViewById(R.id.btn_start1);
        startbtn2 = (Button) findViewById(R.id.btn_start2);
        addbtn = (Button) findViewById(R.id.addquestion);

        startbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor = shared_preferences.edit();
                shared_preferences_editor.putString("Number", "1");
                shared_preferences_editor.apply();

                startActivity(new Intent(MainActivity.this, Start.class));

            }
        });

        startbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor = shared_preferences.edit();
                shared_preferences_editor.putString("Number", "0");
                shared_preferences_editor.apply();
                startActivity(new Intent(MainActivity.this, Choose.class));
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

