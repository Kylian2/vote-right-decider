package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Proposal {

    @SerializedName("PRO_id_NB")
    private int id;

    @SerializedName("PRO_title_VC")
    private String title;

    @SerializedName("PRO_description_TXT")
    private String description;

    @SerializedName("PRO_satifaction_NB")
    private int satisfactionPercentage;

    @SerializedName("PRO_budget_NB")
    private int budget;

    @SerializedName("PRO_theme_VC")
    private String themeTextuel;

    @SerializedName("PRO_theme_NB")
    private int theme;

    private Vote vote;
    private Reaction reaction;

    // Constructeur, getters et setters

    public Proposal(int id, String title, String description, int satisfactionPercentage, int budget, String themeTextuel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.satisfactionPercentage = satisfactionPercentage;
        this.budget = budget;
        this.themeTextuel = themeTextuel;
    }

    public Proposal(int id, String title, int budget, Vote vote, Reaction reaction) {
        this.id = id;
        this.title = title;
        this.budget = budget;
        this.vote = vote;
        this.reaction = reaction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.title = description;
    }

    public int getSatisfaction() {
        return satisfactionPercentage;
    }

    public void setSatisfaction(int satisfactionPercentage) {
        this.satisfactionPercentage = satisfactionPercentage;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public String getThemeTextuel() {
        return themeTextuel;
    }

    public void setTheme(String themeTextuel) {
        this.themeTextuel = themeTextuel;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public Vote getVote() {
        return vote;
    }

    /**
     * Calcule le nombre d'utilisateurs satisfaits en fonction des votes positifs,
     * négatifs et des réactions de like. Un utilisateur est considéré comme satisfait
     * s'il a exprimé un vote positif ou a réagi positivement (en likant) sans avoir
     * exprimé un vote négatif.
     *
     * @return Le nombre d'utilisateurs satisfaits.
     */
    public int satisfiedUser(){

        int likeCount = vote.getPositive().size();


        for(int i = 0; i < reaction.getLike().size(); i++){
            if(!vote.getNegative().contains(reaction.getLike().get(i)) && !vote.getPositive().contains(reaction.getLike().get(i))){
                likeCount++;
            }
        }

        return likeCount;
    }
}
