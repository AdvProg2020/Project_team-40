package server.server_resources.chat;

import com.gilecode.yagson.YaGson;
import exceptions.MenuException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.ChatController;
import server.model.chat.Chat;

public class ChatIdResource extends ServerResource{
    @Get
    public String getChat(){
        try {
            return new YaGson().toJson(ChatController.getInstance().getChat(getQueryValue("id")), Chat.class);
        } catch(MenuException e) {
            e.printStackTrace();
        }
        return "";
    }
}
