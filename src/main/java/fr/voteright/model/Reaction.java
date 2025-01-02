package fr.voteright.model;

import java.util.ArrayList;

public class Reaction {
    private ArrayList<Integer> like;
    private ArrayList<Integer> dislike;

    public Reaction(ArrayList<Integer> like, ArrayList<Integer> dislike) {
        this.like = like;
        this.dislike = dislike;
    }

    public Reaction(Reaction reaction) {
        this.like = new ArrayList<>(reaction.like);
        this.dislike = new ArrayList<>(reaction.dislike);
    }

    @Override
    public String toString() {
        return "Reactions{" +
                "like=" + like +
                ", dislike=" + dislike +
                '}';
    }

    public ArrayList<Integer> getLike() {
        return like;
    }

    public ArrayList<Integer> getDislike() {
        return dislike;
    }
}
