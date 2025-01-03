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

    public Community getBudget(Community community){
        try{
            String response = HttpUtil.get("https://api.voteright.fr/communities/"+community.getId()+"/budget?period=2025");
            Gson gson = new Gson();
            Community cmy = gson.fromJson(response, Community.class);
            community.setThemes(cmy.getThemes());
            community.setBudget(cmy.getBudget());
            community.setUsedBudget(cmy.getUsedBudget());
            return community;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}