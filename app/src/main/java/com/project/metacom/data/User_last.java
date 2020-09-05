package com.project.metacom.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONObject;

public class User_last {
    public String chat_room;
    public String id;
    public String count;
    public String time;
    public String title;
    public String url;
    public Bitmap icon;

    public User_last fromJson(JSONObject oneObject) {
        this.chat_room = oneObject.optString("chat_room");
        this.id = oneObject.optString("id");
        this.count = oneObject.optString("count");
        this.time = oneObject.optString("time");
        this.title = oneObject.optString("title");
        this.url = oneObject.optString("url");

        String encoded_image = oneObject.optString("icon");
        if (encoded_image != null) {
            byte[] decodedString_image = Base64.decode(encoded_image, Base64.DEFAULT);
            Bitmap decodedByte_image = BitmapFactory.decodeByteArray(decodedString_image, 0, decodedString_image.length);
            this.icon = decodedByte_image;
        }
        return this;
    }
}
