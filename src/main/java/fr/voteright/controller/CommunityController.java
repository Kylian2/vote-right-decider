package fr.voteright.controller;

import fr.voteright.model.Community;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.Arrays;

public class CommunityController {

    public ArrayList<Community> getCommunities(){
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

    public Community getCommunity(int id){
        try{
            String response = HttpUtil.get("https://api.voteright.fr/communities/"+id);
            Gson gson = new Gson();
            Community community = gson.fromJson(response, Community.class);
            return community;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}