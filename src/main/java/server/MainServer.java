package server;

import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.service.StatusService;
import server.server_resources.accounts.AccountResource;
import server.server_resources.accounts.BankAccountResource;
import server.server_resources.accounts.UserResource;
import server.server_resources.customer_account_controller.*;
import server.server_resources.manager_account_controller.*;
import server.server_resources.seller_account_controller.*;

public class MainServer extends Component {
    private final int DEFAULT_PORT = 8080;
    public static void main(String[] args) throws Exception {
        ServerAuthenticator.getInstance().initVerifier();
        new MainServer().start();
    }


    public MainServer() {
        setStatusService(new StatusService(){
            @Override
            public Representation getRepresentation(Status status, Request request, Response response) {
                if (status.getThrowable() != null)
                    return new StringRepresentation(status.getThrowable().getMessage());
                return null;
            }
        });
        getServers().add(Protocol.HTTP, DEFAULT_PORT);
        ServerAuthenticator authenticator = ServerAuthenticator.getInstance();
        getDefaultHost().attach("/accounts/manager_account_controller/manager/", ManagerResource.class);
//        getDefaultHost().attach("/accounts/manager_account_controller/support/", ManagerResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/user/", authenticator.getNewGuard(ManagerUserResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/users/", authenticator.getNewGuard(ManagerUsersResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/product/", authenticator.getNewGuard(ManagerProductResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/products/", authenticator.getNewGuard(ManagerProductsResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/all_categories/", authenticator.getNewGuard(ManagerAllCategoriesResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/category/", authenticator.getNewGuard(CategoryResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/request/", authenticator.getNewGuard(RequestResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/accept_request/",authenticator.getNewGuard( AcceptRequestResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/decline_request/", authenticator.getNewGuard(DeclineRequestResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/discount/", authenticator.getNewGuard(ManagerDiscountResource.class));
        getDefaultHost().attach("/accounts/manager_account_controller/all_discounts/", authenticator.getNewGuard(ManagerAllDiscountsResource.class));

        getDefaultHost().attach("/accounts/customer_account_controller/customer/", CustomerResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/all_discounts/", authenticator.getNewGuard(CustomerAllDiscountsResource.class));
        getDefaultHost().attach("/accounts/customer_account_controller/balance/", authenticator.getNewGuard(BalanceResource.class));
        getDefaultHost().attach("/accounts/customer_account_controller/cart/", authenticator.getNewGuard(CartResource.class));
        getDefaultHost().attach("/accounts/customer_account_controller/cart_total_price/", authenticator.getNewGuard(CartTotalPriceResource.class));
        getDefaultHost().attach("/accounts/customer_account_controller/discount/", authenticator.getNewGuard(CustomerDiscountResource.class));
        getDefaultHost().attach("/accounts/customer_account_controller/product/", authenticator.getNewGuard(CustomerProductResource.class));
        getDefaultHost().attach("/accounts/customer_account_controller/order/", authenticator.getNewGuard(OrderResource.class));
        getDefaultHost().attach("/accounts/customer_account_controller/orders/", authenticator.getNewGuard(OrdersResource.class));
        getDefaultHost().attach("/accounts/customer_account_controller/payment/", authenticator.getNewGuard(PaymentResource.class));
        getDefaultHost().attach("/accounts/customer_account_controller/all_customers/", authenticator.getNewGuard(AllCustomersResource.class));

        getDefaultHost().attach("/accounts/seller_account_controller/seller/", SellerResource.class);
        getDefaultHost().attach("/accounts/seller_account_controller/all_offs/", authenticator.getNewGuard(AllOffsResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/company_info/", authenticator.getNewGuard(CompanyInfoResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/off/", authenticator.getNewGuard(OffResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/product_count/increase/", authenticator.getNewGuard(ProductCountIncrementResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/product_count/decrease/", authenticator.getNewGuard(ProductCountDecrementResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/product/", authenticator.getNewGuard(SellerProductResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/products/", authenticator.getNewGuard(SellerProductsResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/sales_history/", authenticator.getNewGuard(SalesHistoryResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/all_categories/", authenticator.getNewGuard(SellerAllCategoriesResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/category_properties/", authenticator.getNewGuard(SellerCategoryPropertiesResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/balance/", authenticator.getNewGuard(SellerBalanceResource.class));
        getDefaultHost().attach("/accounts/seller_account_controller/manager_permission/", authenticator.getNewGuard(SellingPermissionResource.class));

        getDefaultHost().attach("/accounts/account/", AccountResource.class);
        getDefaultHost().attach("/accounts/user/", UserResource.class);
        getDefaultHost().attach("/accounts/bank_account", BankAccountResource.class);




    }
}
