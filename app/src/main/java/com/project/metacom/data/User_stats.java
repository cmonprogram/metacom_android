package com.project.metacom.data;

import android.graphics.Bitmap;

import org.json.JSONObject;

public class User_stats {
    public String comment_sum;
    public String like_sum;
    public String dislike_sum;


    public User_stats fromJson(JSONObject oneObject) {
        this.comment_sum = oneObject.optString("comment_sum");
        this.like_sum = oneObject.optString("like_sum");
        if(this.like_sum == "null"){this.like_sum = "0";}
        this.dislike_sum = oneObject.optString("dislike_sum");
        if(this.dislike_sum == "null"){this.dislike_sum = "0";}
        return this;
    }
}
