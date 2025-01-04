package fr.voteright.controller;

import com.google.gson.Gson;
import fr.voteright.model.Community;
import fr.voteright.model.Proposal;

import java.util.ArrayList;
import java.util.Arrays;

public class ProposalController {

    public ArrayList<Proposal> getProposalsOf(int communityId){
        try{
            String response = HttpUtil.get("https://api.voteright.fr/communities/"+communityId+"/ongoing");
            Gson gson = new Gson();
            Proposal[] proposals = gson.fromJson(response, Proposal[].class);
            return new ArrayList<>(Arrays.asList(proposals));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
