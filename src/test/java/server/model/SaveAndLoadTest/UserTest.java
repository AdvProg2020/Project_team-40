package server.model.SaveAndLoadTest;

import exceptions.DataException;
import server.model.users.Customer;
import server.model.users.User;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    @Test
    public void testSaveUser(){
        Customer customer = new Customer("A", "100", "b", "c",
                "abc@gamil.com", "0", 10000);
        try {
            User.saveData();
        } catch (DataException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSaveAndLoadUsers(){
        Customer customer = new Customer("AmiraliEbi", "808595", "Amirali", "Ebi",
                "Ebi@gamil.com", "000", 1000);
        String customerBeforeSaving = customer.toString();
        try {
            User.saveData();
        } catch (DataException e) {
            System.out.println(e.getMessage());
        }
        User.deleteUser(customer);
        if(User.getUserByUsername("AmiraliEbi") == null){
            System.out.println("Customer successfully deleted");
        }
        try {
            User.loadData();
        } catch (DataException e) {
            System.out.println(e.getMessage());
        }
        User user = User.getUserByUsername("AmiraliEbi");
        String customerAfterLoading = user.toString();
        System.out.println("Before:\n" + customerBeforeSaving);
        System.out.println("After:\n" + customerAfterLoading);
        Assert.assertEquals(customerBeforeSaving, customerAfterLoading);
    }
}
