package com.project.metacom.toplist;

import android.app.Activity;
import android.widget.ExpandableListView;

import com.project.metacom.config;

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
import okhttp3.Response;

public class DataRceveiver {

    DataAdapter data_adapter;
    public Boolean show  = false;


    public DataRceveiver(DataAdapter data_adapter){
        this.data_adapter = data_adapter;
    }

    public void execute() throws IOException {

        Request request = new Request.Builder()
                .url(config.server + "/metacom/chat/get_top")
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
                        JSONArray jArray = null;
                        try {
                            //json = new JSONObject(result);
                            jArray = new JSONArray(result);
                            for (int i=0; i < jArray.length(); i++)
                            {

                                JSONObject oneObject = jArray.getJSONObject(i);

                                final DataStructure data = new DataStructure();
                                String tmp_result = oneObject.getString("chat_room");
                                byte[] byte_result = Base32.fromBase32Z(tmp_result);

                                data.chat_room = tmp_result;
                                data.url = new String(byte_result, "UTF-8");
                                if(data.url.length() >= 60)
                                    data.url = data.url.substring(0,60) + "..";
                                data.page_title =  oneObject.getString("title");
                                data.count = oneObject.getString("count");

                                Activity a = (Activity)data_adapter.getContext();
                                a.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        data_adapter.add(data);
                                        data_adapter.notifyDataSetChanged(); }
                                });
                            }
                        } catch (JSONException e) {
                            // Oops
                        }
                    }
                });
        //Top_chart mt = new Top_chart();
        //mt.execute();
    }
}
