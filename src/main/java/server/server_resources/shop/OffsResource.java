package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.Off;

import java.util.ArrayList;

public class OffsResource extends ServerResource {
    @Get
    public String getAllOffs(){
        ArrayList<Off> offs = new ArrayList<>(Off.getAllOffs().values());
        return new YaGson().toJson(offs, new TypeToken<ArrayList<Off>>(){}.getType());
    }
}
