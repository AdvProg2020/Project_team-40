package server.server_resources.manager_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class CreateDiscountHandler extends ServerResource {
    @Get
    public String createDiscount(){
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