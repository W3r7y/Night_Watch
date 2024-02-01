package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message_activities);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.MESSAGE_KEY);

        TextView textView = (TextView) findViewById(R.id.message_display_text_view);
        textView.setText(message);
    }
}