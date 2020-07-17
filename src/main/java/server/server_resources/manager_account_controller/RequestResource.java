package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.requests.Request;

public class RequestResource extends ServerResource {
    @Get
    public String getRequestObject()  {

        try {
            Request request =  ManagerAccountController.getInstance().getRequest(getQueryValue("requestID"));
            return new YaGson().toJson(request, Request.class);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);

        }

    }
}
