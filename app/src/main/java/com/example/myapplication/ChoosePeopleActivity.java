package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    public static final String NAMES_KEY = "com.example.myapplication.NAMES";
    public static final String TIME1_KEY = "com.example.myapplication.TIME1";
    public static final String TIME2_KEY = "com.example.myapplication.TIME2";

    static ListView listView;
    static ArrayList<String> names;
    static ListViewAdapter adapter;
    static String startingTime;
    static String endingTime;
    EditText input;
    ImageView add;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_people_activity);

        Intent intent = getIntent();
        startingTime = intent.getStringExtra(MainActivity.TIME1_KEY);
        endingTime = intent.getStringExtra(MainActivity.TIME2_KEY);

        TextView textView = (TextView) findViewById(R.id.time_difference_tv);
        textView.setText("Total time: " + TimeUtils.calculateDifferenceBetweenTime(startingTime, endingTime));

        listView = findViewById(R.id.list_view);
        input = findViewById(R.id.input);
        add = findViewById(R.id.add);

        if(savedInstanceState == null){
            names = new ArrayList<>();
        }

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
        listView.setAdapter(adapter);
    }

    Toast t;
    private void makeToast(String s){
        if (t != null) {
            t.cancel();
        }
        t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }

    public void clickCalculateShifts(View view){

        Intent intent = new Intent(this, CalculateShiftsActivity.class);
        intent.putExtra(TIME1_KEY, startingTime);
        intent.putExtra(TIME2_KEY, endingTime);
        intent.putExtra(NAMES_KEY, names);
        startActivity(intent);

    }

    //Making sure that the data is not lost while user rotates the screen
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putSerializable(NAMES_KEY, names);

        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        if (savedInstanceState != null) {
            names = (ArrayList<String>) savedInstanceState.getSerializable(NAMES_KEY);
            ListViewAdapter adapter = new ListViewAdapter(this, names);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(adapter);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

}