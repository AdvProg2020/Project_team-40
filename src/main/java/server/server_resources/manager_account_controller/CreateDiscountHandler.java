package server.server_resources.manager_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;

public class CreateDiscountHandler extends ServerResource {
    @Get
    public String createDiscount(){
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            return "Authentication failed.";

        String startDate = getQueryValue("startDate");
        String endDate = getQueryValue("endDate");
        int percentage = Integer.parseInt(getQueryValue("percentage"));
        double maxPrice = Double.parseDouble(getQueryValue("maxDiscount"));
        int countPerUser = Integer.parseInt(getQueryValue("countPerUser"));
        //  TODO: Get customers
//        ManagerAccountController.getInstance().createDiscount();
        return null;
    }

}