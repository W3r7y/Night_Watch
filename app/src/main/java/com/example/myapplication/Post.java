package com.example.myapplication;

import java.io.Serializable;

public class Post implements Serializable {
    String post_type;
    int number_of_watchers;

    public Post(){
        this.post_type = null;
        this.number_of_watchers = 0;
    }
    public Post(String type, int number){
        this.post_type = type;
        this.number_of_watchers = number;
    }

    public String getPostType(){
        return this.post_type;
    }

    public int getNumberOfWatchers(){
        return this.number_of_watchers;
    }

    public void setPostType(String type){
        this.post_type = type;
    }

    public void setNumberOfWatchers(int num){
        this.number_of_watchers = num;
    }

}
