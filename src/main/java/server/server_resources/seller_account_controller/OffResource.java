package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

import java.util.ArrayList;

public class OffResource extends ServerResource {

    @Put
    public void editOff(String offID) throws  AuthorizationException {
        try {
            SellerAccountController.getInstance().editOff(getQueryValue("username"), offID, getQueryValue("field"), getQueryValue("newField"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);

        }
    }

    @Post
    public void createOff(String ids) throws  AuthorizationException {
        String start = getQueryValue("startDate");
        String end = getQueryValue("endDate");
        double percentage = Double.parseDouble(getQueryValue("percentage"));
        YaGson mapper = new YaGson();
        ArrayList<String> productIDs = mapper.fromJson(ids, new TypeToken<ArrayList<String>>(){}.getType());

        try {
            SellerAccountController.getInstance().addOffToSeller(getQueryValue("username"), productIDs, start, end, percentage);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);

        }

    }
}
