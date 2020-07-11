package server.model;

import org.junit.Test;

public class DiscountCodeLoading {
    @Test
    public void testLoadingData(){
        try {
            DiscountCode.loadData();
        } catch (Exception e) {
            System.out.println("Loading data failed");
        }
        System.out.println(DiscountCode.getAllDiscountCodes());
    }
}
