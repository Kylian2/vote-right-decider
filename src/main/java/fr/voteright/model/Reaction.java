package fr.voteright.model;

import java.util.ArrayList;

public class Reaction {
    private int like;
    private int nblove;
    private int dislike;
    private int nbhate;
    private int percentageSatisfaction;

    public Reaction(int like, int nblove, int dislike, int nbhate, int percentageSatisfaction) {
        this.like = like;
        this.nblove = like;
        this.dislike = dislike;
        this.nbhate = dislike;
        this.percentageSatisfaction = percentageSatisfaction;
    }

    public Reaction(Reaction reaction) {
        this.like = reaction.like;
        this.nblove = reaction.nblove;
        this.dislike = reaction.dislike;
        this.nbhate = reaction.nbhate;
        this.percentageSatisfaction = reaction.percentageSatisfaction;
    }

    @Override
    public String toString() {
        return "Reactions{" +
                "like=" + like +
                ", dislike=" + dislike +
                '}';
    }

    public int getLike() {return like;}

    public int getNblove() {return nblove;}

    public int getDislike() {return dislike;}

    public int getNbhate() {return nbhate;}

    public int getPercentageSatisfaction() {return percentageSatisfaction;}
}
