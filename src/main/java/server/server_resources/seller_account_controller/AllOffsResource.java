package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;
import server.model.Off;

import java.util.HashMap;

public class AllOffsResource extends ServerResource {

    @Get
    public String getAllOffs() throws AuthorizationException {
        HashMap<String, Off> offs = SellerAccountController.getInstance().getAllOffs(getQueryValue("username"));
        return new YaGson().toJson(offs, new TypeToken<HashMap<String, Off>>(){}.getType());
    }
}
