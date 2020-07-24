package server.server_resources.seller_account_controller;

import exceptions.WeakPasswordException;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.ServerAuthenticator;
import server.controller.accounts.SellerAccountController;
import server.controller.password.PasswordValidator;

public class SellerResource extends ServerResource {
    @Post
    public void createSeller(){
        SellerAccountController manager = SellerAccountController.getInstance();
        String username = getQueryValue("username");
        String password = getQueryValue("password");
//        PasswordValidator.getInstance().setEnabled(false);
        try {
            PasswordValidator.getInstance().validatePassword(password);
        } catch (WeakPasswordException e) {
            throw new ResourceException(403, e);
        }
        String firstName = getQueryValue("firstName");
        String lastName = getQueryValue("lastName");
        String email = getQueryValue("email");
        String phone = getQueryValue("phoneNumber");
        String company = getQueryValue("companyInfo");
        manager.createSellerAccount(username, password, firstName, lastName, email, phone, 0, company);
        ServerAuthenticator.getInstance().addToSellerVerifier(username, password);
    }
}
