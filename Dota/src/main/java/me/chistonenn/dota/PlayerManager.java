package me.chistonenn.dota;

public class PlayerManager {
    private int minerlevel;
    private int warlevel;
    private int minerxp;
    private int warxp;
    private int rating;
    private int wins;
    private int losses;

    public PlayerManager(int minerlevel, int warlevel, int minerxp, int warxp, int rating, int wins, int losses) {
        this.minerlevel = minerlevel;
        this.warlevel = warlevel;
        this.minerxp = minerxp;
        this.warxp = warxp;
        this.rating = rating;
        this.wins = wins;
        this.losses = losses;
    }

    public int getMinerLevel() {
        return this.minerlevel;
    }

    public void setMinerLevel(int minerlevel) {
        this.minerlevel = minerlevel;
    }

    public int getWarLevel() {
        return this.warlevel;
    }

    public void setWarLevel(int warlevel) {
        this.warlevel = warlevel;
    }

    public int getMinerXp() {
        return this.minerxp;
    }

    public void setMinerXp(int minerxp) {
        this.minerxp = minerxp;
    }

    public int getWarXp() {
        return this.warxp;
    }

    public void setWarXp(int warxp) {
        this.warxp = warxp;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getWins() {
        return this.wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return this.losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}
