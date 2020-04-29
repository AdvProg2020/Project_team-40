package model;

public class Score {
    private String username;
    private int score;
    private String productID;

    public Score(String username, int score, String productID) {
        this.username = username;
        this.score = score;
        this.productID = productID;
    }

    public String getUserName(){
        return username;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
