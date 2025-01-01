package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

public class Proposal {

    @SerializedName("PRO_id_NB")
    private String id;

    @SerializedName("PRO_title_VC")
    private String title;

    @SerializedName("PRO_color_VC")
    private String color;

    @SerializedName("PRO_period_YEAR")
    private String period;

    @SerializedName("PRO_budget_NB")
    private int budget;

    @SerializedName("PRO_theme_VC")
    private String theme;

    @SerializedName("PRO_like_NB")
    private int likeCount;

    @SerializedName("PRO_dislike_NB")
    private int dislikeCount;

    @SerializedName("PRO_love_NB")
    private int loveCount;

    @SerializedName("PRO_hate_NB")
    private int hateCount;

    // Constructeur, getters et setters

    public Proposal(String id, String title, String color, String period, int budget, String theme, int likeCount, int dislikeCount, int loveCount, int hateCount) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
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
}
