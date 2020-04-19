package model;

import model.users.Costumer;

import java.util.ArrayList;
import java.util.Date;

public class DiscountCode {
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
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
        return allDiscountCodes;
    }

    public ArrayList<Costumer> getIncludedCostumers() {
        return includedCostumers;
    }

    public boolean checkIfExpired(){
        Date today = new Date();
        return !today.after(startDate) && today.before(endDate);
    }

    private void generateCode(){}

    public void addCostumer(Costumer costumer){
        includedCostumers.add(costumer);
    }

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

    public boolean isCostumerValid(Costumer costumer){return includedCostumers.contains(costumer);}

    public static DiscountCode getDiscountCodeByCode(String code){
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.code.equals(code))
                return discountCode;
        }
        return null;
    }

    public static void removeDiscountCode(DiscountCode discountCode){
        DiscountCode code = getDiscountCodeByCode(discountCode.code);
        allDiscountCodes.remove(code);
    }

    public static void loadData(){}

    public static void saveData(){}

    @Override
    public String toString() {
        return "DiscountCode{}";
    }
}
