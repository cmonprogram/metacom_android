package com.project.metacom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class config {
    public static final String server = "http://109.196.164.38";
    public static final int timeout = 5000;
    public static String token = "";
    public static String my_id = "";

    public static final MediaType JSON_HEADER = MediaType.parse("application/json; charset=utf-8");

}
