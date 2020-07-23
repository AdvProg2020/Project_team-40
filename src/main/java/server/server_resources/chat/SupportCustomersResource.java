package server.server_resources.chat;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.ChatController;
import server.model.chat.Chat;

import java.util.ArrayList;

public class SupportCustomersResource extends ServerResource{

    //QUERIES : support
    //Returns all of a support's chat history
    @Get
    public String getAllChats(){
        ArrayList<Chat> chats = new ArrayList<>();
        ArrayList<Chat> all = ChatController.getInstance().getAllChats();
        for(Chat chat : all) {
            if(chat.getName().equals("Support : " + getQueryValue("support")) && chat.isSupport())
                chats.add(chat);
        }
        return new YaGson().toJson(chats, ArrayList.class);
    }
}
