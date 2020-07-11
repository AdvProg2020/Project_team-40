package server.model;

import org.junit.Test;

public class UtilityTest {

    @Test
    public void testIdGenerator(){
        for(int i = 0; i < 10; i++){
            System.out.println(Utility.generateId());
        }
    }
}
