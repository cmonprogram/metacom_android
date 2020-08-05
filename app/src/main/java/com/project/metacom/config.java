package com.project.metacom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class config {
    public static final String server = "http://109.196.164.38";
    public static final int timeout = 5000;
    public static String token = "";

    public static final MediaType JSON_HEADER = MediaType.parse("application/json; charset=utf-8");
    public static Boolean checkMe()
    {

        RequestBody requestmBody = RequestBody.create("{\"token_type\": \"bearer\", \"access_token\": \""+token+"\"}",JSON_HEADER );
        Request request = new Request.Builder()
                .url(config.server + "/token/check")
                .post(requestmBody)
                .build();

        try {
            Response response = new OkHttpClient().newCall(request).execute();
            String result = response.body().string();
            JSONObject json = null;
            JSONArray jArray = null;
            try {
                json  = new JSONObject(result);
                String status = json.getString("status");
                if (Objects.equals(status, "success")){
                    return true;
                }else{
                    return false;
                }
                // localStorage.setItem('meta_chat_self_id', response.id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
