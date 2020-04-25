package model;

import model.enumerations.Status;
import model.users.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Comment  {
    private User user;
    private Product product;
    private String text;
    private Status status;
    private boolean hasBought;
    private boolean doesSuggest;
    private Date lastUpdate;
    private int numberOfUpdates;
    private ArrayList<String> cons;
    private ArrayList<String> pros;
    private int upvotes;

    public Comment(User user, Product product, String text, boolean isBought,
                   boolean doesSuggest, ArrayList<String> pros, ArrayList<String> cons) {
        this.user = user;
        this.product = product;
        this.text = text;
        this.hasBought = isBought;
        this.doesSuggest = doesSuggest;
        this.pros = pros;
        this.cons = cons;
        this.lastUpdate = new Date();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser(){
        return user;
    }

    public String getText() {
        return text;
    }

    public boolean hasBought() {
        return hasBought;
    }

    public boolean doesSuggest() {
        return doesSuggest;
    }

    public ArrayList<String> getPros() {
        return pros;
    }

    public ArrayList<String> getCons() {
        return cons;
    }

    public Date getLastUpdate(){
        return lastUpdate;
    }

    public void addUpvote() {
        upvotes++;
    }

    public void addDownvote() {
        upvotes--;
    }

    public void updateText(String update){
        numberOfUpdates++;
        lastUpdate = new Date();
        text = text + "\nEdit " + numberOfUpdates + " : " + update;
    }
}
