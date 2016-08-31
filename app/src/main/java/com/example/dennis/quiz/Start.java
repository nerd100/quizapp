package com.example.dennis.quiz;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Start extends AppCompatActivity {
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    String test_string;

    DatabaseHelper myDB;
    Choose choose;
    TextView que;
    Button btn1,btn2,btn3,btn4;
    String buttonName;
    String[] parts;
    int rightAnswer = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);

        test_string = shared_preferences.getString("Category", "Default");
        Toast.makeText(getApplicationContext(), test_string, Toast.LENGTH_SHORT)
                .show();
        Random r = new Random();
        rightAnswer = r.nextInt(4);

        myDB = new DatabaseHelper(this);
        choose = new Choose();

        buttonName=viewAll();
        parts = buttonName.split(",");

        que =  (TextView) findViewById(R.id.question1);
        que.setText(parts[0]);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);


        mixButtons(rightAnswer,parts[1],parts[2],parts[3],parts[4]);

    }

    public String viewAll(){
                        Cursor res = myDB.getAllData(shared_preferences.getString("Category","Default")
                                ,shared_preferences.getString("Difficulty","Default"));
                        if(res.getCount() == 0){
                            //show Message
                            //showMessage("Error","Nothing found!");
                            return "NoData";
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append(res.getString(0)+",");
                            buffer.append(res.getString(1)+",");
                            buffer.append(res.getString(2)+",");
                            buffer.append(res.getString(3)+",");
                            buffer.append(res.getString(4));
                        }
                        //Show all Data
                        return buffer.toString();

    }

    public void mixButtons(int i,String RA, String FA1, String FA2,String FA3){
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
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
