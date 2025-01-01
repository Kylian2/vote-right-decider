package fr.voteright.controller;

import com.google.gson.*;
import fr.voteright.model.User;

public class LoginController {

    public boolean login(String email, String password){
        try {
            JsonObject user = new JsonObject();
            user.addProperty("email", email);
            user.addProperty("password", password);
            String jsonBody = user.toString();
            String response = HttpUtil.post("https://api.voteright.fr/auth/login", jsonBody);
            return response.equals("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}