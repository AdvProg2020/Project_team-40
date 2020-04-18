package model.users;

import java.util.ArrayList;

public abstract class  User {
    private static ArrayList<User> allUsers;
    private static User loggedInUser;
    private String username;

    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;

    public User(String username, String password, String firstName,
                String lastName, String email, String phoneNo) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public boolean checkPassword(String password){
        return false;
    }

    @Override
    public abstract String toString();

    public static void getUserByUsername(String username){}

    public static void deleteUser(User user){}

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static boolean isUserLoggedIn(){
        return false;
    }

    public static void loadData(){}

    public  static void saveData(){}
}
