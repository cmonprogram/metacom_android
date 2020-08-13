package com.project.metacom.data;

import com.project.metacom.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        new OkHttpClient().newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error
                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String result = response.body().string();
                        JSONObject json = null;
                        try {
                            json = new JSONObject(result);
                            count = json.optString("count");
                            url = json.optString("url");
                            title = json.optString("title");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return this;
    }
}
