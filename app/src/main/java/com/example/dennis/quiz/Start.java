package com.example.dennis.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.jar.Attributes;

public class Start extends AppCompatActivity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    DatabaseHelper myDB;
    TextView question;
    Button btn1, btn2, btn3, btn4;
    ArrayList QuestionAndButtons;
    String[] QuestionAndButtonsParts;
    String firstQuestion = "";
    String whichQuiz = "";
    int rightAnswer = 0;
    Random rand = new Random();
    Random r = new Random();
    public String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    QuestionAdapter questionAdapter;

    String json_url;
    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new BackgroundTask(this).execute("Get");
        //backgroundTask.execute("GET");

        ArrayList<String> list = new ArrayList<String>();
        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
        whichQuiz = shared_preferences.getString("Number", "Default");
        String a = shared_preferences.getString("Questions", "Default");

/*
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String Question,RA,FA1,FA2,FA3;
            while (count <jsonObject.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                Question = JO.getString("Question");
                RA = JO.getString("RA");
                FA1 = JO.getString("FA1");
                FA2 = JO.getString("FA2");
                FA3 = JO.getString("FA3");
                Questions questions = new Questions(Question,RA,FA1,FA2,FA3);
                count++;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), list.indexOf(1), Toast.LENGTH_SHORT).show();

*/
        String[] b = a.split("\\{");
        //Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
        myDB = new DatabaseHelper(this);

        //generate number for right answer
        rightAnswer = r.nextInt(4);
        //get all Questions and all answer options;get a part of the database
        QuestionAndButtons = allQuestions();

        firstQuestion = Question(QuestionAndButtons);
        QuestionAndButtonsParts = firstQuestion.split(";");

        NameButtons();
        getData();
        Toast.makeText(getApplicationContext(), "Hallo"+json_string, Toast.LENGTH_SHORT).show();
    }

    public String Question(ArrayList questionList) {
        String question;
        int i = rand.nextInt(questionList.size());
        question = questionList.get(i).toString();
        questionList.remove(i);
        return question;
    }

    public void next() {
        rightAnswer = r.nextInt(4);
        firstQuestion = Question(QuestionAndButtons);
        QuestionAndButtonsParts = firstQuestion.split(";");
        NameButtons();
    }

    public void NameButtons() {
        question = (TextView) findViewById(R.id.question1);
        question.setText(QuestionAndButtonsParts[0]);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        //mix all buttons
        mixButtons(rightAnswer, QuestionAndButtonsParts[1], QuestionAndButtonsParts[2], QuestionAndButtonsParts[3], QuestionAndButtonsParts[4]);

    }

    public ArrayList allQuestions() {
        int QuestionCounter = 0;
        Cursor res;
        Random row = new Random();
        StringBuffer buffer = new StringBuffer();
        ArrayList<String> list = new ArrayList<String>();

        Toast.makeText(getApplicationContext(), whichQuiz, Toast.LENGTH_SHORT).show();

        if (whichQuiz == "0") {
            //position in database
            res = myDB.getAllData(shared_preferences.getString("Category", "Default")
                    , shared_preferences.getString("Difficulty", "Default"));
            if (res.getCount() == 0) {
                //show error on screen
                Toast.makeText(getApplicationContext(), "Please insert data", Toast.LENGTH_SHORT).show();
                list.add("No Question,3RR()R,3RR()R,3RR()R,3RR()R");
                return list;
            }

            while (res.moveToNext() && QuestionCounter <= 10) {
                buffer.append(res.getString(0) + ";");
                buffer.append(res.getString(1) + ";");
                buffer.append(res.getString(2) + ";");
                buffer.append(res.getString(3) + ";");
                buffer.append(res.getString(4));
                list.add(buffer.toString());
                buffer.delete(0, buffer.length());
                QuestionCounter += 1;
            }
            //res.close();
            return list;


        } else {
            ArrayList<String> list2 = new ArrayList<String>();
            res = myDB.getAllData("Hard");

            list2 = fill("Easy", 1);
            //res.close();
            list.addAll(list2);
            list2 = fill("Medium", 1);
            list.addAll(list2);
            list2 = fill("Hard", 5);
            list.addAll(list2);
            return list;


            //list2.addAll(fill("Medium",row,QuestionCounter,buffer));
            //list2.addAll(fill("Hard",row,QuestionCounter,buffer));
            //return fill("Easy");

        }
        //list.add("No ,3RR()R,3RR()R,3RR()R,3RR()R");
        //return list;
    }


    public ArrayList fill(String diff, int j) {
        int i = 0;
        StringBuffer buffer = new StringBuffer();
        ArrayList<String> list = new ArrayList<>();
        Random row = new Random();
        Cursor res = myDB.getAllData(diff);
        if (res.getCount() == 0) {
            //show error on screen
            Toast.makeText(getApplicationContext(), "Please insert data", Toast.LENGTH_SHORT).show();
            list.add("No Question,3RR()R,3RR()R,3RR()R,3RR()R");
            return list;
        }
        res.moveToPosition(row.nextInt(res.getCount()));
        while (res.moveToNext() && i < j) {
            buffer.append(res.getString(0) + ";");
            buffer.append(res.getString(1) + ";");
            buffer.append(res.getString(2) + ";");
            buffer.append(res.getString(3) + ";");
            buffer.append(res.getString(4));
            list.add(buffer.toString());
            buffer.delete(0, buffer.length());
            i += 1;
        }
        //res.close();
        return list;
    }


    public void mixButtons(int i, final String RA, String FA1, String FA2, String FA3) {
        switch (i) {
            case 0:
                btn1.setText(RA);
                btn2.setText(FA1);
                btn3.setText(FA2);
                btn4.setText(FA3);
                break;
            case 1:
                btn1.setText(FA1);
                btn2.setText(RA);
                btn3.setText(FA2);
                btn4.setText(FA3);
                break;
            case 2:
                btn1.setText(FA2);
                btn2.setText(FA1);
                btn3.setText(RA);
                btn4.setText(FA3);
                break;
            case 3:
                btn1.setText(FA3);
                btn2.setText(FA1);
                btn3.setText(FA2);
                btn4.setText(RA);
                break;
            default:
                break;
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn1.getText() == RA) {
                    Toast.makeText(getApplicationContext(), "10Punkte", Toast.LENGTH_SHORT)
                            .show();
                    if (QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                            .show();
                    if (QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn2.getText() == RA) {
                    Toast.makeText(getApplicationContext(), "Korrekt", Toast.LENGTH_SHORT)
                            .show();
                    if (QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                            .show();
                    if (QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn3.getText() == RA) {
                    Toast.makeText(getApplicationContext(), "Korrekt", Toast.LENGTH_SHORT)
                            .show();
                    if (QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                            .show();
                    if (QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn4.getText() == RA) {
                    Toast.makeText(getApplicationContext(), "Korrekt", Toast.LENGTH_SHORT)
                            .show();
                    if (QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                            .show();
                    if (QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
            }
        });
    }


    public void getData() {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Context ctx;
            String json_url;
            String JSON_STRING;
            String result;


            SharedPreferences shared_preferences;
            SharedPreferences.Editor shared_preferences_editor;


            @Override
            protected String doInBackground(String... params) {

                try {

                    json_url = "http://quizdb.net23.net/json_get_data.php";
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

                    result = stringBuilder.toString();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;

            }

            @Override
            protected void onPostExecute(String result) {
                json_string = result;
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }
            GetDataJSON g = new GetDataJSON();
            g.execute();
    }



}