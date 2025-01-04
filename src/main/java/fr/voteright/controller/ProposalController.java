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
            Proposal[] communities = gson.fromJson(response, Proposal[].class);
            return new ArrayList<>(Arrays.asList(communities));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Proposal getProposal(int id){
        try{
            String response = HttpUtil.get("https://api.voteright.fr/proposals/"+id);
            Gson gson = new Gson();
            Proposal proposal = gson.fromJson(response, Proposal.class);
            return proposal;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
