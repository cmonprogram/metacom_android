package com.project.metacom;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//http://109.196.164.38/metacom/chat/get_top
class DataRceveiver {

    public String server;
    public DataAdapter data_adapter;
    public Boolean show  = false;

    DataRceveiver(String server,DataAdapter data_adapter){
        this.server = server;
        this.data_adapter = data_adapter;
    }

    public void Top_chart_execute() throws IOException {
        Request request = new Request.Builder()
                .url("http://109.196.164.38/metacom/chat/get_top")
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
                                data_adapter.listGroup.add(oneObject.getString("chat_room"));
                                //data_adapter.listChild.put(oneObject.getString("chat_room"), null);
                            }
                        } catch (JSONException e) {
                            // Oops
                        }
                    }
                });
        //Top_chart mt = new Top_chart();
        //mt.execute();
    }




    /*

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://109.196.164.38/metacom/chat/get_top";

    private static void sendGET() throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }
    */

/*
      class Top_chart extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... data) {
            String urlString = server + "/metacom/chat/get_top"; // URL to call
            //String data = "{\"username\": \""+username+"\", \"password\": \""+password+"\"}";
            String responseString;
            OutputStream out = null;
            try {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                //con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                //String jsonInputString = data[0];


                //try (OutputStream os = con.getOutputStream()) {
                //    byte[] input = jsonInputString.getBytes("utf-8");
                //    os.write(input, 0, input.length);
                //}



                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                    responseString = response.toString();
                }
                return responseString;
            } catch (Exception e) {
                //new IOException("Error logging in", e);
                return null;
            }
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {
            // [... Обновите индикатор хода выполнения, уведомления или другой
            // элемент пользовательского интерфейса ...]
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject json = null;
            JSONArray jArray = null;
            try {
                //json = new JSONObject(result);
                jArray = new JSONArray(result);
                for (int i=0; i < jArray.length(); i++)
                {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        data_adapter.listGroup.add(oneObject.getString("chat_room"));
                }
            } catch (JSONException e) {
                // Oops
            }
            // [... Сообщите о результате через обновление пользовательского
            // интерфейса, диалоговое окно или уведомление ...]
        }

    }
    */
}

