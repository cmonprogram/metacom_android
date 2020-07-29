package com.project.metacom.comments;

import android.app.Activity;
import android.os.AsyncTask;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static com.project.metacom.config.server;
import static com.project.metacom.config.timeout;
import static com.project.metacom.config.token;

public class DataRceveiver extends AsyncTask<URL, Integer, Void> {

    DataAdapter data_adapter;
    WebSocket ws;
    public Boolean show  = false;


    public DataRceveiver(DataAdapter data_adapter){
        this.data_adapter = data_adapter;
    }


    @Override
    protected Void doInBackground(URL... urls) {
            try {
                this.ws = new WebSocketFactory()
                        .setConnectionTimeout(timeout)
                        .createSocket(server+"/chat/pb48ehb4fhzsr5dbp3i1h551chzu67dfqp4dnca")
                        .addListener(new WebSocketAdapter() {
                            // A text message arrived from the server.
                            public void onTextMessage(WebSocket websocket, String message) {
                                JSONObject json = null;
                                JSONArray jArray = null;
                                try {
                                    json = new JSONObject(message);
                                    jArray = new JSONArray( json.getString("data"));

                                    for (int i=0; i < jArray.length(); i++)
                                    {

                                        JSONObject oneObject = jArray.getJSONObject(i);
                                        final DataStructure data = new DataStructure();
                                        data.id = oneObject.getString("id");
                                        data.user_id = oneObject.getString("user_id");
                                        data.text = oneObject.getString("text");
                                        data.time = oneObject.getString("time");
                                        data.likes = oneObject.getString("likes");
                                        data.dislikes = oneObject.getString("dislikes");

                                        Activity a = (Activity)data_adapter.getContext();
                                        a.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //int length = data_adapter.getItemCount();
                                                data_adapter.Dataset.add(data);
                                                data_adapter.notifyDataSetChanged();
                                                //data_adapter.notifyItemInserted(length);
                                            }
                                        });
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                        .connect();
            } catch (IOException | WebSocketException e) {
                e.printStackTrace();
            }

        ws.sendText("{\"action\": \"get_history\", \"token\": \""+token+"\"}");
        return null;
    }
}
