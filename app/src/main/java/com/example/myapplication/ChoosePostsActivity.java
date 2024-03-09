package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoosePostsActivity extends AppCompatActivity {

    public static final String POSTS_KEY = "com.example.myapplication.POSTS";
    public static final String TIME1_KEY = "com.example.myapplication.TIME1";
    public static final String TIME2_KEY = "com.example.myapplication.TIME2";
    static ArrayList<Post> posts;
    static int count;
    String time1;
    String time2;
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
        /*
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
        });*/

        count = 0; //initialization

        ImageButton addPost = (ImageButton) findViewById(R.id.add_post_button);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post generated_post = new Post();
                String post_desc = editText.getText().toString();
                generated_post.setPostType(post_desc);
                generated_post.setNumberOfWatchers((int)spinner.getSelectedItem());
                if (generated_post.getPostType() == null || generated_post.getNumberOfWatchers() == 0) {
                    Toast.makeText(getApplicationContext(), "Missing parameters", Toast.LENGTH_SHORT).show();
                }
                else{
                    posts.add(generated_post);

                    //Create TableRow
                    TableRow tr_head = new TableRow(getApplicationContext());
                    count += 10;
                    tr_head.setId(count);
                    tr_head.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    //Adding 2 data sections (Columns)
                    TextView post_type_tv = new TextView(getApplicationContext());
                    count += 10;
                    post_type_tv.setId(count);
                    post_type_tv.setTextSize(20);
                    post_type_tv.setText(generated_post.getPostType());
                    post_type_tv.setPadding(40, 5, 5, 5);
                    tr_head.addView(post_type_tv);

                    TextView post_num_of_watchers_tv = new TextView(getApplicationContext());
                    count += 10;
                    post_num_of_watchers_tv.setId(count);// define id that must be unique
                    post_num_of_watchers_tv.setText(Integer.toString(generated_post.getNumberOfWatchers()));
                    post_num_of_watchers_tv.setTextSize(20);
                    post_num_of_watchers_tv.setPadding(100, 5, 5, 5);
                    tr_head.addView(post_num_of_watchers_tv);

                    // Add TableRow to the Table
                    TableLayout postsTable = (TableLayout) findViewById(R.id.posts_table);
                    postsTable.addView(tr_head, new TableLayout.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    // Toast informing message
                    Toast.makeText(getApplicationContext(), "Post added", Toast.LENGTH_SHORT).show();
                }
                editText.setText("");
            }
        });

        //Todo: add on Continue button click functionality
        Button continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                time1 = intent.getStringExtra(PickTimeActivity.TIME1_KEY);
                time2 = intent.getStringExtra(PickTimeActivity.TIME2_KEY);
                Intent newIntent = new Intent(getApplicationContext(), ChoosePeopleActivity.class);
                newIntent.putExtra(POSTS_KEY, posts);
                newIntent.putExtra(TIME1_KEY, time1);
                newIntent.putExtra(TIME2_KEY, time2);
                startActivity(newIntent);

            }
        });
    }
}