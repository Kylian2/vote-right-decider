package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Vote {
    @SerializedName("system")
    private int system;
    @SerializedName("positive")
    private ArrayList<Integer> positive;
    @SerializedName("negative")
    private ArrayList<Integer> negative;

    public Vote(ArrayList<Integer> positive, ArrayList<Integer> negative, int system) {
        this.positive = positive;
        this.negative = negative;
        this.system = system;
    }

    public int getSystem() {
        return system;
    }

    public ArrayList<Integer> getPositive() {
        return positive;
    }

    public ArrayList<Integer> getNegative() {
        return negative;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "positive=" + positive +
                ", negative=" + negative +
                '}';
    }
}
