package server.server_resources.customer_account_controller;

import exceptions.WeakPasswordException;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.ServerAuthenticator;
import server.controller.accounts.CustomerAccountController;
import server.controller.password.PasswordValidator;

public class CustomerResource extends ServerResource {
    @Post
    public void createCustomer(){
        CustomerAccountController manager = CustomerAccountController.getInstance();
        String username = getQueryValue("username");
        String password = getQueryValue("password");
        PasswordValidator.getInstance().setEnabled(false);
        try {
            PasswordValidator.getInstance().validatePassword(password);
        } catch (WeakPasswordException e) {
            throw new ResourceException(403, e);
        }
        String firstName = getQueryValue("firstName");
        String lastName = getQueryValue("lastName");
        String email = getQueryValue("email");
        String phone = getQueryValue("phoneNumber");
        double credit = Double.parseDouble(getQueryValue("credit"));
        manager.createCustomerAccount(username, password, firstName, lastName, email, phone, credit);
        ServerAuthenticator.getInstance().addToCustomerVerifier(username, password);
    }

}
