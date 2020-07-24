package server.controller.accounts;

import exceptions.AccountsException;
import server.model.DiscountCode;
import server.model.Product;
import server.model.Score;
import server.model.log.Log;
import server.model.users.Customer;
import server.model.users.Manager;
import server.model.users.Seller;
import server.model.users.User;

import java.util.*;

public class CustomerAccountController extends AccountController{
    private static CustomerAccountController customerAccountController = new CustomerAccountController();
    private CustomerAccountController() {}

    public static CustomerAccountController getInstance(){
        if(customerAccountController == null)
            customerAccountController = new CustomerAccountController();

        return customerAccountController;
    }

    public void createCustomerAccount(String username, String password, String firstName, String lastName,
                                      String email, String phoneNumber, double credit) {
        Customer customer = new Customer(username, password, firstName, lastName, email, phoneNumber, credit);
    }

    public HashMap<String, DiscountCode> getDiscountCodes(String username) {
        ArrayList<String> discountCodesNames = ((Customer) User.getUserByUsername(username)).getDiscountCodes();
        HashMap<String, DiscountCode> discountCodes = new HashMap<>();
        for(String discountCodeName: discountCodesNames) {
            discountCodes.put(discountCodeName, DiscountCode.getDiscountCodeByCode(discountCodeName));
        }
        return discountCodes;
    }

    public HashMap<Product, Integer> getCart(String username) {
        HashMap<Product, Integer> productsWithQuantity = new HashMap<>();
        Customer customer = (Customer) User.getUserByUsername(username);
        for(Map.Entry<String, Integer> entry: customer.getCart().entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            productsWithQuantity.put(Product.getProductById(productId), quantity);
        }
        return productsWithQuantity;
    }

    private Log purchase(String username,String code ,String address , double priceWithoutDiscount, double priceAfterDiscount) {
        Customer customer = (Customer) User.getUserByUsername(username);
        Log log = new Log(new Date(), priceAfterDiscount, priceWithoutDiscount, customer.getCart(),
                customer.getUsername(), address,false);
        decreaseProductsCountAfterPurchase(log);
        customer.getLogsId().add(log.getId());
        Log.getLogs().put(log.getId(), log);
        customer.addLog(log);
        addLogToSellers(log);
        customer.removeAllProducts();
        if(code != null)
            decreaseDiscountCodeCountPerUser(username, code);
        return log;
    }


    //This method checks whether the discount code is valid or not

    public void checkDiscountCode(String code, String username) throws AccountsException{
        Customer customer = (Customer) User.getUserByUsername(username);
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if(!customer.getDiscountCodes().contains(code)) {
            throw new AccountsException("Invalid discount code.");
        } else if(!discountCode.isCountRemained(customer)) {
            throw new AccountsException("Discount code has been used.");
        } else if(DiscountCode.isExpired(discountCode.getEndDate())) {
            throw new AccountsException("Date Expire");
        }
    }

    public Log makePayment(String username,String address ,String code,double priceAfterDiscount, double priceWithoutDiscount) throws AccountsException{
        Customer customer = (Customer) User.getUserByUsername(username);

        if(priceAfterDiscount > customer.getCreditInWallet() - Manager.getMinWalletBalance()) {
            throw new AccountsException("Credit not enough.");
        } else {
            //TODO: Not sure if it's correct or not!
            customer.setCreditInWallet(customer.getCreditInWallet() - customer.getTotalPriceOfCart());
            return purchase(username, address, code, priceWithoutDiscount, priceAfterDiscount);
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
            seller.setCreditInWallet(seller.getCreditInWallet() + sellersProfit);
        }
    }

    private void decreaseProductsCountAfterPurchase(Log log) {
        HashMap<String, Integer> productsIds = log.getProductsId();
        for(Map.Entry<String, Integer> entry: productsIds.entrySet()) {
            Product product = Product.getProductById(entry.getKey());
            product.setCount(product.getCount() - entry.getValue());
        }
    }

    private void decreaseDiscountCodeCountPerUser(String username, String code) {
            if (code != null){
                DiscountCode discountCode;
                if((discountCode = DiscountCode.getDiscountCodeByCode(code)) != null) {
                    discountCode.decreaseCountPerUser((Customer) User.getUserByUsername(username));
                }
            }
        }


    public void addToCredit(double money, String username) {
        Customer customer = (Customer) User.getUserByUsername(username);
        customer.setCreditInWallet(customer.getCreditInWallet() + money);
    }

    public HashMap<String, Log> getOrders(String username) {
        ArrayList<String> logsId = ((Customer) User.getUserByUsername(username)).getLogsId();
        HashMap<String, Log> logs = new HashMap<>();
        for(String logId: logsId) {
            logs.put(logId, Log.getLogById(logId));
        }
        return logs;
    }

    public Log getOrder(String orderID, String username) throws AccountsException {
        Customer customer = (Customer) User.getUserByUsername(username);
        if(customer.getLogsId().contains(orderID)) {
            return customer.getLogById(orderID);
        } else {
            throw new AccountsException("Customer doesn't have an Order with this ID.");
        }
    }

    public void rateProduct(String username, String productID, int rate) throws AccountsException{
        Product product = Product.getProductById(productID);

        if(product == null)
            throw new AccountsException("No product with such name exists.");

        Score score = null;

        for(Score score1 : product.getAllScores()) {
            if(score1.getUserName().equals(username)){
                score = score1;
                score.setScore(rate);
            }
        }

        if(score == null){
            score = new Score(username, rate, productID);
            Score.addScore(score);
            product.addScore(score);
        }
    }

    public double getBalance(String username){
        return ((Customer) User.getUserByUsername(username)).getCreditInWallet();
    }

    public ArrayList<DiscountCode> getCustomersDiscountCodes(String username) {
        ArrayList<DiscountCode> customersDiscountCodes = new ArrayList<>();
        Customer customer = (Customer) User.getUserByUsername(username);
        for(String code: customer.getDiscountCodes()) {
            customersDiscountCodes.add(DiscountCode.getDiscountCodeByCode(code));
        }
        return customersDiscountCodes;
    }

    public void deliver(String username, String logId) throws AccountsException {
        Customer customer = (Customer) User.getUserByUsername(username);
        if(customer.getLogsId().contains(logId)) {
            Log.getLogById(logId).setDelivered(true);
        } else {
            throw new AccountsException("Customer doesn't have a log with this ID.");
        }
    }
}
