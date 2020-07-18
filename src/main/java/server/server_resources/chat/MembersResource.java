package server.server_resources.chat;

import com.gilecode.yagson.YaGson;
import exceptions.MenuException;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.controller.menus.ChatController;

import java.util.ArrayList;

public class MembersResource extends ServerResource{
    private ChatController controller = ChatController.getInstance();

    //QUERIES : id, username
    @Put
    public void addMember(){
        try {
            controller.addMember(getQueryValue("id"), getQueryValue("username"));
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
    }

    //QUERIES : id
    //Returns all members as a json string
    @Get
    public String getMembers(){
        ArrayList<String> members = new ArrayList<>();
        try {
            members.addAll(controller.getMembers(getQueryValue("id")));
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }
        return new YaGson().toJson(members, ArrayList.class);
    }
}
