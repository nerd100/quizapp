package com.example.dennis.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.util.Log;
import android.widget.Toast;

public class Choose extends AppCompatActivity {
    public static final String GAME_PREFERENCES = "GamePrefs";
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    Button startbtn2;
    Spinner editSpinner1, editSpinner2; //Spinner in Choose

    String Category = "";
    String Difficulty = "";
    String test_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        editSpinner1 = (Spinner) findViewById(R.id.spinner1);
        editSpinner2 = (Spinner) findViewById(R.id.spinner2);

        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);

       // test_string = shared_preferences.getString("Category", "Default");

        //prefEditor.putString("Diff",editSpinner2.getSelectedItem().toString());
        //setCategory(editSpinner1.getSelectedItem().toString());
        //setDifficutly(editSpinner2.getSelectedItem().toString());

        //Log.d(TAG,"Hallo "+Category);

        startbtn2 = (Button) findViewById(R.id.start_btn2);

        startbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preferences_editor = shared_preferences.edit();
                shared_preferences_editor.putString("Category",editSpinner1.getSelectedItem().toString());
                shared_preferences_editor.putString("Difficulty",editSpinner2.getSelectedItem().toString());
                shared_preferences_editor.commit();
                startActivity(new Intent(Choose.this, Start.class));
                finish();
            }
        });


    }
}
