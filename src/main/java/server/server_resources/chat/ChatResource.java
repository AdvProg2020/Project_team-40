package server.server_resources.chat;

import com.gilecode.yagson.YaGson;
import exceptions.MenuException;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.controller.menus.ChatController;
import server.model.chat.Chat;

import java.util.ArrayList;

public class ChatResource extends ServerResource{
    private ChatController controller = ChatController.getInstance();

    //The creator (username) should be added to the chat and if there is a support involved, them as well
    //QUERIES : name, username, hasSupport, support
    @Post
    public void createChat(){
        Chat chat = controller.createChat(getQueryValue("name"));
        try {
            controller.addMember(chat.getId(), getQueryValue("username"));
            if(getQueryValue("hasSupport").equals("true")){
                controller.addMember(chat.getId(), getQueryValue("support"));
            }
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
    }

    //QUERIES : id, username
    @Put
    public void addUser(){
        Chat chat = null;
        try {
            chat = controller.getChat(getQueryValue("id"));
            controller.addMember(chat.getId(), getQueryValue("username"));
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
    }

    //Returns chats as a json string
    @Get
    public String getChats(){
        return new YaGson().toJson(controller.getAllChats(), ArrayList.class);
    }
}
