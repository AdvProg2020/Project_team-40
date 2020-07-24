package server.model;

import exceptions.DataException;
import org.junit.Test;
import server.model.enumerations.ReceiptTypes;

public class TestSavingReceipt {
    @Test
    public void ReceiptShouldBeSaved(){
        Receipt receipt = new Receipt(12345, "matin", "hello", 200, 1444, 5555, ReceiptTypes.DEPOSIT);
        try {
            Receipt.saveData();
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }
}
