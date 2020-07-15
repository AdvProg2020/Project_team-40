package server.controller.menus;

import exceptions.MenuException;
import server.model.chat.Chat;
import server.model.chat.Message;
import server.model.users.Support;
import server.model.users.User;

import java.util.ArrayList;

public class ChatController{
    private static ChatController chatController;

    private ChatController(){

    }

    public static ChatController getInstance(){
        if(chatController == null)
            chatController = new ChatController();
        return chatController;
    }

    public void createChat(String name){
        Chat chat = new Chat(name);
    }

    public void addUser(String id, String username) throws MenuException{
        Chat chat = Chat.getChat(id);
        if(chat == null)
            throw new MenuException("No chat with such id.");
        if(User.getUserByUsername(username) == null)
            throw new MenuException("No user with such username.");
        chat.getUsers().add(username);
    }

    public ArrayList<User> getUsers(String id) throws MenuException{
        Chat chat = Chat.getChat(id);
        if(chat == null)
            throw new MenuException("No chat with such id.");
        ArrayList<User> users = new ArrayList<>();
        for(String s : chat.getUsers()) {
            User user = User.getUserByUsername(s);
            if(user != null)
                users.add(user);
        }
        return users;
    }

    public Support getSupport(String username) throws MenuException{
        Support result = null;
        for(Support support : Support.getAllSupports()) {
            if(support.getUsername().equals(username))
                result = support;
        }
        if(result == null)
            throw new MenuException("No support with such name exists.");
        return result;
    }

    public void addMessage(String id, Message message) throws MenuException{
        Chat chat = Chat.getChat(id);
        if(chat == null)
            throw new MenuException("No chat with such id.");
        chat.getMessages().add(message);
    }

    public ArrayList<Message> getMessages(String id) throws MenuException{
        Chat chat = Chat.getChat(id);
        if(chat == null)
            throw new MenuException("No chat with such id.");
        return chat.getMessages();
    }

    //index is the number of messages a user has already received
    public ArrayList<Message> getNewMessages(String id, int size) throws MenuException{
        Chat chat = Chat.getChat(id);
        if(chat == null)
            throw new MenuException("No chat with such id.");
        ArrayList<Message> newMessages = new ArrayList<>();
        ArrayList<Message> allMessages = chat.getMessages();
        for(int i = size; i < allMessages.size(); i++){
            newMessages.add(allMessages.get(i));
        }
        return  newMessages;
    }
}
