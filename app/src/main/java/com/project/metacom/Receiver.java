package com.project.metacom;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.project.metacom.comments.DataReceiver_comment;
import com.project.metacom.comments.DataReceiver_user;
import com.project.metacom.comments.activity;
import com.project.metacom.data.Comment;
import com.project.metacom.data.Room;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import static com.project.metacom.config.server;
import static com.project.metacom.config.timeout;

public class Receiver {
    //public static DataReceiver_comment data_receiver_comment;
    public static DataReceiver_user data_receiver_user = new DataReceiver_user();

    /*
    public static WebSocket ws = new WebSocketFactory()
                        .setConnectionTimeout(timeout)
                        .createSocket(server+"/chat/"+urls[0])
                        .addListener(new WebSocketAdapter() {
                        public void onTextMessage(WebSocket websocket, String message) {

                        }
                        })
                        .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                        .connect();
     */
}
