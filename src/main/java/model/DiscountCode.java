package model;

import model.users.Costumer;

import java.util.ArrayList;
import java.util.Date;

public class DiscountCode {
    private static ArrayList<DiscountCode> AllDiscountCodes;
    private String code;
    private Date startDate;
    private Date endDate;
    private int percentage;
    private double maxAmount;
    private int countPerUser;
    private ArrayList<Costumer> includedCostumers;

    public DiscountCode(Date startDate, Date endDate,
                        int percentage, double maxAmount, int countPerUser) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentage = percentage;
        this.maxAmount = maxAmount;
        this.countPerUser = countPerUser;
        this.includedCostumers = new ArrayList<>();
    }

    public static ArrayList<DiscountCode> getAllDiscountCodes() {
        return AllDiscountCodes;
    }

    public ArrayList<Costumer> getIncludedCostumers() {
        return includedCostumers;
    }

    public boolean checkIfExpired(){return false;}

    private void generateCode(){}

    public void addCostumer(Costumer costumer){}

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setCountPerUser(int countPerUser) {
        this.countPerUser = countPerUser;
    }

    public static DiscountCode getDiscountCodeByCode(String code){return null;}

    public static void removeDiscountCode(DiscountCode discountCode){}

    public static void loadData(){}

    public static void saveData(){}

    @Override
    public String toString() {
        return "DiscountCode{}";
    }
}
