package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String TIME1_KEY = "com.example.myapplication.TIME1";
    public static final String TIME2_KEY = "com.example.myapplication.TIME2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void buttonClick(View view) {
        TextView tv1 = (TextView) findViewById(R.id.tvForTime1);
        TextView tv2 = (TextView) findViewById(R.id.tvForTime2);

        String startingTime = tv1.getText().toString();
        String endingTime = tv2.getText().toString();

        Intent intent = new Intent(this, ChoosePeopleActivity.class);
        intent.putExtra(TIME1_KEY, startingTime);
        intent.putExtra(TIME2_KEY, endingTime);
        startActivity(intent);
    }

    public void clickTimePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        int buttonId = view.getId();
        int textViewId;

        if (buttonId == R.id.pickTime1) {
            textViewId = R.id.tvForTime1;
        } else if (buttonId == R.id.pickTime2) {
            textViewId = R.id.tvForTime2;
        } else {
            // Handle unexpected button click
            return;
        }

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                // Set time to the selected text view
                TextView tv = findViewById(textViewId);
                tv.setText(timeToString(hour, minute));
            }
        }, hour, min, true);

        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    public String timeToString(int hours, int minutes){
        String time = "";
        String hourStr = "";
        String minuteStr = "";

        if(hours < 10){
            hourStr = "0" + hours;
        }else{
            hourStr = "" + hours;
        }
        if(minutes < 10){
            minuteStr = "0" + minutes;
        }else{
            minuteStr = "" + minutes;
        }

        time = hourStr + ":" + minuteStr;
        return time;
    }

    //Check
}