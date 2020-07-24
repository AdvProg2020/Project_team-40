package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.Product;
import server.model.log.Log;
import server.model.users.Customer;
import server.model.users.Manager;
import server.model.users.Seller;
import server.model.users.User;

import java.util.HashMap;

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
            Customer customer = (Customer) User.getUserByUsername(username);
            HashMap<String, Integer> cart = customer.getCart();
            handleTransactions(cart);
            Log log =  CustomerAccountController.getInstance().makePayment(username, address,code, priceAfterDiscount, priceWithoutDiscount, true);
            return mapper.toJson(log, Log.class);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    private void handleTransactions(HashMap<String, Integer> cart) {
        for (String productID : cart.keySet()) {
            Product product = Product.getProductById(productID);
            int totalPrice = (int) (product.getPrice() * cart.get(productID));
            Seller seller = product.getSeller();
            double wage = Manager.getWage();
            seller.changeWalletCredit(totalPrice * (1 - 0.01 * wage));

        }
    }
}
