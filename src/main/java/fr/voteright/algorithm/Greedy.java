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

        List<Proposal> p = greedy.maximizeTotalSatisfaction(proposals, community);
        if(totalSatisfaction(p.toArrayList()) == 64){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }else{
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST FAILED 64 EXPECTED, OBTAINED " + totalSatisfaction(p.toArrayList()));
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

        p = greedy.maximizeTotalSatisfaction(proposals, communityWithoutBudget);
        if(totalSatisfaction(p.toArrayList()) == 0){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }else{
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION -- TEST FAILED 0 EXPECTED, OBTAINED " + totalSatisfaction(p.toArrayList()));
        }

        p = greedy.maximizeTotalSatisfactionRatio(proposals, community);
        if(totalSatisfaction(p.toArrayList()) == 70){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION RATIO -- TEST PASSED");
        }else{
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION RATIO -- TEST FAILED 70 EXPECTED, OBTAINED " + totalSatisfaction(p.toArrayList()));
        }

        try{
            greedy.maximizeTotalSatisfactionRatio(new ArrayList<>(), community);
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION RATIO -- EXCEPTION EXPECTED BECAUSE OF EMPTY LIST");
        }catch (Exception e){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION RATIO -- TEST PASSED");
        }

        try{
            greedy.maximizeTotalSatisfactionRatio(new ArrayList<>(), null);
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION RATIO -- EXCEPTION EXPECTED BECAUSE OF INVALID COMMUNITY");
        }catch (Exception e){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION RATIO -- TEST PASSED");
        }

        p = greedy.maximizeTotalSatisfactionRatio(proposals, communityWithoutBudget);
        if(totalSatisfaction(p.toArrayList()) == 0){
            System.out.println("OK -- GREEDY MAXIMISE TOTAL SATISFACTION RATIO -- TEST PASSED");
        }else{
            System.out.println("ERROR -- GREEDY MAXIMISE TOTAL SATISFACTION RATIO -- TEST FAILED 0 EXPECTED, OBTAINED " + totalSatisfaction(p.toArrayList()));
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
        Community cmy = new Community(community); //pour ne pas modifier la liste en paramètre


        //Création d'une liste de proposition respectant les contraintes budgétaires
        ArrayList<Proposal> validProposals = new ArrayList<Proposal>();
        for (Proposal proposal : proposals) {
            int themeIndex = proposal.getTheme() - 1;
            float availableBudget = cmy.getThemes().get(themeIndex).getBudget() - cmy.getThemes().get(themeIndex).getUsedBudget();
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
        cmy.getThemes().get(validProposals.get(best).getTheme()-1).useBudget(validProposals.get(best).getBudget());
        validProposals.remove(best);
        if(!validProposals.isEmpty()){
            selectedProposals.setTail(maximizeTotalSatisfaction(validProposals, cmy));
        }

        return selectedProposals;
    }

    /**
     * Sélectionne une liste optimale de propositions afin de maximiser la satisfaction globale
     * en respectant les contraintes budgétaires de chaque thème de la communauté.
     * Cette méthode utilise un ratio budget/satisfaction pour prioriser les propositions.
     *
     * @param proposals La liste des propositions à examiner.
     * @param community La communauté contenant les thèmes et budgets associés.
     * @return Une liste chaînée de propositions optimisées pour maximiser la satisfaction globale.
     * @throws Exception Si la liste des propositions est vide ou si la communauté est invalide.
     */
    public List<Proposal> maximizeTotalSatisfactionRatio(ArrayList<Proposal> proposals, Community community) throws Exception {
        if(proposals.isEmpty()){
            throw new Exception("Une liste de proposition non vide est attendue");
        }
        if(community == null || community.getThemes() == null || community.getThemes().isEmpty()){
            throw new Exception("La communauté est null ou contient un element invalide");
        }
        Community cmy = new Community(community); //pour ne pas modifier la liste en paramètre

        //Création d'une liste de proposition respectant les contraintes budgétaires
        ArrayList<Proposal> validProposals = new ArrayList<Proposal>();
        for (Proposal proposal : proposals) {
            int themeIndex = proposal.getTheme() - 1;
            float availableBudget = cmy.getThemes().get(themeIndex).getBudget() - cmy.getThemes().get(themeIndex).getUsedBudget();
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
        double satisfactionBestRatio = validProposals.getFirst().getBudget() / (double) validProposals.getFirst().satisfiedUser();

        for (int i = 1; i < validProposals.size(); i++) {
            double ratio = validProposals.get(i).getBudget() / (double) validProposals.get(i).satisfiedUser();
            if(ratio < satisfactionBestRatio){
                satisfactionBestRatio = ratio;
                best = i;
            }
        }

        selectedProposals.setData(validProposals.get(best));
        cmy.getThemes().get(validProposals.get(best).getTheme()-1).useBudget(validProposals.get(best).getBudget());
        validProposals.remove(best);
        if(!validProposals.isEmpty()){
            selectedProposals.setTail(maximizeTotalSatisfaction(validProposals, cmy));
        }

        return selectedProposals;
    }

    public static int totalSatisfaction(ArrayList<Proposal> proposals){
        int totalSatisfaction = 0;
        for(Proposal p : proposals){
            totalSatisfaction += p.satisfiedUser();
        }
        return totalSatisfaction;
    }

    /**
     * @author Mathieu GUIBORAT--BOST
     * Optimise la sélection des propositions pour minimiser le budget, tout en garantissant que
     * chaque utilisateur a au moins un vote satisfait.
     * Sélectionne à chaque itération de la fonction la proposition avec le budget le plus bas.
     * Retourne une liste chaînée des propositions sélectionnées.
     *
     * @param proposals La liste des propositions à examiner.
     * @param community La communauté avec ses membres et ses budgets thématiques.
     * @return Une liste chaînée contenant les propositions sélectionnées pour minimiser le budget. Si aucune n'est selectionnée l'element data de la liste sera null.
     * @throws Exception Si la liste des propositions est vide ou si la communauté est null.
     */
    public List<Proposal> minimizeBudget(ArrayList<Proposal> proposals, Community community, ArrayList<Integer> satisfiedUsers, int numberOfMembers) throws Exception {
        if(proposals.isEmpty()){
            throw new Exception("Une liste de proposition non vide est attendue");
        }
        if(community == null || community.getThemes() == null || community.getThemes().isEmpty()){
            throw new Exception("La communauté est null ou contient un element invalide");
        }
        Community cmy = new Community(community); //pour ne pas modifier la liste en paramètre


        //Création d'une liste de proposition respectant les contraintes budgétaires
        ArrayList<Proposal> validProposals = new ArrayList<Proposal>();
        for (Proposal proposal : proposals) {
            int themeIndex = proposal.getTheme() - 1;
            float availableBudget = cmy.getThemes().get(themeIndex).getBudget() - cmy.getThemes().get(themeIndex).getUsedBudget();
            if (proposal.getBudget() <= availableBudget) {
                validProposals.add(proposal);
            }
        }
        if (validProposals.isEmpty()) {
            return new List<>(null);
        }

        //Selection de la proposition avec le budget le plus bas
        List<Proposal> selectedProposals = new List<>(null);
        int best = 0;
        float minimumBudget = community.getBudget() + 1;

        for (int i = 0; i < validProposals.size(); i++) {
            float budget = validProposals.get(i).getBudget();
            if(budget < minimumBudget){
                minimumBudget = budget;
                best = i;
            }
        }

        Proposal bestProposal = validProposals.get(best);

        for (Integer userId : bestProposal.getSatisfiedUsers()){
            if(!satisfiedUsers.contains(userId)){
                satisfiedUsers.add(userId);
            }
        }

        if(numberOfMembers == satisfiedUsers.size()){
            return selectedProposals;
        }

        selectedProposals.setData(bestProposal);
        cmy.getThemes().get(bestProposal.getTheme()-1).useBudget(bestProposal.getBudget());
        validProposals.remove(best);
        if(!validProposals.isEmpty()){
            selectedProposals.setTail(minimizeBudget(validProposals, cmy, satisfiedUsers, numberOfMembers));
        }

        return selectedProposals;
    }

    public static boolean everyUserHasAtLeastOneSatisfiedVote (ArrayList<Proposal> proposals, int numberOfMembers) {
        ArrayList<Integer> satisfiedUsers = new ArrayList<>();
        for(Proposal p : proposals){
            for(Integer userId : p.getSatisfiedUsers()){
                if(!satisfiedUsers.contains(userId)){
                    satisfiedUsers.add(userId);
                    if(numberOfMembers == satisfiedUsers.size()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
