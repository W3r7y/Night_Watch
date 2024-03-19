package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.myapplication.R;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.textfield.TextInputEditText;


public class ProfileActivity extends AppCompatActivity {
    Button groups_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        groups_button = (Button) findViewById(R.id.groups_btn);
        groups_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View groups_layout = LayoutInflater.from(ProfileActivity.this)
                        .inflate(R.layout.groups_layout, null);


                new MaterialStyledDialog.Builder(ProfileActivity.this)
                        .setIcon(R.drawable.group_icon)
                        .setTitle(R.string.groups_title)
                        .setDescription(R.string.groups_description)
                        .setCustomView(groups_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@androidx.annotation.NonNull MaterialDialog dialog, @androidx.annotation.NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("Continue")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@androidx.annotation.NonNull MaterialDialog dialog, @androidx.annotation.NonNull DialogAction which) {

                            }
                        }).show();
            }
        });
    }

    public void make_shifts_click(View view) {
        Intent intent = new Intent(this, PickTimeActivity.class);
        startActivity(intent);
    }
}