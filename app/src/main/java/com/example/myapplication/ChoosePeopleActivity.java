package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChoosePeopleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_people_activity);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.MESSAGE_KEY);

        TextView textView = (TextView) findViewById(R.id.choose_people_tv);
        textView.setText(message);
    }
}