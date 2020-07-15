package server.server_resources.customer_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.users.Customer;

import java.util.ArrayList;

public class AllCustomersResource extends ServerResource {
    @Get
    public ArrayList<String> getAllCustomers(){
        return Customer.getAllCustomers();
    }
}
