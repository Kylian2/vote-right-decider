package fr.voteright.algorithm;

import fr.voteright.model.Community;
import fr.voteright.model.Proposal;
import fr.voteright.utils.List;
import com.google.gson.Gson;
import fr.voteright.utils.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Greedy {

    public static void main(String[] args) throws Exception {

        String filePath = Greedy.class.getClassLoader().getResource("proposals-examples.json").getPath();
        ArrayList<Proposal> proposals = new ArrayList<Proposal>();

        Gson gson = new Gson();
        FileReader reader = new FileReader(filePath);
        Proposal[] proposalsArray = gson.fromJson(reader, Proposal[].class);
        Collections.addAll(proposals, proposalsArray);

        reader.close();
        gson = new Gson();
        filePath = Greedy.class.getClassLoader().getResource("community-example.json").getPath();
        reader = new FileReader(filePath);
        Community community = gson.fromJson(reader, Community.class);

        reader.close();
        gson = new Gson();
        filePath = Greedy.class.getClassLoader().getResource("community-without-budget.json").getPath();
        reader = new FileReader(filePath);
        Community communityWithoutBudget = gson.fromJson(reader, Community.class);

        Greedy greedy = new Greedy();

        List<Proposal> maximiseTotalSatisfaction = greedy.maximizeTotalSatisfaction(proposals, community);
        if(greedy.totalSatisfaction(maximiseTotalSatisfaction.toArrayList(), community) == 64){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }else{
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST FAILED 64 EXPECTED, OBTAINED " + greedy.totalSatisfaction(maximiseTotalSatisfaction.toArrayList(), community));
        }

        try{
            greedy.maximizeTotalSatisfaction(new ArrayList<>(), community);
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION -- EXCEPTION EXPECTED BECAUSE OF EMPTY LIST");
        }catch (Exception e){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }

        try{
            greedy.maximizeTotalSatisfaction(new ArrayList<>(), null);
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION -- EXCEPTION EXPECTED BECAUSE OF INVALID COMMUNITY");
        }catch (Exception e){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }

        maximiseTotalSatisfaction = greedy.maximizeTotalSatisfaction(proposals, communityWithoutBudget);
        if(greedy.totalSatisfaction(maximiseTotalSatisfaction.toArrayList(), community) == 0){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }else{
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST FAILED 0 EXPECTED, OBTAINED " + greedy.totalSatisfaction(maximiseTotalSatisfaction.toArrayList(), community));
        }

    }

    /**
     * Optimise la sélection des propositions pour maximiser la satisfaction totale de la communauté,
     * tout en respectant les contraintes budgétaires des thèmes. Retourne une liste chaînée des propositions
     * sélectionnées.
     *
     * @param proposals La liste des propositions à examiner.
     * @param community La communauté avec ses membres et ses budgets thématiques.
     * @return Une liste chaînée contenant les propositions sélectionnées pour maximiser la satisfaction. Si aucune n'est selectionnée l'element data de la liste sera null.
     * @throws Exception Si la liste des propositions est vide ou si la communauté est null.
     */
    public List<Proposal> maximizeTotalSatisfaction(ArrayList<Proposal> proposals, Community community) throws Exception {
        if(proposals.isEmpty()){
            throw new Exception("Une liste de proposition non vide est attendue");
        }
        if(community == null || community.getThemes() == null || community.getThemes().isEmpty()){
            throw new Exception("La communauté est null ou contient un element invalide");
        }

        //Création d'une liste de proposition respectant les contraintes budgétaires
        ArrayList<Proposal> validProposals = new ArrayList<Proposal>();
        for (Proposal proposal : proposals) {
            int themeIndex = proposal.getTheme() - 1;
            int availableBudget = community.getThemes().get(themeIndex).getBudget() - community.getThemes().get(themeIndex).getUsedBudget();
            if (proposal.getBudget() <= availableBudget) {
                validProposals.add(proposal);
            }
        }
        if (validProposals.isEmpty()) {
            return new List<>(null);
        }

        //Selection de la meilleur proposition
        List<Proposal> selectedProposals = new List<>(null);
        int best = 0;
        int satisfactionScoreMax = 0;

        for (int i = 0; i < validProposals.size(); i++) {
            int satisfactionScore = validProposals.get(i).satisfiedUser();
            if(satisfactionScore > satisfactionScoreMax){
                satisfactionScoreMax = satisfactionScore;
                best = i;
            }
        }

        selectedProposals.setData(validProposals.get(best));
        community.getThemes().get(validProposals.get(best).getTheme()-1).useBudget(validProposals.get(best).getBudget());
        validProposals.remove(best);
        if(!validProposals.isEmpty()){
            selectedProposals.setTail(maximizeTotalSatisfaction(validProposals, community));
        }

        return selectedProposals;
    }

    public int totalSatisfaction(ArrayList<Proposal> proposals, Community community){
        int totalSatisfaction = 0;
        for(Proposal p : proposals){
            totalSatisfaction += p.satisfiedUser();
        }
        return totalSatisfaction;
    }

}
