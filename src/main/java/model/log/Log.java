package model.log;
import java.util.ArrayList;
import java.util.Date;

public abstract class Log {
    private String id;
    private Date date;
    private double cost;
    private double discount;
    private ArrayList<String> productNames;

    public Log(Date date, double cost, double discount,
               ArrayList<String> productNames) {
        this.date = date;
        this.cost = cost;
        this.discount = discount;
        this.productNames = productNames;
    }

    public void generateId(){}

    public String getId(){
        return id;
    }

    @Override
    public abstract String toString();
}