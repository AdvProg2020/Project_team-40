package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.users.Customer;

public class CartTotalPriceResource extends ServerResource {
    @Get
    public String getCartTotalPrice(){
        Customer customer = (Customer) Customer.getUserByUsername(getQueryValue("username"));
        Double total =  customer.getTotalPriceOfCart();
        return new YaGson().toJson(total, Double.class);
    }
}
