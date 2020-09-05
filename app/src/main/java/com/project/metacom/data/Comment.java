package com.project.metacom.data;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Comment {
    public String id;
    public String user_id;
    public String text;
    public String time;
    public String likes;
    public String dislikes;
    public Comment[] replies;
    public User user;

    public Comment fromJson(JSONObject oneObject){
        this.id = oneObject.optString("id");
        this.user_id = oneObject.optString("user_id");
        this.text = oneObject.optString("text");
        this.likes = oneObject.optString("likes");
        this.dislikes = oneObject.optString("dislikes");

        // date
        String timeStampString = oneObject.optString("time");
        if(timeStampString != null) {
            long timeStamp = (int) Double.parseDouble(timeStampString);
            Date date = new Date(timeStamp * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("d MMMM HH:mm");
            sdf.setTimeZone(TimeZone.getDefault());
            String formattedDate = sdf.format(date);
            this.time = formattedDate;
        }
        return this;
    }
}