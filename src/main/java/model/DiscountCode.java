package model;

import model.users.Costumer;
import model.users.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiscountCode implements Serializable {
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private String code;
    private Date startDate;
    private Date endDate;
    private int percentage;
    private double maxAmount;
    private int countPerUser;
    private Map<String, Integer> includedCostumers;

    public DiscountCode(Date startDate, Date endDate,
                        int percentage, double maxAmount, int countPerUser) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentage = percentage;
        this.maxAmount = maxAmount;
        this.countPerUser = countPerUser;
        this.includedCostumers = new HashMap<>();
        generateCode();
        allDiscountCodes.add(this);
    }

    public static ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public Map<String, Integer> getIncludedCostumers() {
        return includedCostumers;
    }

    public boolean checkIfExpired(){
        Date today = new Date();
        return !(today.after(startDate) && today.before(endDate));
    }

    private void generateCode(){
        this.code = Utility.generateId();
    }

    public void addCostumer(Costumer costumer){
        includedCostumers.put(costumer.getUsername(), countPerUser);
        costumer.addDiscountCode(this);
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

    public void decreaseCountPerUser(Costumer costumer) {
        int newCount = includedCostumers.get(costumer.getUsername()) - 1;
        if (newCount < 0)
            newCount = 0;
        includedCostumers.replace(costumer.getUsername(), newCount);
    }

    public double calculatePriceAfterDiscount(double price){
        double newPrice = price * (1 - this.percentage*0.01) * price;
        if (newPrice > this.maxAmount)
            newPrice = this.maxAmount;
        return newPrice;
    }

    public boolean isCountRemained(Costumer costumer){
        return includedCostumers.get(costumer.getUsername()) >= 0;
    }

    public static DiscountCode getDiscountCodeByCode(String code){
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.code.equals(code))
                return discountCode;
        }
        return null;
    }

    public static void removeDiscountCode(DiscountCode discountCode){
        for (String costumer : discountCode.includedCostumers.keySet()) {
            ((Costumer)(User.getUserByUsername(costumer))).getDiscountCodes().remove(discountCode);
        }
        allDiscountCodes.remove(discountCode);
    }

    public static void loadData(){}

    public static void saveData(){
        String path = "src/main/resources/discount codes/";
        for (DiscountCode discountCode : allDiscountCodes) {
            try {
                FileOutputStream file = new FileOutputStream(path + discountCode.code);
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(discountCode);
                file.close();
                outputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "DiscountCode{}";
    }
}
