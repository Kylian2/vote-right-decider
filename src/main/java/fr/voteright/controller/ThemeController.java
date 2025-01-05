package fr.voteright.controller;

import com.google.gson.Gson;
import fr.voteright.model.Theme;

import java.util.ArrayList;
import java.util.Arrays;

public class ThemeController {

    public ArrayList<Theme> getThemesOf(int communityId){
        try{
            String response = HttpUtil.get("https://api.voteright.fr/communities/"+communityId+"/themes");
            Gson gson = new Gson();
            Theme[] themes = gson.fromJson(response, Theme[].class);
            return new ArrayList<>(Arrays.asList(themes));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
