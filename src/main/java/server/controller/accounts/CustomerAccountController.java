package server.controller.accounts;

import client.view.account_menus.ThisUser;
import exceptions.AccountsException;
import server.model.DiscountCode;
import server.model.Product;
import server.model.Score;
import server.model.log.Log;
import server.model.users.Customer;
import server.model.users.Seller;
import server.model.users.User;

import java.util.*;

public class CustomerAccountController extends AccountController{
    private static CustomerAccountController customerAccountController = new CustomerAccountController();
    private String address;
    private double priceAfterDiscount;
    private double priceWithoutDiscount;
    private String discountCode;

    private CustomerAccountController() {
        resetPurchaseVariables();
    }

    public static CustomerAccountController getInstance(){
        if(customerAccountController == null)
            customerAccountController = new CustomerAccountController();

        return customerAccountController;
    }

    public void createCustomerAccount(String username, String password, String firstName, String lastName,
                                      String email, String phoneNumber, double credit) {
        Customer customer = new Customer(username, password, firstName, lastName, email, phoneNumber, credit);
    }

    public HashMap<String, DiscountCode> getDiscountCodes() {
        ArrayList<String> discountCodesNames = ((Customer) User.getUserByUsername(ThisUser.getUsername())).getDiscountCodes();
        HashMap<String, DiscountCode> discountCodes = new HashMap<>();
        for(String discountCodeName: discountCodesNames) {
            discountCodes.put(discountCodeName, DiscountCode.getDiscountCodeByCode(discountCodeName));
        }
        return discountCodes;
    }

    public HashMap<Product, Integer> getCart() {
        HashMap<Product, Integer> productsWithQuantity = new HashMap<>();
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        for(Map.Entry<String, Integer> entry: customer.getCart().entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            productsWithQuantity.put(Product.getProductById(productId), quantity);
        }
        return productsWithQuantity;
    }

    public Product getChosenProduct(String productId) throws AccountsException {
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        if(customer.getCart().containsKey(productId)) {
            return customer.getProductById(productId);
        } else {
            throw new AccountsException("Product has not been chosen by customer");
        }
    }

    public void increaseChosenProductsQuantity(String productId) throws AccountsException {
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        if(customer.getCart().containsKey(productId)) {
            customer.addProductsQuantity(productId);
        } else {
            throw new AccountsException("Product has not been chosen by customer");
        }
    }

    public void decreaseChosenProductQuantity(String productId) throws AccountsException {
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        if(customer.getCart().containsKey(productId)) {
            customer.decreaseProductsQuantity(productId);
        } else {
            throw new AccountsException("Product has not been chosen by customer");
        }
    }

    public double getCartTotalPrice(){
        return ((Customer) User.getUserByUsername(ThisUser.getUsername())).getTotalPriceOfCart();
    }

    private Log purchase() {
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        if(priceWithoutDiscount == 0 || priceWithoutDiscount == -1) {
            priceWithoutDiscount = customer.getTotalPriceOfCart();
        }
        if(priceAfterDiscount == 0 || priceAfterDiscount == -1) {
            priceAfterDiscount = priceWithoutDiscount;
        }
        Log log = new Log(new Date(), priceAfterDiscount, priceWithoutDiscount, customer.getCart(),
                customer.getUsername(), address,false);
        decreaseProductsCountAfterPurchase(log);
        customer.getLogsId().add(log.getId());
        Log.getLogs().put(log.getId(), log);
        customer.addLog(log);
        addLogToSellers(log);
        customer.removeAllProducts();
        decreaseDiscountCodeCountPerUser();
        resetPurchaseVariables();
        return log;
    }

    private void resetPurchaseVariables() {
        address = null;
        priceAfterDiscount = -1;
        priceWithoutDiscount = -1;
    }

    public void setReceiverInfo(String address){
        this.address = address;
    }

    //If customer doesn't have any discount this method must be called:
    public void setPriceWithoutDiscount() {
        this.priceAfterDiscount = ((Customer) User.getUserByUsername(ThisUser.getUsername())).getTotalPriceOfCart();
    }

    public void enterDiscountCode(String code) throws AccountsException{
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if(!customer.getDiscountCodes().contains(code)) {
            throw new AccountsException("Invalid discount code.");
        } else if(!discountCode.isCountRemained(customer)) {
            throw new AccountsException("Discount code has been used.");
        } else if(DiscountCode.isExpired(discountCode.getEndDate())) {
            throw new AccountsException("Date Expire");
        } else {
            priceWithoutDiscount = customer.getTotalPriceOfCart();
            priceAfterDiscount = discountCode.calculatePriceAfterDiscount(customer.getTotalPriceOfCart());
            this.discountCode = discountCode.getCode();
        }
    }

    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public Log makePayment() throws AccountsException{
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        if(priceAfterDiscount == -1 || priceWithoutDiscount == -1) {
            priceWithoutDiscount = customer.getTotalPriceOfCart();
            priceAfterDiscount = priceWithoutDiscount;
        }
        if(this.priceAfterDiscount > customer.getCredit()) {
            throw new AccountsException("Credit not enough.");
        } else {
            customer.setCredit(customer.getCredit() - customer.getTotalPriceOfCart());
            return purchase();
        }
    }

    private void addLogToSellers(Log log) {
        HashSet<Seller> sellers = new HashSet<>();
        for(Map.Entry<String, Integer> entry: log.getProductsId().entrySet()) {
            sellers.add(Product.getProductById(entry.getKey()).getSeller());
        }
        for(Seller seller: sellers) {
            HashMap<String, Integer> productsId = new HashMap<>();
            double sellersProfit = 0;
            for(Map.Entry<String, Integer> entry: log.getProductsId().entrySet()) {
                Product product = Product.getProductById(entry.getKey());
                if(product.getSeller().equals(seller)) {
                    productsId.put(product.getProductId(), log.getProductsId().get(product.getProductId()));
                    sellersProfit += product.getPrice();
                }
            }
            seller.addLog( new Log(log.getDate(), log.getCost() / log.getCostWithoutDiscount() * sellersProfit,
                    sellersProfit, productsId, log.getBuyerName(), log.getAddress(),false));
            seller.setCredit(seller.getCredit() + sellersProfit);
        }
    }

    private void decreaseProductsCountAfterPurchase(Log log) {
        HashMap<String, Integer> productsIds = log.getProductsId();
        for(Map.Entry<String, Integer> entry: productsIds.entrySet()) {
            Product product = Product.getProductById(entry.getKey());
            product.setCount(product.getCount() - entry.getValue());
        }
    }

    private void decreaseDiscountCodeCountPerUser() {
        if(this.discountCode != null) {
            DiscountCode discountCode;
            if((discountCode = DiscountCode.getDiscountCodeByCode(this.discountCode)) != null) {
                discountCode.decreaseCountPerUser((Customer) User.getUserByUsername(ThisUser.getUsername()));
            }
        }
        this.discountCode = null;
    }

    public void addToCredit(double money) {
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        customer.setCredit(customer.getCredit() + money);
    }

    public HashMap<String, Log> getOrders() {
        ArrayList<String> logsId = ((Customer) User.getUserByUsername(ThisUser.getUsername())).getLogsId();
        HashMap<String, Log> logs = new HashMap<>();
        for(String logId: logsId) {
            logs.put(logId, Log.getLogById(logId));
        }
        return logs;
    }

    public Log getOrder(String orderID) throws AccountsException {
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        if(customer.getLogsId().contains(orderID)) {
            return customer.getLogById(orderID);
        } else {
            throw new AccountsException("Customer doesn't have an Order with this ID.");
        }
    }

    public void rateProduct(String productID, int rate) throws AccountsException{
        Product product = Product.getProductById(productID);

        if(product == null)
            throw new AccountsException("No product with such name exists.");

        if(User.getUserByUsername(ThisUser.getUsername()) == null)
            throw new AccountsException("You are not logged in.");

        Score score = null;

        for(Score score1 : product.getAllScores()) {
            if(score1.getUserName().equals(User.getUserByUsername(ThisUser.getUsername()).getUsername())){
                score = score1;
                score.setScore(rate);
            }
        }

        if(score == null){
            score = new Score(User.getUserByUsername(ThisUser.getUsername()).getUsername(), rate, productID);
            Score.addScore(score);
            product.addScore(score);
        }
    }

    public double getBalance(){
        return ((Customer) User.getUserByUsername(ThisUser.getUsername())).getCredit();
    }

    public ArrayList<DiscountCode> getCustomersDiscountCodes() {
        ArrayList<DiscountCode> customersDiscountCodes = new ArrayList<>();
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        for(String code: customer.getDiscountCodes()) {
            customersDiscountCodes.add(DiscountCode.getDiscountCodeByCode(code));
        }
        return customersDiscountCodes;
    }

    public void deliver(String logId) throws AccountsException {
        Customer customer = (Customer) User.getUserByUsername(ThisUser.getUsername());
        if(customer.getLogsId().contains(logId)) {
            Log.getLogById(logId).setDelivered(true);
        } else {
            throw new AccountsException("Customer doesn't have a log with this ID.");
        }
    }
}