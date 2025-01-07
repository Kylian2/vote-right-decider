package fr.voteright.algorithm;

import fr.voteright.model.Community;
import fr.voteright.model.Proposal;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.voteright.model.Theme;

import java.util.ArrayList;

/**
 * La classe Mock fournit des méthodes utilitaires pour générer des données fictives.
 * Ces données incluent des propositions avec budgets, votes, réactions, ainsi que des communautés avec thèmes,
 * budgets et utilisateurs.
 *
 * Les méthodes sont :
 * - La génération de propositions en format JSON.
 * - La génération d'une communauté en format JSON.
 * - La conversion des données JSON en objets Java.
 *
 * Cette classe est utile pour des scénarios de tests ou de simulations des algorithmes de rcommendation.
 */
public class Mock {

    public static void main(String[] args) {
        int nbProposal = 15;
        int nbUser = 20;
        int budget = 20000;
        int communityBudget = 400000;
        int nbTheme = 5;

        String jsonOutput = generateProposals(nbProposal, nbUser, budget, 5);
        System.out.println(jsonOutput);

        String communityJson = generateCommunity(nbTheme, communityBudget, nbUser);
        System.out.println(communityJson);
    }

    /**
     * Génère une liste JSON de propositions aléatoires avec des budgets, des thèmes,
     * des votes et des réactions, basée sur les paramètres spécifiés.
     *
     * @param nbProposal Le nombre de propositions à générer.
     * @param nbUser Le nombre d'utilisateurs disponibles pour voter ou réagir.
     * @param budget Le budget de référence pour les propositions.
     * @param nbTheme Le nombre total de thèmes disponibles.
     * @return Une chaîne JSON contenant la liste des propositions générées.
     */
    public static String generateProposals(int nbProposal, int nbUser, int budget, int nbTheme) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray proposalsArray = new JsonArray();
        Random random = new Random();

        int minBudget = (int) (budget * 0.2);
        int maxBudget = (int) (budget * 1.8);

        for (int i = 1; i <= nbProposal; i++) {
            JsonObject proposal = new JsonObject();

            proposal.addProperty("PRO_id_NB", i);
            proposal.addProperty("PRO_title_VC", "Proposition " + i);
            proposal.addProperty("PRO_budget_NB", random.nextInt(maxBudget - minBudget + 1) + minBudget);
            proposal.addProperty("PRO_theme_NB",  random.nextInt(nbTheme-1)+1);

            JsonObject votes = new JsonObject();
            votes.addProperty("system", random.nextInt(10) > 3 ? random.nextInt(2) + 1 : random.nextInt(2) + 3);
            //seuls les votes POUR/CONTRE ou OUI/NON permettent de determiner explicitement la position de l'utilisateur par rapport à la proposition
            if(votes.get("system").getAsInt() == 1 || votes.get("system").getAsInt() == 2){
                ArrayList<Integer> positiveVotes = getRandomUsers(nbProposal, random);
                ArrayList<Integer> negativeVotes = getRandomUsers(nbProposal, random);
                negativeVotes.removeAll(positiveVotes);
                votes.add("positive", gson.toJsonTree(positiveVotes));
                votes.add("negative", gson.toJsonTree(negativeVotes));
            }else{
                votes.add("positive", gson.toJsonTree(new ArrayList<>()));
                votes.add("negative", gson.toJsonTree(new ArrayList<>()));
            }

            proposal.add("vote", votes);

            JsonObject reactions = new JsonObject();
            ArrayList<Integer> likes = getRandomUsers(nbUser, random);
            ArrayList<Integer> dislikes = getRandomUsers(nbUser, random);
            dislikes.removeAll(likes);
            reactions.add("like", gson.toJsonTree(likes));
            reactions.add("dislike", gson.toJsonTree(dislikes));
            proposal.add("reaction", reactions);

            proposalsArray.add(proposal);
        }

        return gson.toJson(proposalsArray);
    }

    /**
     * Génère un objet JSON représentant une communauté avec un budget global, des thèmes,
     * des frais fixes et des membres, basé sur les paramètres spécifiés.
     *
     * @param nbTheme Le nombre de thèmes disponibles dans la communauté.
     * @param globalBudget Le budget global de la communauté.
     * @param nbUser Le nombre d'utilisateurs dans la communauté.
     * @return Une chaîne JSON représentant la communauté générée.
     */
    public static String generateCommunity(int nbTheme, int globalBudget, int nbUser) {
        Random random = new Random();
        ArrayList<Theme> themes = new ArrayList<>();

        // Calculer la limite de budget allouée aux thèmes (70% du budget global par exemple)
        int fixedFees = globalBudget * (random.nextInt(3)+1)/10;
        int remainingBudget = (int) (globalBudget * 0.7) - fixedFees;

        for (int i = 1; i <= nbTheme; i++) {
            // Chaque thème obtient une fraction aléatoire du budget restant
            int themeBudget = random.nextInt(remainingBudget / (nbTheme - i + 1)) + 1; // Minimum 1, maximum budget restant
            themes.add(new Theme(i,"Thème " + i, themeBudget));
            remainingBudget -= themeBudget;
        }

        // Créer une communauté avec le budget et les thèmes générés
        Community community = new Community(globalBudget, themes, fixedFees, nbUser);

        // Convertir l'objet Community en JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(community);
    }

    /**
     * Génère une liste aléatoire d'identifiants d'utilisateurs uniques.
     *
     * @param numUsers Le nombre maximal d'utilisateurs possibles.
     * @param random L'instance de Random utilisée pour générer des nombres aléatoires.
     * @return Une liste contenant des identifiants d'utilisateurs uniques.
     */
    private static ArrayList<Integer> getRandomUsers(int numUsers, Random random) {
        ArrayList<Integer> userList = new ArrayList<>();
        int numberOfUsers = random.nextInt(numUsers) + 1;

        while (userList.size() < numberOfUsers) {
            int userId = random.nextInt(numUsers) + 1;
            if (!userList.contains(userId)) {
                userList.add(userId);
            }
        }

        return userList;
    }

    /**
     * Convertit une chaîne JSON représentant une liste de propositions en une liste d'objets `Proposal`.
     *
     * @param json La chaîne JSON contenant les propositions.
     * @return Une liste d'objets `Proposal` convertis depuis le JSON.
     */
    public static ArrayList<Proposal> convertJsonToProposals(String json) {
        Gson gson = new Gson();
        Proposal[] proposalsArray = gson.fromJson(json, Proposal[].class);
        ArrayList<Proposal> proposals = new ArrayList<>();

        Collections.addAll(proposals, proposalsArray);

        return proposals;
    }

    /**
     * Convertit une chaîne JSON représentant une communauté en un objet `Community`.
     *
     * @param json La chaîne JSON contenant les informations de la communauté.
     * @return Un objet `Community` converti depuis le JSON.
     */
    public static Community convertJsonToCommunity(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Community.class);
    }
}
