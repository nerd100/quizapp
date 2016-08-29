package com.example.dennis.quiz;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {
    DatabaseHelper myDB;
    Button btn1,btn2,btn3,btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        myDB = new DatabaseHelper(this);
        btn1 = (Button) findViewById(R.id.button1);
        viewAll();
    }

    public void viewAll(){
        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDB.getAllData("Assi","Hard");
                        if(res.getCount() == 0){
                            //show Message
                            showMessage("Error","Nothing found!");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Question :"+ res.getString(0)+"\n");
                            buffer.append("RA :"+res.getString(1)+"\n");
                            buffer.append("FA1 :"+res.getString(2)+"\n");
                            buffer.append("FA2 :"+res.getString(3)+"\n");
                            buffer.append("FA3 :"+res.getString(4)+"\n");
                        }
                        //Show all Data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
