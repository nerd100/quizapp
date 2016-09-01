package com.example.dennis.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.jar.Attributes;

public class Start extends AppCompatActivity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    DatabaseHelper myDB;
    TextView question;
    Button btn1,btn2,btn3,btn4;
    ArrayList QuestionAndButtons;
    String[] QuestionAndButtonsParts;
    String firstQuestion="";
    int rightAnswer = 0;
    Random rand = new Random();
    Random r = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        shared_preferences = getSharedPreferences("shared_preferences_test",MODE_PRIVATE);
        //call database
        myDB = new DatabaseHelper(this);
        //generate number for right answer
        rightAnswer = r.nextInt(4);
        //get all Questions and all answer options;get a part of the database
        QuestionAndButtons=allQuestion();

        firstQuestion=Question(QuestionAndButtons);
        QuestionAndButtonsParts = firstQuestion.split(",");

        NameButtons();
    }

    public String Question(ArrayList questionList){
        String question;
        int i = rand.nextInt(questionList.size());
        question = questionList.get(i).toString();
        questionList.remove(i);
        return question;
    }

    public void next(){
        rightAnswer = r.nextInt(4);
        firstQuestion=Question(QuestionAndButtons);
        QuestionAndButtonsParts = firstQuestion.split(",");
        NameButtons();
    }

    public void NameButtons(){
        question = (TextView) findViewById(R.id.question1);
        question.setText(QuestionAndButtonsParts[0]);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        //mix all buttons
        mixButtons(rightAnswer,QuestionAndButtonsParts[1],QuestionAndButtonsParts[2],QuestionAndButtonsParts[3],QuestionAndButtonsParts[4]);

    }
    public ArrayList allQuestion(){
                        //position in database
                        Cursor res = myDB.getAllData(shared_preferences.getString("Category","Default")
                                ,shared_preferences.getString("Difficulty","Default"));

                        ArrayList<String> list=new ArrayList<String>();

                        if(res.getCount() == 0){
                            //show error on screen
                            Toast.makeText(getApplicationContext(), "Please insert data", Toast.LENGTH_SHORT)
                                    .show();
                            list.add("0,0,0,0,0");
                            return list;//"No Question,3RR()R,3RR()R,3RR()R,3RR()R";
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append(res.getString(0)+",");
                            buffer.append(res.getString(1)+",");
                            buffer.append(res.getString(2)+",");
                            buffer.append(res.getString(3)+",");
                            buffer.append(res.getString(4));
                            list.add(buffer.toString());
                            buffer.delete(0,buffer.length());
                        }
                        res.close();
                        return list;

    }



    public void mixButtons(int i, final String RA, String FA1, String FA2, String FA3){
        switch(i){
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
                if (btn1.getText() == RA){
                    Toast.makeText(getApplicationContext(), "10Punkte", Toast.LENGTH_SHORT)
                            .show();
                    if(QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                            .show();
                    if(QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn2.getText() == RA){
                    Toast.makeText(getApplicationContext(), "Korrekt", Toast.LENGTH_SHORT)
                            .show();
                    if(QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                        .show();
                    if(QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn3.getText() == RA){
                    Toast.makeText(getApplicationContext(), "Korrekt", Toast.LENGTH_SHORT)
                            .show();
                    if(QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
                else {
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
                if (btn4.getText() == RA){
                    Toast.makeText(getApplicationContext(), "Korrekt", Toast.LENGTH_SHORT)
                            .show();
                    if(QuestionAndButtons.size() > 0)
                        next();
                    else
                        startActivity(new Intent(Start.this, MainActivity.class));
                }
                else {
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

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }


}
