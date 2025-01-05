package fr.voteright.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.voteright.model.Reaction;

public class ReactionController {

    public Reaction getReactionsOf(int proposalId) {
        try {
            String response = HttpUtil.get("https://api.voteright.fr/proposals/" + proposalId + "/reactions");

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

            int nblike = jsonObject.get("nblike").getAsInt();
            int nblove = jsonObject.get("nblove").getAsInt();
            int nbdislike = jsonObject.get("nbdislike").getAsInt();
            int nbhate = jsonObject.get("nbhate").getAsInt();
            int percentageSatisfaction = ((nblike + nblike) / (nblike + nblove + nbdislike + nbhate))*100;

            return new Reaction(nblike, nblove, nbdislike, nbhate, percentageSatisfaction);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
