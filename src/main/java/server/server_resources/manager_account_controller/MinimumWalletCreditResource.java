package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.model.users.Manager;

public class MinimumWalletCreditResource extends ServerResource {
    @Put
    public void setMinWalletCredit(String credit){
        Manager.setMinWalletBalance(Double.parseDouble(credit));
    }

    @Get
    public String getMinWalletCredit() {
        return new YaGson().toJson(Manager.getMinWalletBalance(), Double.class);
    }
}
