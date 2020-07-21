package server.server_resources.chat;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.ChatController;
import server.model.chat.Chat;

import java.util.ArrayList;

public class SupportChatResource extends ServerResource{
    //QUERIES : username, support
    @Get
    public String getChat() {
        Chat chat = null;
        ArrayList<Chat> allChats = ChatController.getInstance().getAllChats();
        for(Chat chat1 : allChats) {
            ArrayList<String> members = chat.getMembers();
            if(members.size() == 2 && members.contains(getQueryValue("username")) && members.contains(getQueryValue("support"))){
                chat = chat1;
            }
        }
        return new YaGson().toJson(chat, Chat.class);
    }
}
