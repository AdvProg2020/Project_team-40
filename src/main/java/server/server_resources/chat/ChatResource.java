package server.server_resources.chat;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;

public class ChatResource extends ServerResource{
    @Post
    public void createChat(String name, ArrayList<String> usernames){

    }
}
