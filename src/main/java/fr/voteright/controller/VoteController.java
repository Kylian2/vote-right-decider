package fr.voteright.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.voteright.model.Vote;

public class VoteController {
    public Vote getVoteOfProposal(int proposalId){
        try {
            String response1 = HttpUtil.get("https://api.voteright.fr/proposals/" + proposalId + "/votes");

            Gson gson = new Gson();
            JsonObject jsonObject1 = gson.fromJson(response1, JsonObject.class);

            int nbRound = jsonObject1.get("VOT_nb_rounds_NB").getAsInt();
            String type = jsonObject1.get("VOT_type_VC").getAsString();

            String response2 = HttpUtil.get("https://api.voteright.fr/proposals/" + proposalId + "/" + nbRound + "/vote");

            JsonObject jsonObject2 = gson.fromJson(response2, JsonObject.class);
            JsonArray votesArray = jsonObject2.getAsJsonArray("votes");

            int totalVotes = 0;
            for (JsonElement voteElement : votesArray) {
                JsonObject voteObject = voteElement.getAsJsonObject();
                totalVotes += voteObject.get("POS_nbVotes_NB").getAsInt();
            }

            return new Vote(type, nbRound, totalVotes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
