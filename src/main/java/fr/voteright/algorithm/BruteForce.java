package fr.voteright.algorithm;

import com.google.gson.Gson;
import fr.voteright.model.Community;
import fr.voteright.model.Proposal;
import fr.voteright.utils.List;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BruteForce {

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

        BruteForce bruteForce = new BruteForce();

        List<Proposal> maximiseTotalSatisfaction = bruteForce.maximizeTotalSatisfaction(proposals, community);
        if(Greedy.totalSatisfaction(maximiseTotalSatisfaction.toArrayList()) == 70){
            System.out.println("OK -- BRUTE FORCE MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }else{
            System.out.println("ERROR -- BRUTE FORCE MAXIMISE TOTAL SATISFACTION -- TEST FAILED 70 EXPECTED, OBTAINED " + totalSatisfaction(maximiseTotalSatisfaction.toArrayList()));
        }

        try{
            bruteForce.maximizeTotalSatisfaction(new ArrayList<>(), community);
            System.out.println("ERROR -- BRUTE FORCE MAXIMISE TOTAL SATISFACTION -- EXCEPTION EXPECTED BECAUSE OF EMPTY LIST");
        }catch (Exception e){
            System.out.println("OK -- BRUTE FORCE MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }

        try{
            bruteForce.maximizeTotalSatisfaction(new ArrayList<>(), null);
            System.out.println("ERROR -- BRUTE FORCE MAXIMISE TOTAL SATISFACTION -- EXCEPTION EXPECTED BECAUSE OF INVALID COMMUNITY");
        }catch (Exception e){
            System.out.println("OK -- BRUTE FORCE MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }

        maximiseTotalSatisfaction = bruteForce.maximizeTotalSatisfaction(proposals, communityWithoutBudget);
        if(Greedy.totalSatisfaction(maximiseTotalSatisfaction.toArrayList()) == 0){
            System.out.println("OK -- BRUTE FORCE MAXIMISE TOTAL SATISFACTION -- TEST PASSED");
        }else{
            System.out.println("ERROR -- BRUTE FORCE MAXIMISE TOTAL SATISFACTION -- TEST FAILED 0 EXPECTED, OBTAINED " + totalSatisfaction(maximiseTotalSatisfaction.toArrayList()));
        }

        List<Proposal> minimizeBudget = bruteForce.minimizeBudget(proposals, community);
        try{
            bruteForce.minimizeBudget(new ArrayList<>(), community);
            System.out.println("ERROR -- BRUTE FORCE MINIMIZE BUDGET -- EXCEPTION EXPECTED BECAUSE OF EMPTY LIST");
        }catch (Exception e){
            System.out.println("OK -- BRUTE FORCE MINIMIZE BUDGET -- TEST PASSED");
        }

        try{
            bruteForce.minimizeBudget(new ArrayList<>(), null);
            System.out.println("ERROR -- BRUTE FORCE MINIMIZE BUDGET -- EXCEPTION EXPECTED BECAUSE OF INVALID COMMUNITY");
        }catch (Exception e){
            System.out.println("OK -- BRUTE FORCE MINIMIZE BUDGET -- TEST PASSED");
        }
    }

    /**
     * Optimise la sélection des propositions pour maximiser la satisfaction totale de la communauté,
     * tout en respectant les contraintes budgétaires des thèmes. Utilise une approche avec mémoïsation
     * pour éviter le recalcul des sous-problèmes déjà résolus.
     *
     * @param proposals La liste des propositions à examiner.
     * @param community La communauté avec ses membres, ses budgets thématiques et ses informations associées.
     * @return Une liste chaînée contenant les propositions sélectionnées pour maximiser la satisfaction.
     * @throws Exception Si la liste des propositions est vide, si la communauté est null,
     *                   ou si ses thèmes sont invalides ou vides.
     */
    public List<Proposal> maximizeTotalSatisfaction(ArrayList<Proposal> proposals, Community community) throws Exception {
        if (proposals.isEmpty()) {
            throw new Exception("Une liste de proposition non vide est attendue");
        }
        if (community == null || community.getThemes() == null || community.getThemes().isEmpty()) {
            throw new Exception("La communauté est null ou contient un élément invalide");
        }

        // Garde uniquement les propositions valides (respectent le budget)
        ArrayList<Proposal> validProposals = new ArrayList<>();
        for (Proposal proposal : proposals) {
            int themeIndex = proposal.getTheme() - 1;
            float availableBudget = community.getThemes().get(themeIndex).getBudget() - community.getThemes().get(themeIndex).getUsedBudget();
            if (proposal.getBudget() <= availableBudget) {
                validProposals.add(proposal);
            }
        }
        if (validProposals.isEmpty()) {
            return new List<>(null); // Retourne une liste vide si aucune proposition n'est valide
        }

        // Mémoïsation pour éviter de recalculer les mêmes sous-problèmes
        Map<String, List<Proposal>> memoizationCache = new HashMap<>();
        return maximizeTotalSatisfactionWithMemoization(validProposals, community, memoizationCache);
    }

    /**
     * Fonction auxiliaire récursive utilisant la mémoïsation pour maximiser la satisfaction totale.
     * Elle vérifie d'abord dans le cache si une solution équivalente a déjà été calculée, et si oui,
     * elle la retourne directement. Sinon, elle calcule récursivement la meilleure solution possible.
     *
     * @param proposals La liste des propositions valides restantes.
     * @param community Une instance de la communauté représentant l'état courant des budgets thématiques.
     * @param cache Une map de mémoïsation associant un état de la communauté et des propositions restantes
     *              à une solution optimisée.
     * @return Une liste chaînée contenant les propositions sélectionnées pour cette branche de calcul.
     */
    private List<Proposal> maximizeTotalSatisfactionWithMemoization(ArrayList<Proposal> proposals, Community community, Map<String, List<Proposal>> cache) {
        // Vérifier si une proposition semblable n'a pas déjà été trouvée
        String communityState = generateKey(community.getId(), proposals);
        if (cache.containsKey(communityState)) {
            return cache.get(communityState);
        }

        // Si aucune proposition n'est restante, retourner une liste vide
        if (proposals.isEmpty()) {
            cache.put(communityState, new List<>(null)); //mémoriser le résultat
            return new List<>(null);
        }

        // Garde uniquement les propositions valides (respectent le budget)
        ArrayList<Proposal> validProposals = new ArrayList<>();
        for (Proposal proposal : proposals) {
            int themeIndex = proposal.getTheme() - 1;
            float availableBudget = community.getThemes().get(themeIndex).getBudget() - community.getThemes().get(themeIndex).getUsedBudget();
            if (proposal.getBudget() <= availableBudget) {
                validProposals.add(proposal);
            }
        }
        if (validProposals.isEmpty()) {
            return new List<>(null); // Retourne une liste vide si aucune proposition n'est valide
        }

        List<Proposal> best = null;
        int bestSatisfaction = -1;

        for (int i = 0; i < validProposals.size(); i++) {
            Proposal currentProposal = validProposals.get(i);

            // Créer une copie indépendante de la communauté pour éviter les problemes d'adressage
            Community newCommunity = new Community(community);
            newCommunity.getThemes().get(currentProposal.getTheme() - 1).useBudget(currentProposal.getBudget());

            // Créer une nouvelle liste des propositions restantes pour eviter les problèmes d'adressage
            ArrayList<Proposal> remainingProposals = new ArrayList<>(validProposals);
            remainingProposals.remove(i);

            List<Proposal> currentSolution = new List<>(currentProposal);
            currentSolution.setTail(maximizeTotalSatisfactionWithMemoization(remainingProposals, newCommunity, cache));

            int currentSatisfaction = totalSatisfaction(currentSolution.toArrayList());

            if (currentSatisfaction > bestSatisfaction) {
                bestSatisfaction = currentSatisfaction;
                best = currentSolution;
            }
        }

        // Mémoriser le résultat
        cache.put(communityState, best);
        return best;
    }

    /**
     * Génère une clé unique pour représenter l'état courant de la communauté et des propositions restantes.
     * Cette clé est utilisée pour la mémoïsation afin d'identifier des calculs déjà effectués.
     *
     * @param communityId L'identifiant unique de la communauté.
     * @param remainingProposals La liste des propositions restantes à examiner.
     * @return Une chaîne de caractères représentant l'état unique de la communauté et des propositions.
     */
    public String generateKey(int communityId, ArrayList<Proposal> remainingProposals) {
        StringBuilder sb = new StringBuilder();
        sb.append("CommunityID:").append(communityId).append(";Proposals:");
        for (Proposal proposal : remainingProposals) {
            sb.append(proposal.getId()).append(",");
        }
        if (!remainingProposals.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1); // Supprime la dernière virgule
        }
        return sb.toString();
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
     * chaque utilisateur a au moins un vote satisfait. Utilise une approche avec mémoïsation
     * pour éviter le recalcul des sous-problèmes déjà résolus.
     *
     * @param proposals La liste des propositions à examiner.
     * @param community La communauté avec ses membres, ses budgets thématiques et ses informations associées.
     * @return Une liste chaînée contenant les propositions sélectionnées pour maximiser la satisfaction.
     * @throws Exception Si la liste des propositions est vide, si la communauté est null,
     *                   ou si ses thèmes sont invalides ou vides.
     */
    public List<Proposal> minimizeBudget(ArrayList<Proposal> proposals, Community community) throws Exception {
        if (proposals.isEmpty()) {
            throw new Exception("Une liste de proposition non vide est attendue");
        }
        if (community == null || community.getThemes() == null || community.getThemes().isEmpty()) {
            throw new Exception("La communauté est null ou contient un élément invalide");
        }

        // Garde uniquement les propositions valides (respectent le budget)
        ArrayList<Proposal> validProposals = new ArrayList<>();
        for (Proposal proposal : proposals) {
            int themeIndex = proposal.getTheme() - 1;
            float availableBudget = community.getThemes().get(themeIndex).getBudget() - community.getThemes().get(themeIndex).getUsedBudget();
            if (proposal.getBudget() <= availableBudget) {
                validProposals.add(proposal);
            }
        }
        if (validProposals.isEmpty()) {
            return new List<>(null); // Retourne une liste vide si aucune proposition n'est valide
        }

        // Mémoïsation pour éviter de recalculer les mêmes sous-problèmes
        Map<String, List<Proposal>> memoizationCache = new HashMap<>();
        return minimizeBudgetWithMemoization(validProposals, community, memoizationCache);
    }

    /**
     * @author Mathieu GUIBORAT--BOST
     * Fonction auxiliaire récursive utilisant la mémoïsation pour minimiser le budget, tout en garantissant que
     * chaque utilisateur a au moins un vote satisfait.
     * Elle vérifie d'abord dans le cache si une solution équivalente a déjà été calculée, et si oui,
     * elle la retourne directement. Sinon, elle calcule récursivement la meilleure solution possible.
     *
     * @param proposals La liste des propositions valides restantes.
     * @param community Une instance de la communauté représentant l'état courant des budgets thématiques.
     * @param cache Une map de mémoïsation associant un état de la communauté et des propositions restantes
     *              à une solution optimisée.
     * @return Une liste chaînée contenant les propositions sélectionnées pour cette branche de calcul.
     */
    private List<Proposal> minimizeBudgetWithMemoization(ArrayList<Proposal> proposals, Community community, Map<String, List<Proposal>> cache) {
        // Vérifier si une proposition semblable n'a pas déjà été trouvée
        String communityState = generateKey(community.getId(), proposals);
        if (cache.containsKey(communityState)) {
            return cache.get(communityState);
        }

        // Si aucune proposition n'est restante, retourner une liste vide
        if (proposals.isEmpty()) {
            cache.put(communityState, new List<>(null)); //mémoriser le résultat
            return new List<>(null);
        }

        // Garde uniquement les propositions valides (respectent le budget)
        ArrayList<Proposal> validProposals = new ArrayList<>();
        for (Proposal proposal : proposals) {
            int themeIndex = proposal.getTheme() - 1;
            float availableBudget = community.getThemes().get(themeIndex).getBudget() - community.getThemes().get(themeIndex).getUsedBudget();
            if (proposal.getBudget() <= availableBudget) {
                validProposals.add(proposal);
            }
        }
        if (validProposals.isEmpty()) {
            return new List<>(null); // Retourne une liste vide si aucune proposition n'est valide
        }

        List<Proposal> best = null;
        float minimumBudget = community.getBudget() + 1;

        for (int i = 0; i < validProposals.size(); i++) {
            Proposal currentProposal = validProposals.get(i);

            // Créer une copie indépendante de la communauté pour éviter les problemes d'adressage
            Community newCommunity = new Community(community);
            newCommunity.getThemes().get(currentProposal.getTheme() - 1).useBudget(currentProposal.getBudget());
            newCommunity.useBudget(currentProposal.getBudget());

            // Créer une nouvelle liste des propositions restantes pour eviter les problèmes d'adressage
            ArrayList<Proposal> remainingProposals = new ArrayList<>(validProposals);
            remainingProposals.remove(i);

            List<Proposal> currentSolution = new List<>(currentProposal);
            currentSolution.setTail(minimizeBudgetWithMemoization(remainingProposals, newCommunity, cache));

            if (everyUserHasAtLeastOneSatisfiedVote(currentSolution.toArrayList(), newCommunity.getNumberOfMembers()) && newCommunity.getUsedBudget() < minimumBudget) {
                minimumBudget = newCommunity.getUsedBudget();
                best = currentSolution;
            }
        }

        // Mémoriser le résultat
        cache.put(communityState, best);
        return best;
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
