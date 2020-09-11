package com.project.metacom.comments;

import android.app.Activity;
import android.os.AsyncTask;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.project.metacom.data.Comment;
import com.project.metacom.data.Room;
import com.project.metacom.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static com.project.metacom.config.me;
import static com.project.metacom.config.server;
import static com.project.metacom.config.timeout;


public class DataReceiver_comment extends AsyncTask<String, Integer, Void> {

    DataAdapter data_adapter;
    DataReceiver_user data_receiver_user;
    public WebSocket ws;
    public Room room;
    public DataReceiver_comment(DataAdapter data_adapter){
        this.data_adapter = data_adapter;
        this.data_receiver_user = new DataReceiver_user(data_adapter);
    }


    @Override
    protected Void doInBackground(final String... urls) {
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
                                    if(Objects.equals(action, "room_info")){
                                        room = new Room().fromJson(json);
                                        room.id = urls[0];
                                        activity a = (activity) data_adapter.getContext();
                                        a.set_room(room);
                                    } else if (Objects.equals(action, "get_history")){
                                        jArray = new JSONArray(json.getString("data"));
                                    for (int i = 0; i < jArray.length(); i++) {
                                        JSONObject oneObject = jArray.getJSONObject(i);
                                        final Comment comment = new Comment().fromJson(oneObject);
                                        comment.user = data_receiver_user.check(comment.user_id);
                                        add_comment(comment, null);
                                    }
                                }else if(Objects.equals(action, "post")){
                                        json = new JSONObject(message);
                                        final Comment comment = new Comment().fromJson(json);
                                        comment.user = data_receiver_user.check(comment.user_id);
                                        add_comment(comment,0);
                                }else if(Objects.equals(action, "like")){
                                    json = new JSONObject(message);
                                    String id = json.getString("id");
                                    like(id);
                                }else if(Objects.equals(action, "dislike")){
                                    json = new JSONObject(message);
                                    String id = json.getString("id");
                                    dislike(id);
                                }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                        .connect();
            } catch (IOException | WebSocketException e) {
                e.printStackTrace();
            }

        ws.sendText("{\"action\": \"room_info\"}");
        ws.sendText("{\"action\": \"get_history\", \"token\": \""+me.token+"\"}");
        return null;
    }
/*
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        activity a = (activity) data_adapter.getContext();
        a.set_room(room);
    }
*/
private void like(final String id) {
    Activity a = (Activity) data_adapter.getContext();
    a.runOnUiThread(new Runnable() {
        @Override
        public void run() {
            data_adapter.like(id);
            data_adapter.notifyDataSetChanged();
        }
    });
}


private void dislike(final String id) {
    Activity a = (Activity) data_adapter.getContext();
    a.runOnUiThread(new Runnable() {
        @Override
        public void run() {
            data_adapter.dislike(id);
            data_adapter.notifyDataSetChanged();
        }
    });
}
    private void add_comment(final Comment comment, final Integer index){
        Activity a = (Activity) data_adapter.getContext();
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(index == null) {
                    //int length = data_adapter.getItemCount();
                    data_adapter.CommentBase.add(comment);
                    data_adapter.notifyDataSetChanged();
                    //data_adapter.notifyItemInserted(length);
                }else{
                    data_adapter.CommentBase.add(index,comment);
                    data_adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
