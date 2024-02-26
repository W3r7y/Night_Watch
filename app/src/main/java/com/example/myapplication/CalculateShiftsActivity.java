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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Date;

public class CalculateShiftsActivity extends AppCompatActivity {

    static ListView shiftsListView;
    static ShiftViewAdapter adapter;
    static ArrayList<String> names;
    static ArrayList<Post> posts;
    ImageView copyCalculatedShifts;
    static ArrayList<String> shifts;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_shifts);

        Intent intent = getIntent();
        String startingTime = intent.getStringExtra(ChoosePeopleActivity.TIME1_KEY);
        String endingTime = intent.getStringExtra(ChoosePeopleActivity.TIME2_KEY);
        names = intent.getStringArrayListExtra(ChoosePeopleActivity.NAMES_KEY);
        posts = (ArrayList<Post>) intent.getSerializableExtra(ChoosePeopleActivity.POSTS_KEY);
        shifts = new ArrayList<>();

        Collections.shuffle(names);

        List<ArrayList<String>> subLists = splitArrayList(names, posts);

        String shiftBeginning = startingTime;
        for(int i=0; i < subLists.size(); i++){
            String shiftName = posts.get(i).getPostType();
            shifts.add(shiftName);

            String timeDifference = TimeUtils.calculateDifferenceBetweenTime(startingTime, endingTime);
            int minutes_per_shift = TimeUtils.calculateShiftTimeInMinnutes(
                    timeDifference, Math.round(subLists.get(i).size() / posts.get(i).getNumberOfWatchers()));

            int count = 0;

            while(subLists.get(i).size() != count &&
                    subLists.get(i).size() > count){

                String shiftEnding = TimeUtils.addMinutesToTime(shiftBeginning, minutes_per_shift);
                StringBuilder shift = new StringBuilder(shiftBeginning + "-" + shiftEnding + "\t\t");
                for(int j=count; j - count< posts.get(i).getNumberOfWatchers() ; j++ ){
                    shift.append(subLists.get(i).get(j)).append(" \t");
                }
                shifts.add(shift.toString());
                shiftBeginning = shiftEnding;
                count += posts.get(i).getNumberOfWatchers();
            }
        }

        TextView textView = (TextView) findViewById(R.id.shifts_tv);
        textView.setText("Shifts");


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

    private static <T> List<ArrayList<T>> splitArrayList(ArrayList<T> original, ArrayList<Post> posts) {
        List<ArrayList<T>> subLists = new ArrayList<>();

        int total_watchers_in_paralel = 0;
        for(int i=0; i < posts.size(); i++){
            total_watchers_in_paralel += posts.get(i).getNumberOfWatchers();
        }

        for (int i = 0; i < original.size(); i += Math.round(original.size() *
                (posts.get(i).getNumberOfWatchers() / total_watchers_in_paralel))) {
            int end = Math.min(i + Math.round(original.size() *
                            (posts.get(i).number_of_watchers / total_watchers_in_paralel)),
                    original.size());
            subLists.add(new ArrayList<>(original.subList(i, end)));
        }

        return subLists;
    }
}