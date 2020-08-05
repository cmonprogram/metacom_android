package com.project.metacom.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONObject;

public class User {
    public String id;
    public String name;
    public String username;
    public Bitmap image;

    public User fromJson(JSONObject oneObject) {
        this.id = oneObject.optString("id");
        this.name = oneObject.optString("name");
        this.username = oneObject.optString("username");

        String encoded_image = oneObject.optString("image");
        if(encoded_image != null) {
           byte[] decodedString_image = Base64.decode(encoded_image, Base64.DEFAULT);
           Bitmap decodedByte_image = BitmapFactory.decodeByteArray(decodedString_image, 0, decodedString_image.length);
           this.image = decodedByte_image;
        }
        return this;
    }
}
