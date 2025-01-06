package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Proposal {

    @SerializedName("PRO_id_NB")
    private int id;

    @SerializedName("PRO_title_VC")
    private String title;

    @SerializedName("PRO_color_VC")
    private String color;

    @SerializedName("PRO_period_YEAR")
    private String period;

    @SerializedName("PRO_budget_NB")
    private float budget;

    @SerializedName("PRO_theme_NB")
    private int theme;

    @SerializedName("PRO_like_NB")
    private int likeCount;

    @SerializedName("PRO_dislike_NB")
    private int dislikeCount;

    @SerializedName("PRO_love_NB")
    private int loveCount;

    @SerializedName("PRO_hate_NB")
    private int hateCount;

    private Vote vote;
    private Reaction reaction;

    // Constructeur, getters et setters

    public Proposal(int id, String title, String color, String period, float budget, int theme, int likeCount, int dislikeCount, int loveCount, int hateCount) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.period = period;
        this.budget = budget;
        this.theme = theme;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.loveCount = loveCount;
        this.hateCount = hateCount;
    }

    public Proposal(int id, String title, float budget, Vote vote, Reaction reaction) {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(int loveCount) {
        this.loveCount = loveCount;
    }

    public int getHateCount() {
        return hateCount;
    }

    public void setHateCount(int hateCount) {
        this.hateCount = hateCount;
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
