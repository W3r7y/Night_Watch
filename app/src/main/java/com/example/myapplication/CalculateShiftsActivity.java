package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;

public class CalculateShiftsActivity extends AppCompatActivity {

    static ListView shiftsListView;
    static ShiftViewAdapter adapter;
    static ArrayList<String> names;
    ImageView copyCalculatedShifts;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_shifts);

        Intent intent = getIntent();
        String startingTime = intent.getStringExtra(ChoosePeopleActivity.TIME1_KEY);
        String endingTime = intent.getStringExtra(ChoosePeopleActivity.TIME2_KEY);
        names = intent.getStringArrayListExtra(ChoosePeopleActivity.NAMES_KEY);

        String timeDifference = TimeUtils.calculateDifferenceBetweenTime(startingTime, endingTime);
        int minutes_per_shift = TimeUtils.calculateShiftTimeInMinnutes(timeDifference, names.size());

        TextView textView = (TextView) findViewById(R.id.shifts_tv);
        textView.setText("Shift duration: " + minutes_per_shift + " minutes");

        Random rand = new Random();
        ArrayList<String> shifts = new ArrayList<>();
        String shiftBeginning = startingTime;

        while(names.size() != 0){
            String shift = "";
            int index = rand.nextInt(names.size());
            String shiftEnding = TimeUtils.addMinutesToTime(shiftBeginning, minutes_per_shift);

            if(names.size() == 1){
                shiftEnding = endingTime; // Final shift
            }
            shift = shiftBeginning + "-" + shiftEnding + "\t\t" + names.get(index);
            shifts.add(shift);
            names.remove(index);
            shiftBeginning = shiftEnding; // next shift starts when previous ends
        }

        shiftsListView = findViewById(R.id.shifts_list_view);
        adapter = new ShiftViewAdapter(this, shifts);
        shiftsListView.setAdapter(adapter);

        // Copy functionality for the ability to send the shifts in chat
        copyCalculatedShifts = findViewById(R.id.copy_shifts_image);
        copyCalculatedShifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shiftsText = "";

                for(int i=0; i<shifts.size(); i++){
                    shiftsText = shiftsText + shifts.get(i) + "\n";
                }

                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", shiftsText);
                clipboard.setPrimaryClip(clip);
                String copyStr = "Copied";
                Toast.makeText(getApplicationContext(), copyStr, Toast.LENGTH_SHORT).show();
            }
        });
    }
}