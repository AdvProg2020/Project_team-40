package server.server_resources.seller_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.users.Seller;

public class SellingPermissionResource extends ServerResource {
    @Get
    public boolean getSellingPermission(){
        return ((Seller)Seller.getUserByUsername(getQueryValue("username"))).isManagerPermission();
    }
}
