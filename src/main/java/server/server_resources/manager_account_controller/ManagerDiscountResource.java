package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AccountsException;
import org.restlet.resource.*;
import server.controller.accounts.ManagerAccountController;
import server.model.DiscountCode;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerDiscountResource extends ServerResource {
    @Get
    public String getDiscount() throws AccountsException {
        DiscountCode discountCode =  ManagerAccountController.getInstance().getDiscount(getQueryValue("code"));
        return new YaGson().toJson(discountCode, DiscountCode.class);
    }

    @Post
    public void createDiscount(String customersJson)  {
        ArrayList<String> customers = new YaGson().fromJson(customersJson, new TypeToken<ArrayList<String>>(){}.getType());
        String startDate = getQueryValue("startDate");
        String endDate = getQueryValue("endDate");
        int percentage = Integer.parseInt(getQueryValue("percentage"));
        double maxPrice = Double.parseDouble(getQueryValue("maxDiscount"));
        int countPerUser = Integer.parseInt(getQueryValue("countPerUser"));
        try {
            ManagerAccountController.getInstance().createDiscount(startDate, endDate, percentage, maxPrice, countPerUser, customers);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Put
    public void editDiscount(String toEditJson) {
        HashMap<String, String> toEdit = new YaGson().fromJson(toEditJson, new TypeToken<HashMap<String, String>>(){}.getType());
        try {
            ManagerAccountController.getInstance().editDiscount(getQueryValue("code"), toEdit);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Delete
    public void removeDiscount()  {

        try {
            ManagerAccountController.getInstance().removeDiscount(getQueryValue("code"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

}