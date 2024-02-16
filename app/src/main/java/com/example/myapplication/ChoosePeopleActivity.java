package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChoosePeopleActivity extends AppCompatActivity {

    static ListView listView;
    static ArrayList<String> names;
    static ListViewAdapter adapter;
    EditText input;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_people_activity);

        Intent intent = getIntent();
        String time1 = intent.getStringExtra(MainActivity.TIME1_KEY);
        String time2 = intent.getStringExtra(MainActivity.TIME2_KEY);

        TextView textView = (TextView) findViewById(R.id.time_difference_tv);
        textView.setText("Total time: " + calculateDifferenceBetweenTime(time1, time2));

        listView = findViewById(R.id.list_view);
        input = findViewById(R.id.input);
        add = findViewById(R.id.add);

        names = new ArrayList<>();
        adapter = new ListViewAdapter(getApplicationContext(), names);
        listView.setAdapter(adapter);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if(text == null || text.length() == 0){
                    makeToast("Enter a Name");
                }
                else{
                    addName(text);
                    input.setText("");
                    makeToast("Added: " + text);
                }
            }
        });


        /*
        //TODO: custom style for spinner
        Spinner spinner =(Spinner) findViewById(R.id.spinner);
        Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,100};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // calculate time for each shift


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // make a joke
            }
        });
         */
    }

    public void addName(String name){
        names.add(name);
        listView.setAdapter(adapter);
    }

    public static void removeName(int index){
        names.remove(index);
        adapter.notifyDataSetChanged();
    }
    Toast t;
    private void makeToast(String s){
        if (t != null) {
            t.cancel();
        }
        t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }

    public String calculateDifferenceBetweenTime(String t1, String t2){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date time1 = format.parse(t1);
            Date time2 = format.parse(t2);

            // Calculate the difference in milliseconds
            long differenceMillis = time2.getTime() - time1.getTime();

            // Handle differences across midnight
            if (differenceMillis < 0) {
                differenceMillis += 24 * 60 * 60 * 1000; // Add 24 hours
            }

            // Convert milliseconds to hours and minutes
            long hours = differenceMillis / (60 * 60 * 1000);
            long minutes = (differenceMillis / (60 * 1000)) % 60;

            // Format the result as "HH:mm"
            return String.format("%02d:%02d", hours, minutes);

        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid time format";
        }
    }

    public int calculateShiftTimeInMinnutes(String totalTime, int people){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try{
            Date time = format.parse(totalTime);
            int minutes_per_shift = 0;

            long minutes = time.getTime() / (60 * 1000);
            minutes_per_shift = (int) minutes / people;

            return minutes_per_shift;

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

}