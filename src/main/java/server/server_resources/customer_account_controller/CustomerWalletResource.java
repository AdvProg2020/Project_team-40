package server.server_resources.customer_account_controller;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.model.users.Customer;
import server.model.users.User;

public class CustomerWalletResource extends ServerResource {
    @Put
    public void increaseWalletCredit(String username){
        double amount = Double.parseDouble(getQueryValue("amount"));
        Customer customer = (Customer) User.getUserByUsername(getQueryValue("username"));
        customer.changeWalletCredit(amount);
    }
}
