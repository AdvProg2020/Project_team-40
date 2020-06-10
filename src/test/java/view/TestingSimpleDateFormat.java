package view;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestingSimpleDateFormat {
    @Test
    public void testSimpleDateFormat() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("12/11/33 01:33:33");
            System.out.println(date);
            date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("12/13/33 01:33:33");
            System.out.println(date);
            date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("13/12/33 09:11:11");
            System.out.println(date);
            date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("10/12/33 99:11:11");
            System.out.println(date);
            date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("10/12/01 11:11:11");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        Date date2 = new Date();
        Assert.assertTrue(date2.after(date));
    }
}
