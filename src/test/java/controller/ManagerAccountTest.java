package controller;

import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import model.DiscountCode;
import model.Product;
import model.requests.AddProduct;
import model.requests.Request;
import model.users.Customer;
import model.users.Manager;
import model.users.Seller;
import model.users.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ManagerAccountTest {
    @Test
    public void testManagerAccountController(){
        ManagerAccountController manager = ManagerAccountController.getInstance();
        Customer c1 = new Customer("matind", "1234", "Matin", "Daghyani","m@mail.com","09121234567", 1000);
        Customer c2 = new Customer("alid", "1234", "Ali", "Davoodi","a@mail.com","09121111111", 1030);
        Seller seller = new Seller("FaridD", "123", "Farid", "Dori", "f@mail.com", "222222", 500, "Damavand");
        Manager m1 = new Manager("saraK", "12ss3", "sara", "kamali", "s@mail.com", "444222");
        User.addUser(c1);
        User.addUser(c2);
        User.addUser(seller);
        Product p1 = new Product("Apple", "Tarebar", 10, 20, "FaridD", "Fruit");
        Product p2 = new Product("Orange", "Tarebar", 12, 40, "FaridD", "Fruit");
        Product.addProduct(p1);
        //Manage Users
        for (String userName : manager.getAllUserNames()) {
            System.out.println(userName);
            Assert.assertEquals(4, User.getAllUsernames().size());
        }
        //View user
        try {
            System.out.println(manager.getUser("matind"));
        } catch (AccountsException e) {
            System.out.println(e.getMessage());
        }

        //delete user
        try {
            manager.deleteUser("FridD");
        } catch (AccountsException e) {
            System.out.println(e.getMessage());
        }
        for (String userName : manager.getAllUserNames()) {
            System.out.println(userName);
        }

        //remove Product
        String id = p1.getProductId();
        try {
            manager.removeProduct(id);
        } catch (AccountsException e) {
            System.out.println(e.getMessage());
        }

        //discount code
        ArrayList<String> discountUsers = new ArrayList<>();
        discountUsers.add("matind");

        try {
            manager.createDiscount("11-1-2020 20:00", "11-6-2020 20:00", 20, 1000, 10, discountUsers);
            Assert.assertEquals(1, DiscountCode.getAllDiscountCodes().size());
        } catch (AccountsException e) {
            System.out.println(e.getMessage());
        }
//        try {
//            manager.editDiscount(DiscountCode.getAllDiscountCodes().get(0).getCode(), "Percentage", "10");
//        } catch (AccountsException e) {
//            System.out.println(e.getMessage());
//        }
        try {
            System.out.println(manager.getDiscount(DiscountCode.getAllDiscountCodes().get(0).getCode()));
        } catch (AccountsException e) {
            e.printStackTrace();
        }
        System.out.println(c1.getDiscountCodes());

        //manage request
        Request addProduct = new AddProduct(p1);
        Request addProduct2 = new AddProduct(p2);
        Manager.addRequest(addProduct);
        try {
            manager.acceptRequest(addProduct.getRequestId());
            Assert.assertEquals(1, Product.getAllProducts().size());
        } catch (AccountsException e) {
            System.out.println(e.getMessage());
        }

        try {
            manager.declineRequest(addProduct2.getRequestId());
            Assert.assertEquals(1, Product.getAllProducts().size());
        } catch (AccountsException e) {
            System.out.println(e.getMessage());
        }
    }
}
