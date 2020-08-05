package com.project.metacom.comments;

import org.json.JSONException;
import org.json.JSONObject;

public class DataStructure {
    public String id;
    public String user_id;
    public String text;
    public String time;
    public String likes;
    public String dislikes;
    public DataStructure[] replies;

    public DataStructure fromJson(JSONObject oneObject){
        this.id = oneObject.optString("id");
        this.user_id = oneObject.optString("user_id");
        this.text = oneObject.optString("text");
        this.time = oneObject.optString("time");
        this.likes = oneObject.optString("likes");
        this.dislikes = oneObject.optString("dislikes");

        return this;
    }
}