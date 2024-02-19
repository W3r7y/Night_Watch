package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalculateShiftsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_shifts);

        Intent intent = getIntent();
        String startingTime = intent.getStringExtra(ChoosePeopleActivity.TIME1_KEY);
        String endingTime = intent.getStringExtra(ChoosePeopleActivity.TIME2_KEY);
        ArrayList<String> names = intent.getStringArrayListExtra(ChoosePeopleActivity.NAMES_KEY);

        String timeDifference = TimeUtils.calculateDifferenceBetweenTime(startingTime, endingTime);
        int minutes_per_shift = TimeUtils.calculateShiftTimeInMinnutes(timeDifference, names.size());
        TextView textView = (TextView) findViewById(R.id.shifts_tv);


        textView.setText("Total time per shift: " + minutes_per_shift);

    }
}