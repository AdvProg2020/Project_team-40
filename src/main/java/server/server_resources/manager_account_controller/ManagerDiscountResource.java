package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.DiscountCode;

public class ManagerDiscountResource extends ServerResource {
    @Get
    public DiscountCode getDiscount(){
        try {
            return ManagerAccountController.getInstance().getDiscount(getQueryValue("code"));
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Post
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

    @Delete
    public String removeDiscount(){
        try {
            ManagerAccountController.getInstance().removeDiscount(getQueryValue("code"));
        } catch (AccountsException e) {
            return e.getMessage();
        }
        return "Successful";
    }

}