package fr.voteright.controller;

import com.google.gson.Gson;
import fr.voteright.algorithm.BruteForce;
import fr.voteright.algorithm.Greedy;
import fr.voteright.algorithm.Mock;
import fr.voteright.model.Community;
import fr.voteright.model.Proposal;

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

}
