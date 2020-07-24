package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.log.Log;

public class LogResource extends ServerResource {
    @Get
    public String getLog(){
        Log log = Log.getLogById(getQueryValue("logID"));
        return new YaGson().toJson(log, Log.class);
    }
}
