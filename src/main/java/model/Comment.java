package model;

import model.enumerations.Status;
import model.users.User;

public class Comment  {
    private User user;
    private Product product;
    private String text;
    private Status status;
    private boolean isBought;

    public Comment(User user, Product product,
                   String text, boolean isBought) {
        this.user = user;
        this.product = product;
        this.text = text;
        this.isBought = isBought;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isBought() {
        return isBought;
    }
}
