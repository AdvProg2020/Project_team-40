package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.Off;
import server.model.users.Seller;
import server.model.users.User;

import java.util.ArrayList;

public class SellerOffsResource extends ServerResource {
    @Get
    public String getSellerOffIds(){
        Seller seller = (Seller) User.getUserByUsername(getQueryValue("username"));
        ArrayList<Off> offs = seller.getOffs();
        return new YaGson().toJson(offs, new TypeToken<ArrayList<Off>>(){}.getType());
    }
}
