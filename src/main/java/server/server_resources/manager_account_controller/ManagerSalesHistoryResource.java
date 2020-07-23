package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import exceptions.AccountsException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.log.Log;

import java.util.ArrayList;

public class ManagerSalesHistoryResource extends ServerResource{
    @Get
    public String getSales(){
        ArrayList<Log> logs = ManagerAccountController.getInstance().getLogs();
        return new YaGson().toJson(logs, ArrayList.class);
    }

    @Post
    public void setDelivered(){
        try {
            ManagerAccountController.getInstance().setDelivered(getQueryValue("logId"), true);
        } catch(AccountsException e) {
            e.printStackTrace();
        }
    }

    @Delete
    public void setNotDelivered(){
        try {
            ManagerAccountController.getInstance().setDelivered(getQueryValue("logId"), false);
        } catch(AccountsException e) {
            e.printStackTrace();
        }
    }

}
