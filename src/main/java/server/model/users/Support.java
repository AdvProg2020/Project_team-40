package server.model.users;

import java.util.ArrayList;

public class Support extends User{

    private static ArrayList<Support> allSupports = new ArrayList<>();

    @Override
    public String getRole(){
        return "Support";
    }

    @Override
    public String toString(){
        return "Name: " + firstName + " " + lastName + "\n" +
                "Username: " + username + "\n" +
                "Email Address: " + email + "\n" +
                "Phone Number: " + phoneNo + "\n";
    }

    public static ArrayList<Support> getAllSupports(){
        return allSupports;
    }
}
