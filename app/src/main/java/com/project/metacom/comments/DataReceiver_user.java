package com.project.metacom.comments;

import com.project.metacom.Receiver;
import com.project.metacom.config;
import com.project.metacom.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static functions.MakeRequest.MakeRequest;

public class DataReceiver_user {
    public List<User> UserBase = new ArrayList<User>();

    /*
    public DataReceiver_user(DataAdapter data_adapter){
        this.data_adapter = data_adapter;
    }
    */


    public User check(String id){
        for (User user : UserBase) {
           if(Objects.equals(id, user.id)){
               return user;
           }
        }

        RequestBody requestmBody = RequestBody.create(new byte[0]);
        final Request request = new Request.Builder()
                .url(config.server + "/metacom/user_info/"+id)
                .post(requestmBody)
                .build();

        try {
            String result = MakeRequest(request);
            JSONObject json = null;
            JSONArray jArray = null;
                json  = new JSONObject(result);
                User user = new User().fromJson(json);
                if(user != null) {
                    UserBase.add(user);
                    return user;
                }
                // localStorage.setItem('meta_chat_self_id', response.id);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
