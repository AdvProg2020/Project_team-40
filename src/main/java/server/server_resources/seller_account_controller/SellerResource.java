package server.server_resources.seller_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

public class SellerResource extends ServerResource {
    @Get
    public String createSeller(){
        SellerAccountController manager = SellerAccountController.getInstance();
        String username = getQueryValue("username");
        String password = getQueryValue("password");
        String firstName = getQueryValue("firstName");
        String lastName = getQueryValue("lastName");
        String email = getQueryValue("email");
        String phone = getQueryValue("phoneNumber");
        double credit = Double.parseDouble(getQueryValue("credit"));
        String company = getQueryValue("companyInfo");
        manager.createSellerAccount(username, password, firstName, lastName, email, phone, credit, company);
        return "Successful";
    }
}
