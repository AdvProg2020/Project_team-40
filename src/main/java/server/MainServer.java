package server;

import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Resource;
import org.restlet.service.StatusService;
import server.server_resources.customer_account_controller.*;
import server.server_resources.manager_account_controller.*;

public class MainServer extends Component {
    private final int DEFAULT_PORT = 8080;
    public static void main(String[] args) throws Exception {
        new MainServer().start();
    }


    public MainServer() {
        setStatusService(new StatusService(){
            @Override
            public Representation getRepresentation(Status status, Request request, Response response) {
                return new StringRepresentation(status.getThrowable().getMessage());
            }

            @Override
            public Status getStatus(Throwable throwable, Resource resource) {
                return Status.CLIENT_ERROR_FORBIDDEN;
            }
        });
        getServers().add(Protocol.HTTP, DEFAULT_PORT);
        getDefaultHost().attach("/accounts/manager_account_controller/manager/", ManagerResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/user/", UserResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/product/", ManagerProductResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/all_categories/", AllCategoriesResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/category/", CategoryResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/request/", RequestResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/accept_request/", AcceptRequestResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/decline_request/", DeclineRequestResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/discount/", ManagerDiscountResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/all_discounts/", ManagerAllDiscountsResource.class);

        getDefaultHost().attach("/accounts/customer_account_controller/all_discounts/", CustomerAllDiscountsResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/balance/", BalanceResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/cart/", CartResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/cart_total_price/", CartTotalPriceResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/discount/", CustomerDiscountResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/product/", CustomerProductResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/customer/", CustomerResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/order/", OrderResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/orders/", OrdersResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/payment/", PaymentResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/price_after_discount/", PriceAfterDiscountResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/price_without_discount/", PriceWithoutDiscountResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/receiver_info/", ReceiverInfoResource.class);



    }
}
