package client.controller;

import server.model.users.User;

public class Client {
    private User user;

    private static Client client = new Client();
    private static String latestToken;

    private Client(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn(){
        return user != null;
    }

    public String getUsername(){
        if (user != null)
            return user.getUsername();
        return null;
    }

    public String getPassword(){
        return user.getPassword();
    }

    public void setLatestToken(String latestToken) {
        Client.latestToken = latestToken;
    }

    public void logout(){
        user = null;
    }
    public static Client getInstance() {
        return client;
    }







}
