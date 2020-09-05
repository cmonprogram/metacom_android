package com.project.metacom.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import functions.Base32;

public class TopListItem {
    public String chat_room;
    public String url;
    public String page_title;
    public String count;
    public Bitmap icon;

    public TopListItem fromJson(JSONObject oneObject) {
        String tmp_result = oneObject.optString("chat_room");
        byte[] byte_result = Base32.fromBase32Z(tmp_result);

        this.page_title =  oneObject.optString("title");
        this.count = oneObject.optString("count");
        this.chat_room = tmp_result;
        try {
            this.url = new String(byte_result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(this.url.length() >= 60)
            this.url = this.url.substring(0,60) + "..";

        String encoded_image = oneObject.optString("icon");
        if (encoded_image != null) {
            byte[] decodedString_image = Base64.decode(encoded_image, Base64.DEFAULT);
            Bitmap decodedByte_image = BitmapFactory.decodeByteArray(decodedString_image, 0, decodedString_image.length);
            this.icon = decodedByte_image;
        }
        return  this;
    }
}
