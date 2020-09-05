package com.project.metacom.comments;

import com.project.metacom.config;
import com.project.metacom.data.User;
import com.project.metacom.data.User_stats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataReceiver_user {
    DataAdapter data_adapter;
    public DataReceiver_user(DataAdapter data_adapter){
        this.data_adapter = data_adapter;
    }

    public User check(String id){
        for (User user : data_adapter.UserBase) {
           if(Objects.equals(id, user.id)){
               return user;
           }
        }

        RequestBody requestmBody = RequestBody.create(new byte[0]);
        Request request = new Request.Builder()
                .url(config.server + "/metacom/user_info/"+id)
                .post(requestmBody)
                .build();

        try {
            Response response = new OkHttpClient().newCall(request).execute();
            String result = response.body().string();
            JSONObject json = null;
            JSONArray jArray = null;
            try {
                json  = new JSONObject(result);
                User user = new User().fromJson(json);
                if(user != null) {
                    data_adapter.UserBase.add(user);
                    return user;
                }
                // localStorage.setItem('meta_chat_self_id', response.id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
