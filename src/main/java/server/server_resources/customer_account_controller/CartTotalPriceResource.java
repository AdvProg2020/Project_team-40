package server.server_resources.customer_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.users.Customer;

public class CartTotalPriceResource extends ServerResource {
    @Get
    public double getCartTotalPrice(){
        Customer customer = (Customer) Customer.getUserByUsername(getQueryValue("username"));
        return customer.getTotalPriceOfCart();
    }
}
