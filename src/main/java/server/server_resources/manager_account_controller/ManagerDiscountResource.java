package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.*;
import server.controller.accounts.ManagerAccountController;
import server.model.DiscountCode;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerDiscountResource extends ServerResource {
    @Get
    public DiscountCode getDiscount() throws AccountsException {
       return ManagerAccountController.getInstance().getDiscount(getQueryValue("code"));
    }

    @Post
    public void createDiscount(ArrayList<String> customers) throws AccountsException {
        String startDate = getQueryValue("startDate");
        String endDate = getQueryValue("endDate");
        int percentage = Integer.parseInt(getQueryValue("percentage"));
        double maxPrice = Double.parseDouble(getQueryValue("maxDiscount"));
        int countPerUser = Integer.parseInt(getQueryValue("countPerUser"));
        ManagerAccountController.getInstance().createDiscount(startDate, endDate, percentage, maxPrice, countPerUser, customers);
    }

    @Put
    public void editDiscount(HashMap<String, String> toEdit) throws AccountsException {
        ManagerAccountController.getInstance().editDiscount(getQueryValue("code"), toEdit);
    }

    @Delete
    public void removeDiscount() throws AccountsException {

        ManagerAccountController.getInstance().removeDiscount(getQueryValue("code"));
    }

}