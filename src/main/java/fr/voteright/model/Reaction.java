package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Reaction {
    @SerializedName("like")
    private ArrayList<Integer> like;
    @SerializedName("dislike")
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
