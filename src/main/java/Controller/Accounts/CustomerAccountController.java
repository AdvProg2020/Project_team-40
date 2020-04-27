package Controller.Accounts;

import model.Cart;
import model.Product;
import model.log.Log;
import model.users.Customer;
import model.users.User;

import java.util.ArrayList;

public class CustomerAccountController extends AccountController{
    private static CustomerAccountController customerAccountController;

    public void createCustomerAccount(String username, String password, String firstName,
                                      String lastName, String email, String phoneNumber, double credit){
        Customer customer = new Customer(username, password, firstName, lastName, email, phoneNumber, credit);
        User.addUser(customer);
        this.user = (User) customer;
    }

    public Cart getCart(){
        return null;
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

    private CustomerAccountController(){

    }

    public static CustomerAccountController getInstance(){
        if(customerAccountController == null)
            customerAccountController = new CustomerAccountController();

        return customerAccountController;
    }
}
