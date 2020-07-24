package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
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
    public String enterDiscount(String code) throws  AuthorizationException {
        try {
            String username = getQueryValue("username");
            CustomerAccountController.getInstance().checkDiscountCode(code, username);
            DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
            Double priceAfterDiscount =  discountCode.
                    calculatePriceAfterDiscount(((Customer) User.getUserByUsername(username)).getTotalPriceOfCart());
            return new YaGson().toJson(priceAfterDiscount, Double.class);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

}
