package server.server_resources.chat;

import exceptions.AccountsException;
import exceptions.MenuException;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.controller.accounts.AccountController;
import server.controller.menus.ChatController;
import server.model.chat.Message;

import java.util.ArrayList;

public class MessageResource extends ServerResource{
    private ChatController controller = ChatController.getInstance();

    @Put
    public void sendMessage(String chatId, String username, String content){
        try {
            AccountController.getInstance().getUser(username);
        }catch(AccountsException e){
            System.out.println(e.getMessage());
        }

        try {
            controller.addMessage(chatId, new Message(username, content, System.currentTimeMillis()));
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
    }

    @Get
    public ArrayList<Message> getMessages(String chatId, int size){
        ArrayList<Message> messages = new ArrayList<>();
        try {
            messages.addAll(controller.getNewMessages(chatId, size));
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
