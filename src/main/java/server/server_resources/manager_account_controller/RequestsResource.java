package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.requests.Request;
import server.model.users.Manager;

import java.util.ArrayList;

public class RequestsResource extends ServerResource {
    @Get
    public String getAllRequests(){
        ArrayList<Request> requests = Manager.getRequests();
        return new YaGson().toJson(requests, new TypeToken<ArrayList<Request>>(){}.getType());
    }
}
