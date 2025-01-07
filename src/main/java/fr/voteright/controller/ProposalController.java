package fr.voteright.controller;

import fr.voteright.model.Proposal;
import fr.voteright.model.Community;
import fr.voteright.algorithm.BruteForce;
import fr.voteright.algorithm.Greedy;

import com.google.gson.*;

import java.util.ArrayList;

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
            String response1 = HttpUtil.get("https://api.voteright.fr/proposals/" + proposalId);
            JsonObject json1 = JsonParser.parseString(response1).getAsJsonObject();

            int id = json1.get("PRO_id_NB").getAsInt();
            String title = json1.get("PRO_title_VC").getAsString();
            String description = json1.get("PRO_description_TXT").getAsString();
            int budget = json1.get("PRO_budget_NB").getAsInt();
            String theme = json1.get("PRO_theme_VC").getAsString();
            int idCommunity = json1.get("PRO_community_NB").getAsInt();

            String response2 = HttpUtil.get("https://api.voteright.fr/proposals/" + proposalId + "/reactions");
            JsonObject json2 = JsonParser.parseString(response2).getAsJsonObject();

            int nblove = json2.get("nblove").getAsInt();
            int nblike = json2.get("nblike").getAsInt();
            int nbdislike = json2.get("nbdislike").getAsInt();
            int nbhate = json2.get("nbhate").getAsInt();

            int totalReactions = nblove + nblike + nbdislike + nbhate;
            int satisfactionPercentage = (int) (((double) (nblove + nblike) / totalReactions) * 100);

            return new Proposal(id, title, description, satisfactionPercentage, budget, theme, idCommunity);
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
