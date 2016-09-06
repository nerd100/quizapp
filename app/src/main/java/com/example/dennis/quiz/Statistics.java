package com.example.dennis.quiz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by Lui on 04.09.2016.
 */
public class Statistics extends Activity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        shared_preferences = getSharedPreferences("shared_preferences_test",MODE_PRIVATE);
    }
}
