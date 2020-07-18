package server.server_resources.chat;

import com.gilecode.yagson.YaGson;
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

    //QUERIES : chatId, username, content
    @Put
    public void sendMessage(){
        try {
            AccountController.getInstance().getUser(getQueryValue("username"));
        }catch(AccountsException e){
            System.out.println(e.getMessage());
        }

        try {
            controller.addMessage(getQueryValue("chatId"), new Message(getQueryValue("username"), getQueryValue("content"), System.currentTimeMillis()));
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
    }

    //QUERIES : chatId, size (= index of the last message the user has received)
    //Returns all messages as a json string
    @Get
    public String getMessages(){
        ArrayList<Message> messages = new ArrayList<>();
        try {
            messages.addAll(controller.getNewMessages(getQueryValue("chatId"), Integer.parseInt(getQueryValue("size"))));
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
        return new YaGson().toJson(messages, ArrayList.class);
    }
}
