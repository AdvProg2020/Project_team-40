package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.users.Customer;
import server.model.users.User;

public class CustomerPurchaseStatusResource extends ServerResource {
    @Get
    public String getHasBought(){
        Customer customer = (Customer) User.getUserByUsername(getQueryValue("username"));
        String productID = getQueryValue("productID");
        return new YaGson().toJson(customer.hasBought(productID), boolean.class);
    }
}
