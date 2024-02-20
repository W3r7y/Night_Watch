package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ShiftViewAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    //Constructor
    public ShiftViewAdapter(Context context, ArrayList<String> shifts) {
        super(context, R.layout.shifts_list_row, shifts);
        this.context = context;
        list = shifts;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.shifts_list_row, null);

            TextView number = convertView.findViewById(R.id.shift_number);
            number.setText(position + 1 + ".");

            TextView shift = convertView.findViewById(R.id.shift);
            shift.setText(list.get(position));
        }
        return convertView;
    }

}
