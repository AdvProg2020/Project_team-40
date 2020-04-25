package model.UserTest;

import model.users.Customer;
import model.users.User;
import org.junit.Test;

public class SaveAndLoadTest {
    @Test
    public void testSaveData(){
        Customer customer = new Customer("AmiraliEbi", "808595", "Amirali", "Ebi",
                "Ebi@gamil.com", "000", 1000);
        System.out.println(customer);
        User.saveData();
        System.out.println(customer);
    }
}
