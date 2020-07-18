package server.server_resources.chat;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.ChatController;

import java.util.ArrayList;

public class SupportsResource extends ServerResource{
    private ChatController controller = ChatController.getInstance();

    //All supports as a json object
    @Get
    public String getSupports(){
        return new YaGson().toJson(controller.getSupports(), ArrayList.class);
    }
}
