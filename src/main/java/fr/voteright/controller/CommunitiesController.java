package fr.voteright.controller;

import fr.voteright.model.Community;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class CommunitiesController {

    public ArrayList<Community> getCommunties(){
        try{
            String response = HttpUtil.get("https://api.voteright.fr/communities/managed?role=decider");
            Gson gson = new Gson();
            Community[] communities = gson.fromJson(response, Community[].class);
            return new ArrayList<>(Arrays.asList(communities));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}