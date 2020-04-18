package model.log;

import java.util.ArrayList;
import java.util.Date;

public class SellLog extends Log {
    private String sellerName;
    private boolean isDelivered;
    public SellLog(Date date, double cost, double discount,
                   ArrayList<String> productNames,
                   String sellerName, boolean isDelivered) {
        super(date, cost, discount, productNames);
        this.isDelivered = isDelivered;
        this.sellerName = sellerName;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    @Override
    public String toString() {
        return null;
    }
}
