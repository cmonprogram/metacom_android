package com.project.metacom.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.project.metacom.config;
import com.project.metacom.fragment.user_profile_fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.project.metacom.config.me;
import static functions.MakeRequest.MakeRequest;


public class User {
    public String id;
    public String name;
    public String username;
    public Bitmap image;
    public String joined;
    public String subscriptions;
    private User_stats user_stats;

    public void SubscribeMe(String user_id, Context context, user_profile_fragment.DataAdapter data_adapter) {
        if(!me.checkMe()){
            me.checkMe_dialog(context);
        }else {
            RequestBody requestmBody = RequestBody.create(new byte[0]);
            Request request = new Request.Builder()
                    .url(config.server + "/metacom/me/subscribe/" + user_id)
                    .addHeader("Authorization", "Bearer " + me.token)
                    .post(requestmBody)
                    .build();
            try {
                String result = MakeRequest(request);
                Toast.makeText(context, "Success", Toast.LENGTH_LONG);
                this.subscriptions = String.valueOf(Integer.parseInt(subscriptions) + 1);
                data_adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }




    public User_stats get_stats() {
        if (user_stats == null) {
            RequestBody requestmBody = RequestBody.create(new byte[0]);
            Request request = new Request.Builder()
                    .url(config.server + "/metacom/user_stats/" + id)
                    .post(requestmBody)
                    .build();

            try {
                String result = MakeRequest(request);
                assert result != null;
                JSONObject json = null;
                JSONArray jArray = null;

                json = new JSONObject(result);
                user_stats = new User_stats().fromJson(json);
                return user_stats;
                // localStorage.setItem('meta_chat_self_id', response.id);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return user_stats;
    }

    public List<User_last> get_last() {
        List<User_last> user_last = new ArrayList<User_last>();
        RequestBody requestmBody = RequestBody.create(new byte[0]);
        Request request = new Request.Builder()
                .url(config.server + "/metacom/get_last/" + id)
                .post(requestmBody)
                .build();
        try {
            String result = MakeRequest(request);
            assert result != null;
            JSONObject json = null;
            JSONArray jArray = null;

            jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject oneObject = jArray.getJSONObject(i);
                final User_last user_last_obj = new User_last().fromJson(oneObject);
                user_last.add(user_last_obj);
            }
            return user_last;
            // localStorage.setItem('meta_chat_self_id', response.id);

        } catch (Exception e) {
            e.printStackTrace();
        }



        return user_last;
    }

    public User fromJson(JSONObject oneObject) {
        this.id = oneObject.optString("id");
        this.name = oneObject.optString("name");
        this.username = oneObject.optString("username");

        long timeStamp = (int) Double.parseDouble(oneObject.optString("joined"));
        Date date = new Date(timeStamp * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
        sdf.setTimeZone(TimeZone.getDefault());
        String formattedDate = sdf.format(date);
        this.joined = "joined: " + formattedDate;

        String encoded_image = oneObject.optString("image");
        if (encoded_image != null) {
            byte[] decodedString_image = Base64.decode(encoded_image, Base64.DEFAULT);
            Bitmap decodedByte_image = BitmapFactory.decodeByteArray(decodedString_image, 0, decodedString_image.length);
            this.image = decodedByte_image;
        }
        return this;
    }

    public static class User_last {
        public String chat_room;
        public String id;
        public String user_id;
        public String count;
        public String time;
        public String title;
        public String url;
        public Bitmap icon;

        public User_last fromJson(JSONObject oneObject) {
            this.chat_room = oneObject.optString("chat_room");
            this.id = oneObject.optString("id");
            this.user_id = oneObject.optString("user_id");
            this.count = oneObject.optString("count");
            this.title = oneObject.optString("title");
            this.url = oneObject.optString("url");

            long timeStamp = (int) Double.parseDouble(oneObject.optString("time"));
            Date date = new Date(timeStamp * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
            sdf.setTimeZone(TimeZone.getDefault());
            String formattedDate = sdf.format(date);
            this.time = formattedDate;

            String encoded_image = oneObject.optString("icon");
            if (encoded_image != null) {
                byte[] decodedString_image = Base64.decode(encoded_image, Base64.DEFAULT);
                Bitmap decodedByte_image = BitmapFactory.decodeByteArray(decodedString_image, 0, decodedString_image.length);
                this.icon = decodedByte_image;
            }
            return this;
        }
    }

    public static class User_stats {
        public String comment_sum;
        public String like_sum;
        public String dislike_sum;


        public User_stats fromJson(JSONObject oneObject) {
            this.comment_sum = oneObject.optString("comment_sum");
            this.like_sum = oneObject.optString("like_sum");
            if(this.like_sum.equals("null")){this.like_sum = "0";}
            this.dislike_sum = oneObject.optString("dislike_sum");
            if(this.dislike_sum.equals("null")){this.dislike_sum = "0";}
            return this;
        }
    }
}
