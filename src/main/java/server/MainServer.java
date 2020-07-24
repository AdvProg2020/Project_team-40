package server;

import exceptions.DataException;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.service.StatusService;
import server.controller.menus.AuctionController;
import server.model.Loader;
import server.server_resources.accounts.AccountResource;
import server.server_resources.accounts.UserResource;
import server.server_resources.bank.*;
import server.server_resources.chat.*;
import server.server_resources.customer_account_controller.*;
import server.server_resources.manager_account_controller.*;
import server.server_resources.seller_account_controller.*;
import server.server_resources.seller_customer_common.AuctionResource;
import server.server_resources.seller_customer_common.AuctionsProductResource;
import server.server_resources.seller_customer_common.AuctionsResource;
import server.server_resources.seller_customer_common.WalletResource;
import server.server_resources.shop.*;

import java.io.File;

public class MainServer extends Component {
    private final int DEFAULT_PORT = 8080;
    private static final String PATH = "src/main/resources";
    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.err.println("Saving data ...");
                try {
                    Loader.getLoader().saveData();
                } catch (DataException e) {
                    e.printStackTrace();
                }
            }
        });
        initializeLoading();
//        addTempVariables();
        ServerAuthenticator.getInstance().initVerifier();
        AuctionController.checkDeadlines();
        new MainServer().start();
    }
//        private static void addTempVariables() {
//        HashMap<String, PropertyType> properties = new HashMap<>();
//        properties.put("size", PropertyType.STRING);
//        properties.put("wifi", PropertyType.STRING);
//        properties.put("number of usb jacks", PropertyType.RANGE);
//        properties.put("storage space", PropertyType.RANGE);
//
//        HashMap<String, PropertyType> subProperties = new HashMap<>();
//        subProperties.put("display", PropertyType.STRING);
//        subProperties.put("OS", PropertyType.STRING);
//        subProperties.put("ram", PropertyType.RANGE);
//        subProperties.put("camera quality", PropertyType.RANGE);
//
//        HashMap<String, PropertyType> subProperties2 = new HashMap<>();
//        subProperties2.put("ram", PropertyType.STRING);
//        subProperties2.put("cpu", PropertyType.STRING);
//        subProperties2.put("graphics", PropertyType.RANGE);
//        subProperties2.put("ssd", PropertyType.RANGE);
//
//        try {
//            ManagerAccountController.getInstance().createCategory("electronics", null, properties);
//            ManagerAccountController.getInstance().createCategory("mobile", "electronics", subProperties);
//            ManagerAccountController.getInstance().createCategory("pc", "electronics", subProperties2);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        Seller seller = new Seller("a", "a", "a", "a", "a@.a", "0", 1000, "a");
//        seller.setManagerPermission(true);
//        Product product1 = new Product("b", "a", 10, 10, "a", "mobile");
//        Product product2 = new Product("c", "a", 10, 10, "a", "mobile");
//        Product product3 = new Product("d", "a", 10, 10, "a", "mobile");
//        Product product4 = new Product("e", "a", 1000, 10, "a", "mobile");
//        Product product5 = new Product("f", "a", 1000, 10, "a", "pc");
//        Product product6 = new Product("g", "a", 1000, 10, "a", "pc");
//        Product product7 = new Product("h", "a", 1000, 10, "a", "pc");
//        Product product8 = new Product("i", "a", 1000, 10, "a", "pc");
//        product1.setExplanation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum commodo lacinia augue eu rhoncus. Sed non erat in felis rutrum faucibus. Integer laoreet bibendum purus, id interdum augue faucibus volutpat. Suspendisse efficitur tincidunt ipsum id ultrices. Praesent sem enim, ultricies sit amet tempor eu, aliquet at ipsum. Donec dapibus elit vitae tortor vulputate faucibus. Mauris purus urna, pellentesque maximus augue quis, blandit vestibulum urna.\n" +
//                "\n" +
//                "Mauris fermentum maximus ligula, sed vehicula lacus laoreet quis. Aliquam erat volutpat. Donec a convallis diam, nec condimentum felis. Cras orci ante, porta vitae tortor in, dictum egestas nibh. Etiam consequat dignissim iaculis. Nulla in suscipit augue. Proin imperdiet ligula sit amet ipsum malesuada, sit amet finibus enim varius. Donec semper a enim molestie vehicula. Aliquam erat volutpat.\n" +
//                "\n" +
//                "Mauris nunc eros, ultrices sed convallis fermentum, iaculis sed lacus. Aliquam placerat, sapien in tempus placerat, erat leo venenatis lorem, vitae feugiat leo orci sed nisl. Aenean eu euismod tortor, ac feugiat arcu. Suspendisse sed odio id nunc dictum tempus lobortis non dolor. Maecenas in orci facilisis, commodo ligula non, aliquet justo. Nulla lacus purus, iaculis sed placerat eu, sodales feugiat eros. Curabitur volutpat volutpat felis sed viverra. Ut faucibus tortor ac urna fermentum, eget pretium sem eleifend. Vestibulum nec suscipit ipsum. Pellentesque dictum eleifend mauris, et tincidunt est commodo nec.\n" +
//                "\n" +
//                "Quisque odio libero, tempor eget luctus vitae, porta pulvinar sapien. Vivamus vel aliquet tellus, quis tincidunt turpis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer venenatis arcu imperdiet nisl placerat tincidunt. Donec in purus dictum, tristique ipsum a, dignissim est. Praesent suscipit felis quis nunc viverra, sed pellentesque massa pulvinar. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In quis dui sit amet ex aliquam pellentesque vel at libero. Praesent sed magna ac arcu fermentum fermentum. Vestibulum vestibulum nec odio id sollicitudin. Ut enim ipsum, porttitor in ex varius, varius dignissim ipsum.\n" +
//                "\n" +
//                "Curabitur ligula nisi, facilisis eget enim id, porttitor facilisis metus. Praesent eget maximus neque. Morbi congue orci non pulvinar feugiat. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Cras vehicula orci nec mauris posuere, quis scelerisque massa congue. Suspendisse hendrerit elit eu rutrum dignissim. Aenean elementum tellus vitae massa lobortis efficitur. Proin vel mauris eget mauris luctus laoreet sed vel est. Sed arcu odio, pellentesque sit amet lacinia et, elementum sit amet lectus. Proin vel facilisis erat, non pellentesque turpis. Phasellus ultricies tincidunt ullamcorper. Pellentesque dignissim hendrerit orci non tempus. Vivamus sollicitudin nisi in tortor auctor, eget pretium sem dapibus. Vestibulum egestas tellus id condimentum facilisis. Quisque ac luctus nulla.");
//        seller.addProduct(product1);
//        seller.addProduct(product2);
//        seller.addProduct(product3);
//        seller.addProduct(product4);
//        seller.addProduct(product5);
//        seller.addProduct(product6);
//        seller.addProduct(product7);
//        seller.addProduct(product8);
//        Product.addProduct(product1);
//        Product.addProduct(product2);
//        Product.addProduct(product3);
//        Product.addProduct(product4);
//        Product.addProduct(product5);
//        Product.addProduct(product6);
//        Product.addProduct(product7);
//        Product.addProduct(product8);
//
//    }

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
        getDefaultHost().attach("/accounts/manager_account_controller/support/", SupportResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/user/", authenticator.getNewGuard(ManagerUserResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/users/", authenticator.getNewGuard(ManagerUsersResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/product/", authenticator.getNewGuard(ManagerProductResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/products/", authenticator.getNewGuard(ManagerProductsResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/all_categories/", authenticator.getNewGuard(ManagerAllCategoriesResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/category/", CategoryResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/request/", authenticator.getNewGuard(RequestResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/requests/", authenticator.getNewGuard(RequestsResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/accept_request/",authenticator.getNewGuard( AcceptRequestResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/decline_request/", authenticator.getNewGuard(DeclineRequestResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/discount/", ManagerDiscountResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/all_discounts/", authenticator.getNewGuard(ManagerAllDiscountsResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/store_bank_account/", StoreBankAccountResource.class);
        getDefaultHost().attach("/accounts/manager_account_controller/wage/", authenticator.getNewGuard(WageResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/manager_account_controller/min_wallet_credit/", MinimumWalletCreditResource.class);

        getDefaultHost().attach("/accounts/customer_account_controller/customer/", CustomerResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/all_discounts/", authenticator.getNewGuard(CustomerAllDiscountsResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/balance/", authenticator.getNewGuard(BalanceResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/cart/", authenticator.getNewGuard(CartResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/cart_total_price/", authenticator.getNewGuard(CartTotalPriceResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/discount/", authenticator.getNewGuard(CustomerDiscountResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/product/", authenticator.getNewGuard(CustomerProductResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/product/purchase_status/", CustomerPurchaseStatusResource.class);
        getDefaultHost().attach("/accounts/customer_account_controller/order/", authenticator.getNewGuard(OrderResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/orders/", authenticator.getNewGuard(OrdersResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/payment/", authenticator.getNewGuard(PaymentResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/all_customers/", authenticator.getNewGuard(AllCustomersResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/customer_account_controller/bank_account/", authenticator.getNewGuard(StoreBankAccountResource.class, RoleAccessibility.MANAGER));
        getDefaultHost().attach("/accounts/customer_account_controller/wallet/", authenticator.getNewGuard(CustomerWalletResource.class, RoleAccessibility.CUSTOMER));
        getDefaultHost().attach("/accounts/customer_account_controller/pay_by_bank/", authenticator.getNewGuard(PayByBankResource.class, RoleAccessibility.CUSTOMER));

        getDefaultHost().attach("/accounts/seller_account_controller/seller/", SellerResource.class);
        getDefaultHost().attach("/accounts/seller_account_controller/seller/offs", SellerOffsResource.class);
        getDefaultHost().attach("/accounts/seller_account_controller/all_offs/", authenticator.getNewGuard(AllOffsResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/company_info/", authenticator.getNewGuard(CompanyInfoResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/off/", authenticator.getNewGuard(OffResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/product_count/increase/", authenticator.getNewGuard(ProductCountIncrementResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/product_count/decrease/", authenticator.getNewGuard(ProductCountDecrementResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/product/", authenticator.getNewGuard(SellerProductResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/products/", authenticator.getNewGuard(SellerProductsResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/sales_history/", authenticator.getNewGuard(SalesHistoryResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/all_categories/", authenticator.getNewGuard(SellerAllCategoriesResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/category_properties/", authenticator.getNewGuard(SellerCategoryPropertiesResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/balance/", authenticator.getNewGuard(SellerBalanceResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/manager_permission/", authenticator.getNewGuard(SellingPermissionResource.class, RoleAccessibility.SELLER));
        getDefaultHost().attach("/accounts/seller_account_controller/add_auction/", AddAuctionResource.class);
        getDefaultHost().attach("/accounts/seller_account_controller/wallet/", authenticator.getNewGuard(SellerWalletResource.class, RoleAccessibility.SELLER));

        getDefaultHost().attach("/accounts/seller_customer_common/auction/", AuctionResource.class);
        getDefaultHost().attach("/accounts/seller_customer_common/wallet/", WalletResource.class);
        getDefaultHost().attach("/accounts/seller_customer_common/auctions/", AuctionsResource.class);
        getDefaultHost().attach("/accounts/seller_customer_common/auctions_product/", AuctionsProductResource.class);

        getDefaultHost().attach("/bank/register/", BankRegisterResource.class);
        getDefaultHost().attach("/bank/login/", BankLoginResource.class);
        getDefaultHost().attach("/bank/balance/", BankBalance.class);
        getDefaultHost().attach("/bank/receipts/", ReceiptsResources.class);
        getDefaultHost().attach("/bank/transactions/", TransactionsResources.class);
        getDefaultHost().attach("/bank/create_receipt_resources/", CreateReceiptResources.class);

        getDefaultHost().attach("/accounts/account/", AccountResource.class);
        getDefaultHost().attach("/accounts/user/", UserResource.class);

        getDefaultHost().attach("/shop/all_categories/", AllCategoriesResource.class);
        getDefaultHost().attach("/shop/all_sub_categories/", AllSubCategoriesResource.class);
        getDefaultHost().attach("/shop/comment/", CommentResource.class);
        getDefaultHost().attach("/shop/all_comments/", CommentsResource.class);
        getDefaultHost().attach("/shop/product/", ProductResource.class);
        getDefaultHost().attach("/shop/product/seller", ProductSellerResource.class);
        getDefaultHost().attach("/shop/product/attributes/", ProductAttributesResource.class);
        getDefaultHost().attach("/shop/product/sellers/", ProductSellersResource.class);
        getDefaultHost().attach("/shop/all_products/", ProductsResource.class);
        getDefaultHost().attach("/shop/cart/", ShoppingCartResource.class);
        getDefaultHost().attach("/shop/all_offs/", OffsResource.class);
        getDefaultHost().attach("/shop/all_offs/products/", ProductsInOffResource.class);
        getDefaultHost().attach("/shop/log/", LogResource.class);

        getDefaultHost().attach("/chat/chat/", ChatResource.class);
        getDefaultHost().attach("/chat/message/", MessageResource.class);
        getDefaultHost().attach("/chat/members/", MembersResource.class);
        getDefaultHost().attach("/chat/supports/", SupportsResource.class);
        getDefaultHost().attach("/chat/support_chat/", SupportChatResource.class);
        getDefaultHost().attach("/chat/support_customers/", SupportCustomersResource.class);

        getDefaultHost().attach("/shop/file/", FileResource.class);

        getDefaultHost().attach("/accounts/manager_account_controller/manager_sales_history/", ManagerSalesHistoryResource.class);
    }

    private static void resourcesInitialization() throws DataException {
        File resourcesDirectory = new File(PATH);
        if (!resourcesDirectory.exists())
            if (!resourcesDirectory.mkdir())
                throw new DataException("System loading failed.");
    }

    private static void initializeLoading(){
        try {
            resourcesInitialization();
        } catch (DataException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        try {
            Loader.getLoader().loadData();
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }
}
