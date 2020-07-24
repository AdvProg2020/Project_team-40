package server.model.chat;

import exceptions.DataException;
import server.model.Utility;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chat implements Serializable{
    private static final String PATH = "src/main/resources/chats/";
    private static HashMap<String, Chat> allChats = new HashMap<>();

    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<String> members = new ArrayList<>();
    private String name;
    private String id;
    private boolean isSupport;

    public Chat(String name){
        this.id = Utility.generateId();
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    public ArrayList<String> getMembers(){
        return members;
    }

    public static HashMap<String, Chat> getAllChats(){
        return allChats;
    }

    public static Chat getChat(String id){
        for(Map.Entry<String, Chat> entry : allChats.entrySet()) {
            if(entry.getKey().equals(id))
                return entry.getValue();
        }
        return null;
    }

    public void setIsSupport(boolean isSupport){
        this.isSupport = isSupport;
    }

    public boolean isSupport(){
        return isSupport;
    }

    public static void loadData() throws DataException{
        File directory = new File(PATH);
        String[] pathNames = directory.list();
        if (pathNames == null)
            return;
        for (String path : pathNames) {
            try {
                FileInputStream file = new FileInputStream(PATH + path);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                Chat chat = (Chat) inputStream.readObject();
                allChats.put(chat.getId(), chat);
                file.close();
                inputStream.close();
                new File(PATH + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading chats failed.");
            }
        }
    }

    public static void saveData() throws DataException{
        File directory = new File(PATH);
        if(!directory.exists())
            if(!directory.mkdir())
                throw new DataException("Saving chats failed.");

        for(Map.Entry<String, Chat> entry : allChats.entrySet()) {
            try {
                Chat chat = entry.getValue();
                FileOutputStream file = new FileOutputStream(PATH + entry.getKey());
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(chat);
                file.close();
                outputStream.close();

            } catch (Exception e) {
                throw new DataException("Saving chats failed.");
            }
        }
    }
}
