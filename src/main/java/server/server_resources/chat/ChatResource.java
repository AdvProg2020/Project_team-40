package server.server_resources.chat;

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

    @Post
    public void createChat(String name, ArrayList<String> usernames){
        Chat chat = controller.createChat(name);
        if(usernames != null){
            for(String username : usernames) {
                try {
                    controller.addMember(chat.getId(), username);
                }catch(MenuException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Put
    public void addUser(String id, String username){
        Chat chat = null;
        try {
            chat = controller.getChat(id);
            controller.addMember(chat.getId(), username);
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
    }

    @Get
    public Chat getChat(String id){
        Chat chat = null;
        try {
            chat = controller.getChat(id);
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
        return chat;
    }
}
