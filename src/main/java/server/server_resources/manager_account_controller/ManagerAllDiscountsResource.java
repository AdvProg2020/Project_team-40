package server.server_resources.manager_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.DiscountCode;

import java.util.ArrayList;

public class ManagerAllDiscountsResource extends ServerResource {
    @Get
    public ArrayList<DiscountCode> getAllDiscounts(){
        return ManagerAccountController.getInstance().getAllDiscountCodes();
    }
}
