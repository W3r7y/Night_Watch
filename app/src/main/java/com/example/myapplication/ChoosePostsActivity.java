package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoosePostsActivity extends AppCompatActivity {

    static ArrayList<Post> posts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_posts);

        EditText editText = (EditText) findViewById(R.id.post_name);
        Spinner spinner = (Spinner) findViewById(R.id.number_of_night_watchers);

        Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);

        posts = new ArrayList<>();
        Post generated_post = new Post();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // set the chosen option
                generated_post.setNumberOfWatchers(Integer.parseInt(parentView.getItemAtPosition(position).toString()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // make a joke
            }
        });


        ImageButton addPost = (ImageButton) findViewById(R.id.add_post_button);

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post_desc = editText.getText().toString();
                generated_post.setPostType(post_desc);
                if (generated_post.getPostType() == null || generated_post.getNumberOfWatchers() == 0) {
                    Toast.makeText(getApplicationContext(), "Missing parameters", Toast.LENGTH_SHORT).show();
                }
                else{
                    posts.add(generated_post);


                    //TODO: add generated post to table view and display its values


                    Toast.makeText(getApplicationContext(), "Post added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Todo: add on Continue button click functionality
    }
}