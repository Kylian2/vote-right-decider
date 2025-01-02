package fr.voteright.model;

import java.util.ArrayList;

public class Vote {
    private int system;
    private ArrayList<Integer> positive;
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
