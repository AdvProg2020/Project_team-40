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
            if(chat1.isSupport() && chat1.getMembers().contains(getQueryValue("username")) && chat1.getMembers().contains(getQueryValue("support")))
                chat = chat1;
        }

        if(chat == null){
            chat = ChatController.getInstance().createChat("Support : " + getQueryValue("support"));
            chat.getMembers().add(getQueryValue("username"));
            chat.getMembers().add(getQueryValue("support"));
            chat.setIsSupport(true);
        }

        return new YaGson().toJson(chat, Chat.class);
    }
}
