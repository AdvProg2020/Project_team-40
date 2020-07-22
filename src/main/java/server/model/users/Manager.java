package server.model.users;

import server.model.requests.Request;

import java.util.ArrayList;

public class Manager extends User {
    private static ArrayList<Request> requests = new ArrayList<>();
    private static Double wage;
    private static Double minWalletBalance;

    public Manager(){}

    public Manager(String username, String password, String firstName,
                   String lastName, String email, String phoneNo) {
        super(username, password, firstName, lastName, email, phoneNo);
        setCommonBankAccount();
        if(wage == null)
            wage = (double) 0;
        if(minWalletBalance == null)
            minWalletBalance = (double) 0;
    }

    private void setCommonBankAccount() {
        for(String username : User.getAllUsernames()) {
            User user = User.getUserByUsername(username);
            if(user instanceof Manager) {
                this.setBankAccount(user.getBankAccount());
                this.setBankUsername(user.getBankUsername());
                break;
            }
        }
    }

    public static void setWage(Double wage) {
        Manager.wage = wage;
    }

    public static Double getWage() {
        return wage;
    }

    public static Double getMinWalletBalance() {
        return minWalletBalance;
    }

    public static void setMinWalletBalance(Double minWalletBalance) {
        Manager.minWalletBalance = minWalletBalance;
    }

    public static double subtractWage(double money) {
        return money * ((100 - wage) / 100);
    }

    public static boolean isWalletBalanceEnough(double money) {
        return money >= minWalletBalance;
    }

    @Override
    public String getRole() {
        return "Manager";
    }

    public static ArrayList<Request> getRequests() {
        return requests;
    }

    public static void addRequest(Request request){
        requests.add(request);
    }

    public static Request getRequestById(String id){
        for (Request request : requests) {
            if (request.getRequestId().equals(id))
                return request;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Username: " + username + "\n" +
                "Email Address: " + email + "\n" +
                "Phone Number: " + phoneNo + "\n";
    }
}
