package functions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.project.metacom.comments.activity;
import com.project.metacom.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.project.metacom.config.JSON_HEADER;
import static com.project.metacom.config.my_id;
import static com.project.metacom.config.token;

public class CheckMe {
    public static void reset() {
    token = "";
    my_id = "";
    }

    public static Boolean checkMe() {
                RequestBody requestmBody = RequestBody.create("{\"token_type\": \"bearer\", \"access_token\": \"" + token + "\"}", JSON_HEADER);
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
                        json = new JSONObject(result);
                        String status = json.getString("status");
                        if (Objects.equals(status, "success")) {
                            my_id =  json.getString("id");
                            return true;
                        } else {
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

    public static void checkMe_dialog(final Context context) {
        Runnable runnable = new Runnable() {
            public void run() {
                    new AlertDialog.Builder(context)
                            .setTitle("You need to be login to do this")
                            .setMessage("Do you want to?")
                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent startIntent = new Intent(context, com.project.metacom.login.activity.class);
                                    startIntent.putExtra("go_to", ((Activity) context).getIntent().getStringExtra("go_to"));
                                    startIntent.putExtra("page_title", ((Activity) context).getIntent().getStringExtra("page_title"));
                                    ((Activity) context).startActivity(startIntent);
                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


            }
        };

        ((Activity) context).runOnUiThread(runnable) ;
}
}
