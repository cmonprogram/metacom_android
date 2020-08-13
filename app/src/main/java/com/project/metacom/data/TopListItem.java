package com.project.metacom.data;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import functions.Base32;

public class TopListItem {
    public String chat_room;
    public String url;
    public String page_title;
    public String count;

    public TopListItem fromJson(JSONObject oneObject) {
        String tmp_result = oneObject.optString("chat_room");
        byte[] byte_result = Base32.fromBase32Z(tmp_result);

        this.chat_room = tmp_result;
        try {
            this.url = new String(byte_result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(this.url.length() >= 60)
            this.url = this.url.substring(0,60) + "..";
        this.page_title =  oneObject.optString("title");
        this.count = oneObject.optString("count");
        return  this;
    }
}
