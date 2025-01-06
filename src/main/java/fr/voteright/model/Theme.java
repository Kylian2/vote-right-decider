package fr.voteright.model;
import com.google.gson.annotations.SerializedName;
public class Theme {
    @SerializedName("THM_id_NB")
    private int id;

    @SerializedName("THM_name_VC")
    private String name;

    @SerializedName("BUT_amount_NB")
    private float budget;

    @SerializedName("BUT_used_budget_NB")
    private float usedBudget;

    public Theme(int id, String name, float amount) {
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

    public int getId() {
        return id;
    }

    public float getUsedBudget() {
        return usedBudget;
    }

    public void useBudget(float amount){
        if(amount <= budget){
            this.usedBudget += amount;
        }
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getBudget() {
        return budget;
    }

    public String getName() {
        return name;
    }
}
