package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.log.Log;

public class PaymentResource extends ServerResource {
    @Put
    public String purchase() throws  AuthorizationException {
        YaGson mapper = new YaGson();
        try {
            String username = getQueryValue("username");
            String address = getQueryValue("address");
            String code = getQueryValue("code");
            double priceAfterDiscount = Double.parseDouble(getQueryValue("priceAfterDiscount"));
            double priceWithoutDiscount = Double.parseDouble(getQueryValue("priceWithoutDiscount"));
            Log log =  CustomerAccountController.getInstance().makePayment(username, address,code, priceAfterDiscount, priceWithoutDiscount);
            return mapper.toJson(log, Log.class);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }
}
