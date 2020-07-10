package server;

import org.restlet.Component;
import org.restlet.data.Protocol;
import server.server_resources.manager_account_controller.*;

public class MainServer extends Component {
    private final int DEFAULT_PORT = 8080;
    public static void main(String[] args) throws Exception {
        new MainServer().start();
    }


    public MainServer() {
        getServers().add(Protocol.HTTP, DEFAULT_PORT);
        getDefaultHost().attach("/accounts/manager_account_controller/manager/", ManagerResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/user/", UserResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/product/", ProductResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/all_categories/", AllCategoriesResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/category/", CategoryResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/request/", RequestResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/accept_request/", AcceptRequestResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/decline_request/", DeclineRequestResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/discount/", DiscountResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/all_discounts/", AllDiscountsResource.class);



    }
}
