package com.project.metacom.data;

import org.json.JSONObject;

public class User {
    public String id;
    public String name;
    public String username;
    public String image;

    public User fromJson(JSONObject oneObject) {
        this.id = oneObject.optString("id");
        this.name = oneObject.optString("name");
        this.username = oneObject.optString("username");
        this.image = oneObject.optString("image");
        return this;
    }
}
