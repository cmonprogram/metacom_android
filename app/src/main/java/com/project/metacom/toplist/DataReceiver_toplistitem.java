package com.project.metacom.toplist;

import android.app.Activity;
import android.widget.ExpandableListView;

import com.project.metacom.config;
import com.project.metacom.data.TopListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import functions.Base32;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.project.metacom.config.me;
import static functions.MakeRequest.MakeRequest;


public class DataReceiver_toplistitem {

    DataAdapter data_adapter;
    public Boolean show  = false;

    public DataReceiver_toplistitem(DataAdapter data_adapter){
        this.data_adapter = data_adapter;
    }



    public class all{
        private String url_get = "/metacom/chat/get_top";
        private String url_search = "/metacom/room_search";
        public void get() throws IOException {
            reset();
            get_func(url_get);
        }
        public void search(String req) throws IOException {
            reset();
            get_func(url_search+"?item_id="+req);
        }
    }

    public class my{
        private String url_get = "/metacom/get_last/" + me.user.id;
        private String url_search = "/metacom/room_search/" + me.user.id;
        public void get() throws IOException {
            reset();
            post_func(url_get);
        }
        public void search(String req) throws IOException {
            reset();
            get_func(url_search+"?item_id="+req);
        }
    }



    public void reset(){
        Activity a = (Activity)data_adapter.getContext();
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data_adapter.clear();
                data_adapter.notifyDataSetChanged(); }
        });
    }

    public void get_func(String url) throws IOException {

        Request request = new Request.Builder()
                .url(config.server + url)
                .build();
        try {
        String result = MakeRequest(request);
        assert result != null;
        JSONObject json = null;
        JSONArray jArray = null;

            //json = new JSONObject(result);
            jArray = new JSONArray(result);
            for (int i=0; i < jArray.length(); i++)
            {
                JSONObject oneObject = jArray.getJSONObject(i);
                final TopListItem data = new TopListItem().fromJson(oneObject);
                Activity a = (Activity)data_adapter.getContext();
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data_adapter.add(data);
                        data_adapter.notifyDataSetChanged(); }
                });
            }

        } catch (Exception e) {
            // Oops
        }

    }

    public void post_func(String url) throws IOException {
        RequestBody requestmBody = RequestBody.create(new byte[0]);

        Request request = new Request.Builder()
                .url(config.server + url)
                .post(requestmBody)
                .build();

        try {
            String result = MakeRequest(request);
            assert result != null;
            JSONObject json = null;
            JSONArray jArray = null;

            //json = new JSONObject(result);
            jArray = new JSONArray(result);
            for (int i=0; i < jArray.length(); i++)
            {
                JSONObject oneObject = jArray.getJSONObject(i);
                final TopListItem data = new TopListItem().fromJson(oneObject);
                Activity a = (Activity)data_adapter.getContext();
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data_adapter.add(data);
                        data_adapter.notifyDataSetChanged(); }
                });
            }

        } catch (Exception e) {
            // Oops
        }


    }
}
