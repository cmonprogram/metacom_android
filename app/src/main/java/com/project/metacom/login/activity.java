package com.project.metacom.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import com.project.metacom.R;
import com.project.metacom.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.project.metacom.config.server;

public class activity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view cv = new view(this);
        setContentView(R.layout.login_layout);

        setTitle("Login");


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("username", usernameEditText.getText().toString())
                        .addFormDataPart("password", passwordEditText.getText().toString())
                        .build();

                final Request request = new Request.Builder()
                        .url(server + "/token")
                        .post(requestBody)
                        .build();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response response = new OkHttpClient().newCall(request).execute();
                            String result = response.body().string();
                            JSONObject json = null;
                            JSONArray jArray = null;
                            try {
                                json  = new JSONObject(result);
                                config.token = json.getString("access_token");
                                String room = getIntent().getStringExtra("go_to");
                                if(room != null){
                                    Intent startIntent = new Intent(activity.this, com.project.metacom.comments.activity.class);
                                    startIntent.putExtra("go_to", room);
                                    startActivity(startIntent);
                                }else {
                                    Intent startIntent = new Intent(activity.this, com.project.metacom.toplist.activity.class);
                                    startActivity(startIntent);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
                //loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });



    }



    class view extends View {
        public view(Context context) {
            super(context);
        }
    }

}
