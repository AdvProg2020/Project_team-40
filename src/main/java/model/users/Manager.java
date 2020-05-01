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
