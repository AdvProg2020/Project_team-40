package Controller.Accounts;

import exceptions.AccountsException;
import model.Cart;
import model.Product;
import model.log.Log;
import model.users.Customer;
import model.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerAccountController extends AccountController{
    private static CustomerAccountController customerAccountController = new CustomerAccountController();

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

    public Product getChosenProduct(String productId){
        Customer customer = (Customer) user;
        if(customer.getCart().containsKey(productId)) {
            return customer.getProductById(productId);
        } else {
            return null;
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

    public void purchase(){

    }

    public Log getReceiveInfo(){
        return null;
    }

    public void enterDiscountCode(String code){

    }

    public void makePayment(){

    }

    public ArrayList<String> getOrders(){
        return null;
    }

    public Log getOrder(String orderID){
        Customer customer = (Customer) user;
        if(customer.getLogsId().contains(orderID)) {
            return customer.getLogById(orderID);
        } else {
            return null;
        }
    }

    public void rateProduct(String productID, int rate){

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
