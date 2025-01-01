package fr.voteright.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class Test {
    public static void main(String[] args) {
        try {
            String response = HttpUtil.get("https://api.voteright.fr/users");

            Gson gson = new Gson();
            JsonArray users = gson.fromJson(response, JsonArray.class);
            for (int i = 0; i < users.size(); i++) {
                JsonObject user = users.get(i).getAsJsonObject();
                System.out.println(user.get("USR_firstname_VC").getAsString() + " " + user.get("USR_lastname_VC").getAsString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
