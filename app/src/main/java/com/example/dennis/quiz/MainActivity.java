package com.example.dennis.quiz;

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

    String json;

    DatabaseHelper getDatabase;

    //SQLiteDatabase db = adb.openDatabase();
    protected void onCreate(Bundle savedInstanceState) {
        new BackgroundTask(this).execute("Get");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


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
                shared_preferences_editor.putString("Questions", json);
                shared_preferences_editor.commit();
                //Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Start.class));

            }
        });

        startbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor = shared_preferences.edit();
                shared_preferences_editor.putString("Number", "0");
                shared_preferences_editor.putString("Questions", json);
                shared_preferences_editor.commit();
                //Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
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


    class BackgroundTask extends AsyncTask<String, Void, String> {
        Context ctx;
        String json_url;
        String JSON_STRING;
        public String json_string;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        SharedPreferences shared_preferences;
        SharedPreferences.Editor shared_preferences_editor;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://quizdb.net23.net/json_get_data.php";
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //Toast.makeText(ctx,JSON_STRING,Toast.LENGTH_LONG).show();

                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onProgressUpdate(Void... values) {
            onProgressUpdate(values);
        }


        protected void onPostExecute(String result) {
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            json = result;
        }
    }
}

