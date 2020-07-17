package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

public class CompanyInfoResource extends ServerResource {
    @Get
    public String getCompanyInfo() throws AuthorizationException {
        String info = SellerAccountController.getInstance().getCompanyInfo(getQueryValue("username"));
        return new YaGson().toJson(info, String.class);
    }
}
