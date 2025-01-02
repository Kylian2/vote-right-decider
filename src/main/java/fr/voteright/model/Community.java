package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Community {

    @SerializedName("CMY_id_NB")
    private int id;

    @SerializedName("CMY_name_VC")
    private String name;

    @SerializedName("CMY_image_VC")
    private String image;

    @SerializedName("CMY_emoji_VC")
    private String emoji;

    @SerializedName("CMY_color_VC")
    private String color;

    @SerializedName("CMY_description_TXT")
    private String description;

    @SerializedName("CMY_nb_member_NB")
    private int numberOfMembers;

    @SerializedName("CMY_budget_NB")
    private int budget;

    @SerializedName("CMY_used_budget_NB")
    private int usedBudget;

    @SerializedName("CMY_fixed_fees_NB")
    private int fixedFees;

    @SerializedName("CMY_budget_theme_NB")
    private ArrayList<Theme> themes;

    public Community(int id, String name, String image, String emoji, String color, String description, int numberOfMembers) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.emoji = emoji;
        this.color = color;
        this.description = description;
        this.numberOfMembers = numberOfMembers;
    }

    public Community(int budget, ArrayList<Theme> themes, int fixedFees, int nbMembers) {
        this.budget = budget;
        this.usedBudget = 0;
        this.fixedFees = fixedFees;
        this.themes = themes;
        this.numberOfMembers = nbMembers;
    }

    public Community(Community community) {
        this.id = community.getId();
        this.name = community.getName();
        this.image = community.getImage();
        this.emoji = community.getEmoji();
        this.color = community.getColor();
        this.description = community.getDescription();
        this.numberOfMembers = community.getNumberOfMembers();
        this.budget = community.getBudget();
        this.usedBudget = community.getUsedBudget();
        this.fixedFees = community.getFixedFees();
        this.themes = new ArrayList<>();
        for(Theme theme : community.getThemes()){
            this.themes.add(new Theme(theme));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public int getBudget() {
        return budget;
    }

    public int getUsedBudget() {
        return usedBudget;
    }

    public int getFixedFees() {
        return fixedFees;
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }
}