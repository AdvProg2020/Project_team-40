package server.server_resources.customer_account_controller;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.DiscountCode;
import server.model.users.Customer;
import server.model.users.User;

public class CustomerDiscountResource extends ServerResource {
    @Put
    public double enterDiscount(String code) throws  AuthorizationException {
        try {
            String username = getQueryValue("username");
            CustomerAccountController.getInstance().checkDiscountCode(code, username);
            DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
            return discountCode.
                    calculatePriceAfterDiscount(((Customer) User.getUserByUsername(username)).getTotalPriceOfCart());
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

}
