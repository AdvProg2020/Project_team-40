package server.server_resources.chat;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.ChatController;

import java.util.ArrayList;

public class SupportsResource extends ServerResource{
    private ChatController controller = ChatController.getInstance();

    @Get
    public ArrayList<String> getSupports(){
        return controller.getSupports();
    }
}
