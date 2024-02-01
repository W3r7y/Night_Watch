package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE_KEY = "com.example.myapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void buttonClick(View view) {
        //EditText editText = (EditText) findViewById(R.id.edit_text);
        //String message = editText.getText().toString();

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        // intent.putExtra(MESSAGE_KEY, message);
        startActivity(intent);
    }

    public void clickButtonTimePick(View view){

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Set time to text view
                TextView tv = (TextView) findViewById(R.id.tvForTime1);

                String hours = "";
                String minutes = "";
                if(hourOfDay < 10){
                    hours = "0" + hourOfDay;
                }else{
                    hours = "" + hourOfDay;
                }
                if(minute < 10){
                    minutes = "0" + minute;
                }else{
                    minutes = "" + minute;
                }

                tv.setText(hours + ":" + minutes);
            }
        }, hour, min, true);

        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    //Check
}