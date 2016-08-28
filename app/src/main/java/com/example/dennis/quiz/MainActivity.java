package com.example.dennis.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editQuestion, editRA, editFA1, editFA2, editFA3;
    Button btnAddData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        editQuestion = (EditText) findViewById(R.id.editQuestion);
        editRA = (EditText) findViewById(R.id.editRightAnswer);
        editFA1 = (EditText) findViewById(R.id.editFalseAnswer1);
        editFA2 = (EditText) findViewById(R.id.editFalseAnswer2);
        editFA3 = (EditText) findViewById(R.id.editFalseAnswer3);
        btnAddData = (Button) findViewById(R.id.button_add);
        AddData();
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(editQuestion.getText().toString(),
                                        editRA.getText().toString(),
                                        editFA1.getText().toString(),
                                        editFA2.getText().toString(),
                                        editFA3.getText().toString());
                        if(isInserted = true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }
}
