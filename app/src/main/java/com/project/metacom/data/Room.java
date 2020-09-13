package com.project.metacom.data;

import com.project.metacom.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static functions.MakeRequest.MakeRequest;

public class Room {
    public String id;
    public String count;
    public String url;
    public String title;

    public Room fromJson(JSONObject oneObject) {
        this.count = oneObject.optString("count");
        this.url = oneObject.optString("url");
        this.title = oneObject.optString("title");
        return this;
    }

    public Room fromid(String id) {
        this.id = id;
        Request request = new Request.Builder()
                .url(config.server + "/metacom/room_info/" + id)
                .build();

        try {
            String result = MakeRequest(request);
            JSONObject json = null;
            JSONArray jArray = null;

            json = new JSONObject(result);
            count = json.optString("count");
            url = json.optString("url");
            title = json.optString("title");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }
}
