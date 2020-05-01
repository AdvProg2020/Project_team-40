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

    public Product getChosenProduct(String productId) throws Exception {
        Customer customer = (Customer) user;
        if(customer.getCart().containsKey(productId)) {
            return customer.getProductById(productId);
        } else {
            throw new Exception("Product has not been chosen by customer");
        }
    }

    public void increaseChosenProductQuantity(String productId) {
        Customer customer = (Customer) user;
        customer.addProductsQuantity(productId);
    }

    public void decreaseChosenProductQuantity(String productId){
        Customer customer = (Customer) user;
        customer.decreaseProductsQuantity(productId);
    }

    public double getCartTotalPrice(){
        return ((Customer) user).getTotalPriceOfCart();
    }

    public Log purchase() {
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

    public void getReceiveInfo(String address){
        this.address = address;
    }

    public void setPriceAfterDiscount() {
        this.priceAfterDiscount = ((Customer) user).getTotalPriceOfCart();
        //If customer doesn't have any discount this method must be called
    }

    public void enterDiscountCode(String code) {
        Customer customer = (Customer) user;
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if(!customer.getDiscountCodes().contains(code)) {
            //TODO: THROW PROPER EXCEPTION
        } else if(!discountCode.isCountRemained(customer)) {
            //TODO: THROW PROPER EXCEPTION
        } else if(discountCode.isExpired()) {
            //TODO: THROW PROPER EXCEPTION
        } else {
            priceAfterDiscount = discountCode.calculatePriceAfterDiscount(customer.getTotalPriceOfCart());
        }
    }

    public Log makePayment() {
        Customer customer = (Customer) user;
        if(this.priceAfterDiscount > customer.getCredit()) {
            //TODO: THROW EXCEPTION
            return null;
        } else {
            customer.setCredit(customer.getCredit() - customer.getTotalPriceOfCart());
            return purchase();
        }
    }

    public ArrayList<Log> getOrders() {
        Customer customer = (Customer) user;
        ArrayList<Log> logs = new ArrayList<>();
        for(String logId: customer.getLogsId()) {
            logs.add(Log.getLogById(logId));
        }
        return logs;
    }

    public Log getOrder(String orderID){
        Customer customer = (Customer) user;
        if(customer.getLogsId().contains(orderID)) {
            return customer.getLogById(orderID);
        } else {
            return null;
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

    public ArrayList<String> getDiscountCodes(){
        return ((Customer) user).getDiscountCodes();
    }

    public static CustomerAccountController getInstance(){
        if(customerAccountController == null)
            customerAccountController = new CustomerAccountController();

        return customerAccountController;
    }
}
