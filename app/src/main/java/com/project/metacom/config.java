package com.project.metacom;

import android.app.Activity;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.project.metacom.toplist.DataStructure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import functions.Base32;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class config {
    public static final String server = "http://109.196.164.38";
    public static final int timeout = 5000;
    public static final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXBfdmxhZCIsImV4cCI6MTU5NjYyOTk3N30.cKbW8oiF2B8KGQ_xUc4zQTwmSo3UFGEgvM_4vigiIWw";

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
