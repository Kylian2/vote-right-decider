package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

public class Theme {
    @SerializedName("THM_id_NB")
    private int id;

    @SerializedName("THM_name_VC")
    private String name;

    @SerializedName("THM_amount_NB")
    private float amount;

    // Constructeurs, getters et setters

    public Theme(int idTheme,String name, float amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
