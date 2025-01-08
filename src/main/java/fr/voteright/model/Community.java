package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Community {

    @SerializedName("CMY_id_NB")
    private int id;

    @SerializedName("CMY_name_VC")
    private String name;

    @SerializedName("CMY_description_TXT")
    private String description;

    @SerializedName("CMY_nb_member_NB")
    private int numberOfMembers;

    @SerializedName("CMY_budget_NB")
    private float budget;

    @SerializedName("CMY_used_budget_NB")
    private float usedBudget;

    @SerializedName("CMY_fixed_fees_NB")
    private float fixedFees;

    @SerializedName("CMY_budget_theme_NB")
    private ArrayList<Theme> themes;

    public Community(int id, String name, String description, int numberOfMembers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfMembers = numberOfMembers;
    }

    public Community(float budget, ArrayList<Theme> themes, float fixedFees, int nbMembers) {
        this.budget = budget;
        this.usedBudget = 0;
        this.fixedFees = fixedFees;
        this.themes = themes;
        this.numberOfMembers = nbMembers;
    }

    public Community(Community community) {
        this.id = community.getId();
        this.name = community.getName();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public float getBudget() {
        return budget;
    }

    public float getUsedBudget() {
        return usedBudget;
    }

    public float getFixedFees() {
        return fixedFees;
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<Theme> themes) {
        this.themes = new ArrayList<>();
        for(Theme theme : themes){
            this.themes.add(new Theme(theme));
        }
    }

    public void setUsedBudget(float usedBudget) {
        this.usedBudget = usedBudget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void setFixedFees(float fixedFees) {
        this.fixedFees = fixedFees;
    }

    public void useBudget(float amount){
        if(amount + usedBudget <= budget){
            this.usedBudget += amount;
        }
    }
}