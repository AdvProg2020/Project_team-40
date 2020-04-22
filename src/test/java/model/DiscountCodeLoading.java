package model;

import org.junit.Test;

import java.io.IOException;

public class DiscountCodeLoading {
    @Test
    public void testLoadingData(){
        try {
            DiscountCode.loadData();
        } catch (IOException e) {
            System.out.println("Loading data failed");
        }
        System.out.println(DiscountCode.getAllDiscountCodes());
    }
}
