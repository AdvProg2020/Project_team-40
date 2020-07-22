package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.users.Manager;
import server.model.users.User;

import java.util.HashMap;

public class StoreBankAccountResource extends ServerResource {
    @Get
    public String getBankAccountInfo() {
        HashMap<String, String> accountInfo = new HashMap<>();
        Manager manager = null;
        for(String username : User.getAllUsernames()) {
            User user =  User.getUserByUsername(username);
            if(user instanceof Manager) {
                manager = (Manager) user;
                break;
            }
        }
        accountInfo.put("bank id", String.valueOf(manager.getBankAccount()));
        accountInfo.put("bank username", manager.getBankUsername());
        accountInfo.put("wage", String.valueOf(Manager.getWage()));
        accountInfo.put("min balance", String.valueOf(Manager.getMinWalletBalance()));
        return new YaGson().toJson(accountInfo, new TypeToken<HashMap<String, String>>(){}.getType());
    }
}
