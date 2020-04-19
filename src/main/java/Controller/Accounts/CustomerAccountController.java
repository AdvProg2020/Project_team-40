package Controller.Accounts;

import model.Cart;
import model.Product;
import model.log.Log;

import java.util.ArrayList;

public class CustomerAccountController extends AccountController{
    private static CustomerAccountController customerAccountController;

    public void createCustomerAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber){

    }

    public Cart getCart(){
        return null;
    }

    public Product getChosenProduct(String productID){
        return null;
    }

    public void increaseChosenProductQuantity(String productID){

    }

    public void decreaseChosenProductQuantity(String productID){

    }

    public double getCartTotalPrice(){
        return 0.0;
    }

    public void purchase(){

    }

    public Log getRecieverInfo(){
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
        return null;
    }

    public void rateProduct(String productID, int rate){

    }

    public double getBalance(){
        return 0.0;
    }

    public ArrayList<String> getDiscountCodes(){
        return null;
    }

    private CustomerAccountController(){

    }

    public static CustomerAccountController getInstance(){
        if(customerAccountController == null)
            customerAccountController = new CustomerAccountController();

        return customerAccountController;
    }
}
