package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

public class Proposal {

    @SerializedName("PRO_id_NB")
    private int id;

    @SerializedName("PRO_title_VC")
    private String title;

    @SerializedName("PRO_description_TXT")
    private String description;

    @SerializedName("PRO_color_VC")
    private String color;

    @SerializedName("PRO_period_YEAR")
    private String period;

    @SerializedName("PRO_budget_NB")
    private int budget;

    @SerializedName("PRO_theme_NB")
    private int theme;

    private Vote vote;
    private Reaction reaction;

    // Constructeur, getters et setters

    public Proposal(int id, String title, String description,String color, String period, int budget, int theme) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.period = period;
        this.budget = budget;
        this.theme = theme;
    }

    public Proposal(int id, String title, String description, int budget, Vote vote, Reaction reaction) {
        this.id = id;
        this.title = title;
        this.description = description;
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
        this.description = description;
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
     * @param nbUser Le nombre total d'utilisateurs.
     * @return Le nombre d'utilisateurs satisfaits.

    public int satisfiedUser(int nbUser){

        boolean[] like = new boolean[nbUser];
        boolean[] negative = new boolean[nbUser];

        for(int i = 0; i < vote.getPositive().size(); i++){
            if(!like[vote.getPositive().get(i)-1]){
                like[vote.getPositive().get(i)-1] = true;
            }
        }

        for(int i = 0; i < vote.getNegative().size(); i++){
            if(!negative[vote.getNegative().get(i)-1]){
                negative[vote.getNegative().get(i)-1] = true;
            }
        }

        for(int i = 0; i < reaction.getLike().size(); i++){
            if(!like[reaction.getLike().get(i)-1] && !negative[reaction.getLike().get(i)-1]){
                like[reaction.getLike().get(i)-1] = true;
            }
        }

        int nbSatisfiedUser = 0;
        for(boolean b : like){
            if(b){
                nbSatisfiedUser++;
            }
        }
        return nbSatisfiedUser;
    }
     */
}
