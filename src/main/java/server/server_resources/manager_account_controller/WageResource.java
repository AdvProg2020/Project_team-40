package server.server_resources.manager_account_controller;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.model.users.Manager;

public class WageResource extends ServerResource {
    @Put
    public void setWage(String wage){
        Manager.setWage(Double.parseDouble(wage));
    }
}
