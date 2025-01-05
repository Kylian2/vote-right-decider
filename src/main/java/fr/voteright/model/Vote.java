package fr.voteright.model;

import java.util.ArrayList;

public class Vote {
    private String system;
    private int nbRound;
    private int percentageParticipation;

    private ArrayList<Integer> positive;
    private ArrayList<Integer> negative;

    public Vote(ArrayList<Integer> positive, ArrayList<Integer> negative, String system, int nbRound, int percentageParticipation) {
        this.positive = positive;
        this.negative = negative;
        this.system = system;
        this.nbRound = nbRound;
        this.percentageParticipation = percentageParticipation;
    }

    public Vote(String system, int nbRound, int percentageParticipation) {
        this.system = system;
        this.nbRound = nbRound;
        this.percentageParticipation = percentageParticipation;
    }

    public String getSystem() {
        return system;
    }

    public int getNbRound() {
        return nbRound;
    }

    public int getpercentageParticipation() {
        return percentageParticipation;
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
