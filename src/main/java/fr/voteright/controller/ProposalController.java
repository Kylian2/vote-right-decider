package fr.voteright.controller;

import com.google.gson.*;
import fr.voteright.model.Proposal;
import fr.voteright.model.Community;
import fr.voteright.algorithm.BruteForce;
import fr.voteright.algorithm.Greedy;
import fr.voteright.algorithm.Mock;

import java.util.ArrayList;
import java.util.Arrays;

import static fr.voteright.algorithm.Mock.convertJsonToProposals;

public class ProposalController {

    public ArrayList<Proposal> getProposalsOf(int communityId){
        try{
            String response = HttpUtil.get("https://api.voteright.fr/communities/"+communityId+"/proposals/formatted?period=2024");
            return convertJsonToProposals(response);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Proposal> orderBy(ArrayList<Proposal> proposals, Community community, int criteria) throws Exception {
        Greedy greedy = new Greedy();
        BruteForce bruteForce = new BruteForce();
        return switch (criteria) {
            case 1 -> greedy.maximizeTotalSatisfaction(proposals, community).toArrayList();
            case 2 -> greedy.maximizeTotalSatisfactionRatio(proposals, community).toArrayList();
            case 3 -> bruteForce.maximizeTotalSatisfaction(proposals, community).toArrayList();
            default -> proposals;
        };
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
