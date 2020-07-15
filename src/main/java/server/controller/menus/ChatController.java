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

    public Chat createChat(String name){
        Chat chat = new Chat(name);
        Chat.getAllChats().put(chat.getId(), chat);
        return chat;
    }

    public Chat getChat(String id) throws MenuException{
        Chat chat = Chat.getChat(id);
        if(chat == null)
            throw new MenuException("No chat with such id.");
        return chat;
    }

    public void addMember(String id, String username) throws MenuException{
        Chat chat = Chat.getChat(id);
        if(chat == null)
            throw new MenuException("No chat with such id.");
        if(User.getUserByUsername(username) == null)
            throw new MenuException("No user with such username.");
        chat.getMembers().add(username);
    }

    public ArrayList<String> getMembers(String id) throws MenuException{
        Chat chat = Chat.getChat(id);
        if(chat == null)
            throw new MenuException("No chat with such id.");
        return chat.getMembers();
    }

    public ArrayList<String> getSupports(){
        ArrayList<String> supports = new ArrayList<>();
        ArrayList<Support> allSupports = Support.getAllSupports();
        for(Support support : allSupports) {
            supports.add(support.getUsername());
        }
        return supports;
    }

    public Support getSupport(String username) throws MenuException{
        Support support = Support.getSupport(username);
        if(support == null)
            throw new MenuException("No support with such name exists.");
        return support;
    }

    public void addMessage(String id, Message message) throws MenuException{
        Chat chat = Chat.getChat(id);
        if(chat == null)
            throw new MenuException("No chat with such id.");
        chat.getMessages().add(message);
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
