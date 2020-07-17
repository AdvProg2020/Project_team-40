package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.users.Seller;

public class SellingPermissionResource extends ServerResource {
    @Get
    public String getSellingPermission(){
        boolean permission = ((Seller)Seller.getUserByUsername(getQueryValue("username"))).isManagerPermission();
        return new YaGson().toJson(permission, boolean.class);
    }
}
