package model;

import exceptions.DataException;
import model.users.Customer;
import model.users.User;

import java.io.*;
import java.util.*;

public class DiscountCode implements Serializable {
    private static final long serialVersionUID = -639372600290420004L;
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

    public int getPercentage() {
        return percentage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isStartDateValid(Date startDate){
        Date today = new Date();
        return today.before(startDate);
    }

    public boolean isEndDateValid(Date endDate){
        Date today = new Date();
        return today.before(endDate);
    }

    private void generateCode(){
        this.code = Utility.generateId();
    }

    public void addCostumer(Customer customer){
        includedCostumers.put(customer.getUsername(), countPerUser);
        customer.addDiscountCode(this);
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
        for (String s : this.includedCostumers.keySet()) {
            includedCostumers.replace(s, countPerUser);
        }
    }

    public void decreaseCountPerUser(Customer customer) {
        int newCount = includedCostumers.get(customer.getUsername()) - 1;
        if (newCount < 0)
            newCount = 0;
        includedCostumers.replace(customer.getUsername(), newCount);
    }

    public double calculatePriceAfterDiscount(double price){
        double newPrice = price * (1 - this.percentage*0.01) * price;//TODO: I THINK THIS METHOD HAS BUGS
        if (newPrice > this.maxAmount)
            newPrice = this.maxAmount;
        return newPrice;
    }

    public boolean isCountRemained(Customer customer){
        return includedCostumers.get(customer.getUsername()) >= 0;
    }

    public String getCode(){
        return code;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public int getCountPerUser() {
        return countPerUser;
    }

    public String getStatus(){
        if (isExpired(endDate, startDate))
            return "EXPIRED";
        return "ACTIVE";
    }

    public Set<String> getIncludedCostumers() {
        return includedCostumers.keySet();
    }

    @Override
    public String toString() {
        StringBuilder customerList = new StringBuilder();
        for (String username : includedCostumers.keySet()) {
            customerList.append(username).append("\n");
        }
        return "Code: " + code + "\n"
                + "Status: " + getStatus() + "\n"
                + "Start Date: " + startDate + "\n"
                + "End Date: " + endDate + "\n"
                + "Percentage: " + percentage + "\n"
                + "Maximum discount amount: "+ maxAmount + "\n"
                + "Count per user: " + countPerUser + "\n"
                + "List of customers: " + customerList;
    }

    public static boolean isExpired(Date endDate, Date startDate){
        Date today = new Date();
        return !(today.before(startDate) && today.before(endDate));
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
            ((Customer)(User.getUserByUsername(costumer))).getDiscountCodes().remove(discountCode.code);
        }
        allDiscountCodes.remove(discountCode);
    }

    public static void loadData() throws DataException {
        String directoryPath = "src/main/resources/discount codes/";
        File directory = new File(directoryPath);
        String[] pathNames = directory.list();
        if (pathNames == null)
            return;

        for (String path: pathNames) {
            try {
                FileInputStream file = new FileInputStream(directoryPath + path);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                allDiscountCodes.add((DiscountCode)inputStream.readObject());
                file.close();
                inputStream.close();
                new File(directoryPath + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading Discount codes data failed.");
            }
        }
    }

    public static void saveData() throws DataException {
        String path = "src/main/resources/discount codes/";
        File directory = new File(path);
        if (!directory.exists())
            if (!directory.mkdir())
                throw new DataException("Saving Discount codes data failed.");
        for (DiscountCode discountCode : allDiscountCodes) {
            try {
                FileOutputStream file = new FileOutputStream(path + discountCode.code);
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(discountCode);
                file.close();
                outputStream.close();

            } catch (Exception e) {
                throw new DataException("Saving Discount codes data failed.");
            }
        }
    }
}
