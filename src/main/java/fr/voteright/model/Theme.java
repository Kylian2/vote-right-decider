package fr.voteright.model;
import com.google.gson.annotations.SerializedName;
public class Theme {
    @SerializedName("THM_id_NB")
    private int id;

    @SerializedName("THM_name_VC")
    private String name;

    @SerializedName("BUT_amount_NB")
    private int budget;

    @SerializedName("BUT_used_budget_NB")
    private int usedBudget;

    public Theme(int id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.budget = amount;
        this.usedBudget = 0;
    }

    public Theme(Theme theme) {
        this.id = theme.id;
        this.name = theme.name;
        this.budget = theme.budget;
        this.usedBudget = theme.usedBudget;
    }

    public int getUsedBudget() {
        return usedBudget;
    }

    public void useBudget(int amount){
        if(amount <= budget){
            this.usedBudget += amount;
        }
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getBudget() {
        return budget;
    }

    public String getName() {
        return name;
    }
}
