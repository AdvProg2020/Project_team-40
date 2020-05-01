package Controller.Accounts;

import exceptions.AccountsException;
import model.Cart;
import model.DiscountCode;
import model.Product;
import model.Score;
import model.log.Log;
import model.users.Customer;
import model.users.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomerAccountController extends AccountController{
    private static CustomerAccountController customerAccountController = new CustomerAccountController();
    private String address;
    private double priceAfterDiscount;
    private double discount;

    private CustomerAccountController(){}

    public static CustomerAccountController getInstance(){
        if(customerAccountController == null)
            customerAccountController = new CustomerAccountController();

        return customerAccountController;
    }

    public void createCustomerAccount(String username, String password, String firstName, String lastName,
                                      String email, String phoneNumber, double credit) throws AccountsException {
        if(User.doesUserExist(username)) {
            throw new AccountsException("User exists with this username.");
        }
        Customer customer = new Customer(username, password, firstName, lastName, email, phoneNumber, credit);
    }

    public HashMap<Product, Integer> getCart(){
        HashMap<Product, Integer> productsWithQuantity = new HashMap<>();
        Customer customer = (Customer) user;
        for(Map.Entry<String, Integer> entry: customer.getCart().entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            productsWithQuantity.put(Product.getProductById(productId), quantity);
        }
        return productsWithQuantity;
    }

    public Product getChosenProduct(String productId) throws AccountsException {
        Customer customer = (Customer) user;
        if(customer.getCart().containsKey(productId)) {
            return customer.getProductById(productId);
        } else {
            throw new AccountsException("Product has not been chosen by customer");
        }
    }

    public void increaseChosenProductsQuantity(String productId) throws AccountsException {
        Customer customer = (Customer) user;
        if(customer.getCart().containsKey(productId)) {
            customer.addProductsQuantity(productId);
        } else {
            throw new AccountsException("Product has not been chosen by customer");
        }
    }

    public void decreaseChosenProductQuantity(String productId) throws AccountsException {
        Customer customer = (Customer) user;
        if(customer.getCart().containsKey(productId)) {
            customer.decreaseProductsQuantity(productId);
        } else {
            throw new AccountsException("Product has not been chosen by customer");
        }
    }

    public double getCartTotalPrice(){
        return ((Customer) user).getTotalPriceOfCart();
    }

    private Log purchase() {
        Customer customer = (Customer) user;
        Log log = new Log(new Date(), priceAfterDiscount, discount, customer.getCart(),
                customer.getUsername(), false);
        customer.getLogsId().add(log.getId());
        Log.getLogs().put(log.getId(), log);
        address = null;
        priceAfterDiscount = -1;
        discount = -1;
        return log;
    }

    public void getReceiverInfo(String address){
        this.address = address;
    }

    //If customer doesn't have any discount this method must be called:
    public void setPriceWithoutDiscount() {
        this.priceAfterDiscount = ((Customer) user).getTotalPriceOfCart();
    }

    public void enterDiscountCode(String code) throws AccountsException{
        Customer customer = (Customer) user;
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if(!customer.getDiscountCodes().contains(code)) {
            throw new AccountsException("Invalid discount code.");
        } else if(!discountCode.isCountRemained(customer)) {
            throw new AccountsException("Discount code has been used.");
        } else if(discountCode.isExpired()) {
            throw new AccountsException("Date Expire");
        } else {
            priceAfterDiscount = discountCode.calculatePriceAfterDiscount(customer.getTotalPriceOfCart());
        }
    }

    public Log makePayment() throws AccountsException{
        Customer customer = (Customer) user;
        if(this.priceAfterDiscount > customer.getCredit()) {
            throw new AccountsException("Credit not enough.");
        } else {
            customer.setCredit(customer.getCredit() - customer.getTotalPriceOfCart());
            return purchase();
        }
    }

    public void addToCredit(double money) {
        Customer customer = (Customer) user;
        customer.setCredit(customer.getCredit() + money);
    }

    public ArrayList<Log> getOrders() {
        Customer customer = (Customer) user;
        ArrayList<Log> logs = new ArrayList<>();
        for(String logId: customer.getLogsId()) {
            logs.add(Log.getLogById(logId));
        }
        return logs;
    }

    public Log getOrder(String orderID) throws AccountsException {
        Customer customer = (Customer) user;
        if(customer.getLogsId().contains(orderID)) {
            return customer.getLogById(orderID);
        } else {
            throw new AccountsException("Customer doesn't have an Order with this ID.");
        }
    }

    public void rateProduct(String productID, int rate) {
        Product product = Product.getProductById(productID);
        Score score = new Score(user.getUsername(), rate, productID);
        product.addScore(score);
    }

    public double getBalance(){
        return ((Customer) user).getCredit();
    }

    public ArrayList<DiscountCode> getCustomersDiscountCodes() {
        ArrayList<DiscountCode> customersDiscountCodes = new ArrayList<>();
        Customer customer = (Customer) user;
        for(String code: customer.getDiscountCodes()) {
            customersDiscountCodes.add(DiscountCode.getDiscountCodeByCode(code));
        }
        return customersDiscountCodes;
    }
}
