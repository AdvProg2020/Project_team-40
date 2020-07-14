package server.model.chat;

import server.model.Utility;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable{
    private static ArrayList<Chat> allChats = new ArrayList<>();

    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();
    private String id;

    public Chat(){
        this.id = Utility.generateId();
    }

    public String getId(){
        return id;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    public ArrayList<String> getUsers(){
        return users;
    }

    public static ArrayList<Chat> getAllChats(){
        return allChats;
    }

    public static void loadData(){

    }

    public static void saveData(){

    }
}
