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
import java.util.Objects;

import static com.project.metacom.config.server;
import static com.project.metacom.config.timeout;
import static com.project.metacom.config.token;

public class DataRceveiver extends AsyncTask<String, Integer, Void> {

    DataAdapter data_adapter;
    public WebSocket ws;
    public Boolean show  = false;


    public DataRceveiver(DataAdapter data_adapter){
        this.data_adapter = data_adapter;
    }


    @Override
    protected Void doInBackground(String... urls) {
            try {
                this.ws = new WebSocketFactory()
                        .setConnectionTimeout(timeout)
                        .createSocket(server+"/chat/"+urls[0])
                        .addListener(new WebSocketAdapter() {
                            // A text message arrived from the server.
                            public void onTextMessage(WebSocket websocket, String message) {
                                JSONObject json = null;
                                JSONArray jArray = null;
                                try {

                                    json = new JSONObject(message);
                                    String action = json.getString("action");

                                    if (Objects.equals(action, "get_history")){
                                        jArray = new JSONArray(json.getString("data"));
                                    for (int i = 0; i < jArray.length(); i++) {
                                        JSONObject oneObject = jArray.getJSONObject(i);
                                        final DataStructure data = new DataStructure().fromJson(oneObject);
                                        add_comment(data,null);
                                    }
                                }else if(Objects.equals(action, "post")){
                                        json = new JSONObject(message);
                                        final DataStructure data = new DataStructure().fromJson(json);
                                        add_comment(data,0);
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

    private void add_comment(final DataStructure data, final Integer index){
        Activity a = (Activity) data_adapter.getContext();
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(index == null) {
                    //int length = data_adapter.getItemCount();
                    data_adapter.Dataset.add(data);
                    data_adapter.notifyDataSetChanged();
                    //data_adapter.notifyItemInserted(length);
                }else{
                    data_adapter.Dataset.add(index,data);
                    data_adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
