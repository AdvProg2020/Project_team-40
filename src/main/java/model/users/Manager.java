package model.users;

import model.requests.Request;

import java.util.ArrayList;

public class Manager extends User {
    private static ArrayList<Request> requests;

    public Manager(String username, String password, String firstName,
                   String lastName, String email, String phoneNo) {
        super(username, password, firstName, lastName, email, phoneNo);
    }

    public static ArrayList<Request> getRequests() {
        return requests;
    }

    public static void addRequest(Request request){
        requests.add(request);
    }

    public static void loadData(){}

    public static void saveData(){}


    @Override
    public String toString() {
        return null;
    }
}
