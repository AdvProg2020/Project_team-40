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
        getDefaultHost().attach("/accounts/manager_account_controller/create_manager/", CreateManagerHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/delete_user/", DeleteUserHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/remove_product/", RemoveProductHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/get_all_categories/", GetAllCategoriesHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/create_category/", CreateCategoryHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/remove_category/", RemoveCategoryHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/accept_request/", AcceptRequestHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/decline_request/", DeclineRequestHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/create_discount/", CreateDiscountHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/get_all_discounts/", GetAllDiscountsHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/get_discount/", GetDiscountHandler.class);
        getDefaultHost().attach("/accounts/manager_account_controller/remove_discount/", RemoveDiscountHandler.class);



    }
}
