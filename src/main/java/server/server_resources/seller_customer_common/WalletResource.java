package server.server_resources.seller_customer_common;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.users.Customer;
import server.model.users.Seller;
import server.model.users.User;

public class WalletResource extends ServerResource {
    @Get
    public String getWalletCredit() {
        String response;
        User user = User.getUserByUsername(getQueryValue("username"));
        if(user instanceof Seller)
            response = "seller " + ((Seller) user).getCreditInWallet();
        else
            response = "customer " + ((Customer) user).getCreditInWallet();
        return new YaGson().toJson(response, String.class);
    }
}
