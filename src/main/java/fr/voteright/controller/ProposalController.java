package fr.voteright.controller;

import com.google.gson.*;
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

    public Proposal getProposal(int proposalId){
        try{
            String response = HttpUtil.get("https://api.voteright.fr/proposals/"+proposalId);
            Gson gson = new Gson();
            Proposal proposal = gson.fromJson(response, Proposal.class);
            return proposal;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean validateProposal(int proposalId) {
        try {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("approve", true);
            String jsonBody = requestBody.toString();
            String response = HttpUtil.post("https://api.voteright.fr/proposals/" + proposalId + "/approve", jsonBody);
            return response.equals("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
