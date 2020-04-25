package model.UserTest;

import model.users.Customer;
import model.users.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SaveAndLoadTest {
    @Test
    public void testSaveUser(){
        Customer customer = new Customer("A", "100", "b", "c",
                "abc@gamil.com", "0", 10000);
        User.saveData();
    }

    @Test
    public void testSaveAndLoadUsers(){
        Customer customer = new Customer("AmiraliEbi", "808595", "Amirali", "Ebi",
                "Ebi@gamil.com", "000", 1000);
        String customerBeforeSaving = customer.toString();
        User.saveData();
        User.deleteUser(customer);
        if(User.getUserByUsername("AmiraliEbi") == null){
            System.out.println("Customer successfully deleted");
        }
        try {
            User.loadData();
        } catch (IOException e) {
            System.out.println("Load wasn't successful.");
        }
        User user = User.getUserByUsername("AmiraliEbi");
        String customerAfterLoading = user.toString();
        System.out.println("Before:\n" + customerBeforeSaving);
        System.out.println("After:\n" + customerAfterLoading);
        Assert.assertEquals(customerBeforeSaving, customerAfterLoading);
    }
}
