package com.example.dennis.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Dennis on 25.08.2016.
 */
public class AddQuestion extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editQuestion, editRA, editFA1, editFA2, editFA3;
    Button btnAddData;
    Spinner editSpinner1,editSpinner2;
    boolean isInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        myDB = new DatabaseHelper(this);

        editSpinner1 = (Spinner) findViewById(R.id.spin);
        editSpinner2 = (Spinner) findViewById(R.id.spin2);
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
                        isInserted = myDB.insertData(
                                editSpinner1.getSelectedItem().toString(),
                                editSpinner2.getSelectedItem().toString(),
                                editQuestion.getText().toString(),
                                editRA.getText().toString(),
                                editFA1.getText().toString(),
                                editFA2.getText().toString(),
                                editFA3.getText().toString());
                        if (isInserted = true)
                            Toast.makeText(AddQuestion.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AddQuestion.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );

    }
}
